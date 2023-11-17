package com.team5419.frc2023.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.team254.lib.util.Util;
import com.team5419.frc2023.Constants;
import com.team5419.frc2023.loops.ILooper;
import com.team5419.frc2023.loops.Loop;
import com.team5419.lib.requests.Request;

public class Wrist extends ServoMotorSubsystem {
    public static Wrist mInstance;
    public static Wrist getInstance() {
        if (mInstance == null) {
            mInstance = new Wrist(kWristConstnats);
        }
        return  mInstance;
    }
    public static final ServoMotorSubsystemConstants kWristConstnats = Constants.Wrist.kWristServoConstants;
    protected Wrist(ServoMotorSubsystemConstants constants) {
        super(constants);
        zeroSensors();
        setNeutralBrake(true);
    }
    private void setNeutralBrake(boolean isEnabled) {
        NeutralMode mode = isEnabled ? NeutralMode.Brake : NeutralMode.Coast;
        mMaster.setNeutralMode(mode);
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
                return Util.epsilonEquals(mPeriodicIO.position_units, angle, Constants.Wrist.kAngleTolerance);
            }
        };
    }

}
