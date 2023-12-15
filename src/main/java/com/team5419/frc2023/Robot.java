// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team5419.frc2023;

import java.util.Optional;

import com.ctre.phoenix6.SignalLogger;
import com.team5419.frc2023.auto.AutoModeBase;
import com.team5419.frc2023.auto.AutoModeExecutor;
import com.team5419.frc2023.auto.AutoModeSelector;
import com.team5419.frc2023.constants.Constants;
import com.team5419.frc2023.loops.CrashTracker;
import com.team5419.frc2023.loops.SynchronousLooper;
import com.team5419.frc2023.subsystems.Drive;
import com.team5419.lib.TimedRobot;

import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the methods corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot
{
    private SubsystemManager subsystems;
    private DriverControls driverControls;
    private SynchronousLooper looper;

    public static boolean is_red_alliance = false;
    public static boolean flip_trajectories = false;

    private AutoModeExecutor mAutoModeExecutor;
    private final static AutoModeSelector mAutoModeSelector = new AutoModeSelector();

    public Robot() {
        super(Constants.kLooperDt);
    }
    
    @Override
    public void robotInit() {
        RobotController.setBrownoutVoltage(6.1);

        SignalLogger.enableAutoLogging(false);

        driverControls = DriverControls.getInstance();
        subsystems = driverControls.getSubsystems();
        looper = new SynchronousLooper(subsystems);
        looper.registerTeleopLoop(driverControls);

        mAutoModeExecutor = new AutoModeExecutor();
    }

    @Override
    public void autonomousInit(){

        looper.startAuto(Timer.getFPGATimestamp());

        try {
			Optional<AutoModeBase> autoMode = mAutoModeSelector.getAutoMode();
			if (autoMode.isPresent()) {
				Drive.getInstance().resetOdometry(autoMode.get().getStartingPose());
			}

			mAutoModeExecutor.start();
			
		} catch (Throwable t) {
			CrashTracker.logThrowableCrash(t);
			throw t;
		}

		CrashTracker.logAutoInit();
    }
    
    
    /** This method is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
        looper.onAutoLoop(Timer.getFPGATimestamp());
    }

    public void autonomousExit() {
        if(!Drive.getInstance().isDoneWithTrajectory()) {
            mAutoModeExecutor.stop();
        }
        Drive.getInstance().lockPose();
    }

    @Override
    public void robotPeriodic() {
        looper.onTeleopLoop(Timer.getFPGATimestamp());
    }
    
    /** This method is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
        looper.onTeleopLoop(Timer.getFPGATimestamp());
        try {
            boolean alliance_changed = false;
            if (DriverStation.isDSAttached()) {
                if (DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get() == Alliance.Red) {
                    if (!is_red_alliance) {
                        alliance_changed = true;
                    } else {
                        alliance_changed = false;
                    }
                    is_red_alliance = true;
                } else if (DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get()== Alliance.Blue) {
                    if (is_red_alliance) {
                        alliance_changed = true;
                    } else {
                        alliance_changed = false;
                    }
                    is_red_alliance = false;
                }
            } else {
                alliance_changed = true;
            }

            flip_trajectories = is_red_alliance;

            mAutoModeSelector.updateModeCreator(true);
            Optional<AutoModeBase> autoMode = mAutoModeSelector.getAutoMode();
            if (autoMode.isPresent()) {
                mAutoModeExecutor.setAutoMode(autoMode.get());
            }

		} catch (Throwable t) {
			CrashTracker.logThrowableCrash(t);
			throw t;
		}
    }
    
    
    /** This method is called once when the robot is disabled. */
    @Override
    public void disabledInit() {
        looper.startDisabled(Timer.getFPGATimestamp());

        if (mAutoModeExecutor != null) {
			mAutoModeExecutor.stop();
		}

		// Reset all auto mode state.
		mAutoModeSelector.reset();
		mAutoModeSelector.updateModeCreator(false);
		mAutoModeExecutor = new AutoModeExecutor();
    }
    
    
    /** This method is called periodically when disabled. */
    @Override
    public void disabledPeriodic() {
        looper.onDisabledLoop(Timer.getFPGATimestamp());
    }

}
