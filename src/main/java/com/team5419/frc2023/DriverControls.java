package com.team5419.frc2023;

import com.team5419.frc2023.loops.Loop;
import com.team5419.frc2023.subsystems.Arm;
import com.team5419.frc2023.subsystems.Drive;
import com.team5419.frc2023.subsystems.Intake;
import com.team5419.frc2023.subsystems.Superstructure;
import com.team5419.frc2023.subsystems.Wrist;
import com.team5419.lib.io.Xbox;

import edu.wpi.first.math.geometry.Rotation2d;

import java.util.Arrays;

public class DriverControls implements Loop {

    public static DriverControls mInstance = null;

    public static DriverControls getInstance() {
        if (mInstance == null) {
            mInstance = new DriverControls();
        }
        return mInstance;
    }

    Xbox driver, operator;

    private final Superstructure ss = Superstructure.getInstance();

    private final SubsystemManager subsystems;
    public SubsystemManager getSubsystems() {return subsystems;}
    private Drive swerve;

    public DriverControls() {
        driver = new Xbox(Ports.kDriver);
        operator = new Xbox(Ports.kOperator);

        driver.setDeadband(0.15);
        operator.setDeadband(0.6);

        Arm arm = Arm.getInstance();
        Wrist wrist = Wrist.getInstance();
        Intake intake = Intake.getInstance();
        swerve = Drive.getInstance();

        subsystems = new SubsystemManager(Arrays.asList(
            swerve, ss, arm, wrist, intake
        ));
    }

    @Override
    public void onStart(double timestamp) {

    }

    @Override
    public void onLoop(double timestamp) {
        driver.update();
        operator.update();
        handleDrive();
        twoControllerMode();
    }

    @Override
    public void onStop(double timestamp) {
        subsystems.stop();
    }

    private void handleDrive() {
        if(driver.getYButtonPressed()) {
            swerve.zeroGyro(Rotation2d.fromDegrees(-90));
        }

        if(driver.xButton.isBeingPressed()) {
            swerve.lockPose();
        } else if (!driver.aButton.isBeingPressed()) {
            swerve.feedTeleopSetpoints(driver::getLeftX, driver::getLeftY, driver::getRightX, driver::getLeftBumper);
        }

        if(driver.POV0.isBeingPressed()) {
            swerve.setForceOrientSetpoint(0);
        } else if (driver.POV180.isBeingPressed()) {
            swerve.setForceOrientSetpoint(180);
        }
    }

    private void twoControllerMode() {
        if (operator.rightBumper.wasActivated() && !ss.isGroundIntaking()) {
            ss.shelfIntakeState();
        } else if (operator.rightBumper.wasActivated() && ss.isGroundIntaking()) {
            ss.groundIntakeState();
        } else if (operator.leftBumper.wasActivated()) {
            ss.intake.setState(Intake.State.OUTTAKE);
        } else if (operator.bButton.wasActivated()) {
            ss.stowState();
        } else if (operator.aButton.wasActivated()) {
            ss.scoreL1PoseState();
        } else if (operator.xButton.wasActivated()) {
            ss.scoreL2PoseState();
        } else if (operator.yButton.wasActivated()) {
            ss.scoreL3PoseState();
        } else if (operator.POV0.wasActivated()) {
            ss.setIsCube(!ss.getIsCube());
        } else if (operator.POV180.wasActivated()) {
            ss.setGroundIntaking(!ss.isGroundIntaking());
        }
    }
}
