package com.team5419.frc2023.subsystems;

import com.ctre.phoenix6.signals.NeutralModeValue;
import com.team254.lib.util.Util;
import com.team5419.frc2023.constants.*;
import com.team5419.frc2023.loops.ILooper;
import com.team5419.frc2023.loops.Loop;
import com.team5419.lib.requests.Request;

import edu.wpi.first.hal.FRCNetComm.tResourceType;

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
                // setSetpointMotionMagic(angle);
                //! Disabled for now, as we dont have any insurance that the bot wont run into itself
            }

            @Override
            public boolean isFinished() {
                // return Util.epsilonEquals(mPeriodicIO.position_units, angle, Constants.Wrist.kAngleTolerance);
                return true;
            }
        };
    }

}
