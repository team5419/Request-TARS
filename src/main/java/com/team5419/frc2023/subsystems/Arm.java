package com.team5419.frc2023.subsystems;

import com.ctre.phoenix6.signals.NeutralModeValue;
import com.team254.lib.util.Util;
import com.team5419.frc2023.constants.*;
import com.team5419.frc2023.loops.ILooper;
import com.team5419.frc2023.loops.Loop;
import com.team5419.lib.requests.Request;

public class Arm extends ServoMotorSubsystem {
    private static Arm mInstance;
    public static Arm getInstance(){
        if (mInstance == null) {
            mInstance = new Arm(kArmConstants);
        }
        return mInstance;
    }

    private static final ServoMotorSubsystemConstants kArmConstants = Constants.Arm.kArmServoConstants;

    protected Arm(ServoMotorSubsystemConstants constants) {
        super(constants);
        setNeutralBrake(true);
        zeroSensors();
    }

    private void setNeutralBrake(boolean isEnabled) {
        setNeutralMode(isEnabled ? NeutralModeValue.Brake : NeutralModeValue.Coast);
    }

    @Override
    public void registerEnabledLoops(ILooper mEnabledLooper) {
        mEnabledLooper.register(new Loop() {
            @Override
            public void onStart(double timestamp) {

            }

            @Override
            public void onLoop(double timestamp) {

            }

            @Override
            public void onStop(double timestamp) {
                stop();
                setNeutralBrake(true);
            }
        });
    }

    public Request angleRequest(double angle) {
        return new Request() {
            @Override
            public void act() {
                setSetpointMotionMagic(angle);
            }

            @Override
            public boolean isFinished() {
                return Util.epsilonEquals(mPeriodicIO.position_units, angle, Constants.Arm.kAngleTolerance);
            }

        };
    }


}
