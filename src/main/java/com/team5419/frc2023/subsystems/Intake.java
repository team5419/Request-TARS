package com.team5419.frc2023.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.team5419.frc2023.Ports;
import com.team5419.frc2023.loops.ILooper;
import com.team5419.frc2023.loops.Loop;

public class Intake extends Subsystem {

    public static Intake mInstance = null;

    public static Intake getInstance() {
        if (mInstance == null) {
            return mInstance = new Intake();
        }
        return mInstance;
    }

    protected final TalonFX motor = new TalonFX(Ports.kIntake);

    public Intake() {

    }

    @Override
    public void registerEnabledLoops(ILooper mEnabledLooper) {
        mEnabledLooper.register(new Loop() {
            @Override
            public void onStart(double timestamp) {
                stop();
            }

            @Override
            public void onLoop(double timestamp) {

            }

            @Override
            public void onStop(double timestamp) {
                stop();
            }
        });
    }

    @Override
    public void stop() {
        motor.stopMotor();
    }

}
