// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team5419.frc2023;

import com.ctre.phoenix6.SignalLogger;
import com.team5419.frc2023.constants.Constants;
import com.team5419.frc2023.loops.SynchronousLooper;
import com.team5419.lib.TimedRobot;
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
    private static final String DEFAULT_AUTO = "Default";
    private static final String CUSTOM_AUTO = "My Auto";
    private String autoSelected;
    private final SendableChooser<String> chooser = new SendableChooser<>();

    private SubsystemManager subsystems;
    private DriverControls driverControls;
    private SynchronousLooper looper;

    public Robot() {
        super(Constants.kLooperDt);
    }
    
    @Override
    public void robotInit() {
        RobotController.setBrownoutVoltage(6.1);

        chooser.setDefaultOption("Default Auto", DEFAULT_AUTO);
        chooser.addOption("My Auto", CUSTOM_AUTO);
        SmartDashboard.putData("Auto choices", chooser);

        SignalLogger.enableAutoLogging(false);
        driverControls = DriverControls.getInstance();
        subsystems = driverControls.getSubsystems();
        looper = new SynchronousLooper(subsystems);
        looper.registerTeleopLoop(driverControls);
    }

    @Override
    public void autonomousInit()
    {
        autoSelected = chooser.getSelected();
        // autoSelected = SmartDashboard.getString("Auto Selector", DEFAULT_AUTO);
        System.out.println("Auto selected: " + autoSelected);
    }
    
    
    /** This method is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic()
    {
        switch (autoSelected)
        {
            case CUSTOM_AUTO:
                // Put custom auto code here
                break;
            case DEFAULT_AUTO:
            default:
                // Put default auto code here
                break;
        }
    }
    
    /** This method is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
        looper.onTeleopLoop(Timer.getFPGATimestamp());
    }
    
    
    /** This method is called once when the robot is disabled. */
    @Override
    public void disabledInit() {
        looper.startDisabled(Timer.getFPGATimestamp());
    }
    
    
    /** This method is called periodically when disabled. */
    @Override
    public void disabledPeriodic() {
        looper.onDisabledLoop(Timer.getFPGATimestamp());
    }

}
