package com.team5419.frc2023;

import com.team5419.frc2023.loops.Loop;
import com.team5419.frc2023.subsystems.Arm;
import com.team5419.frc2023.subsystems.Intake;
import com.team5419.frc2023.subsystems.Superstructure;
import com.team5419.frc2023.subsystems.Wrist;
import com.team5419.lib.io.Xbox;

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

    private final Arm arm = Arm.getInstance();
    private final Wrist wrist = Wrist.getInstance();
    private final Intake intake = Intake.getInstance();
    private final Superstructure s = Superstructure.getInstance();

    private final SubsystemManager subsystems;
    public SubsystemManager getSubsystems() {return subsystems;}

    public DriverControls() {
        driver = new Xbox(Ports.kDriver);
        operator = new Xbox(Ports.kOperator);

        driver.setDeadband(0.0);
        operator.setDeadband(0.6);

        subsystems = new SubsystemManager(
                Arrays.asList(s, arm, wrist, intake)
        );
    }

    @Override
    public void onStart(double timestamp) {

    }

    @Override
    public void onLoop(double timestamp) {
        driver.update();
        operator.update();
        twoControllerMode();
    }

    @Override
    public void onStop(double timestamp) {
        subsystems.stop();
    }

    private void twoControllerMode() {
        if (operator.rightBumper.wasActivated()) {
            s.groundIntakeState();
        } else if (operator.bButton.wasActivated()) {
            s.stowState();
        } else if (operator.xButton.wasActivated()) {
            s.scoreL2PoseState();
        } else if (operator.leftBumper.wasActivated()) {
            s.intake.setState(Intake.State.OUTTAKE);
        } else if (operator.POV0.wasActivated()) {
            s.setIsCube(!s.getIsCube());
        }
    }
}
