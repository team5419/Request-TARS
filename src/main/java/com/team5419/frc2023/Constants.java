package com.team5419.frc2023;

import static com.team5419.frc2023.subsystems.ServoMotorSubsystem.ServoMotorSubsystemConstants;

public class Constants {

    // Time step for your system
    public static final double kLooperDt = 0.02;

    public static final class Arm {
        public static final ServoMotorSubsystemConstants kArmServoConstants = new ServoMotorSubsystemConstants();

        static {
            kArmServoConstants.kName = "Arm";

            kArmServoConstants.kMasterConstants.id = Ports.kArm;
            kArmServoConstants.kMasterConstants.invert_motor = false;
            kArmServoConstants.kMasterConstants.invert_sensor_phase = false;

            kArmServoConstants.kHomePosition = -9.598;
            kArmServoConstants.kMinUnitsLimit = -9.598;
            kArmServoConstants.kMaxUnitsLimit = 113.706;

            kArmServoConstants.kTicksPerUnitDistance = (2048.0 / 360.0) * 240.0; //encoder ticks per rev * gear ratio
            kArmServoConstants.kKp = 0.1; // Raw output / raw error
            kArmServoConstants.kKi = 0.0; // Raw output / sum of raw error
            kArmServoConstants.kKd = 0.0; // Raw output / (err - prevErr)
            kArmServoConstants.kKf = 0.0; // Raw output / velocity in ticks/100ms
            kArmServoConstants.kKa = 0.0; // Raw output / accel in (ticks/100ms) /
            kArmServoConstants.kMaxIntegralAccumulator = 0;
            kArmServoConstants.kIZone = 0; // Ticks
            kArmServoConstants.kDeadband = 0;

            kArmServoConstants.kPositionKp = 0.0;
            kArmServoConstants.kPositionKi = 0;
            kArmServoConstants.kPositionKd = 0.0;
            kArmServoConstants.kPositionKf = 0;
            kArmServoConstants.kPositionMaxIntegralAccumulator = 0;
            kArmServoConstants.kPositionIZone = 0; // Ticks
            kArmServoConstants.kPositionDeadband = 0; //

            kArmServoConstants.kCruiseVelocity = 25000; // Ticks / 100ms
            kArmServoConstants.kAcceleration = 250000; // Ticks / 100ms / s
            kArmServoConstants.kRampRate = 0.0; // s

            kArmServoConstants.kEnableSupplyCurrentLimit = true;
            kArmServoConstants.kSupplyContinuousCurrentLimit = 40; // amps
            kArmServoConstants.kSupplyPeakCurrentLimit = 40; // amps
            kArmServoConstants.kSupplyPeakCurrentDuration = 10; // milliseconds

            kArmServoConstants.kMaxVoltage = 12.0;

            kArmServoConstants.kRecoverPositionOnReset = false;
        }

        public static final double kAngleTolerance = 3.0;
        public static final double kWristSafeAngle = 10;

    }

    public static final class Wrist {
        public static final ServoMotorSubsystemConstants kWristServoConstants = new ServoMotorSubsystemConstants();

        static {
            kWristServoConstants.kName = "Wrist";

            kWristServoConstants.kMasterConstants.id = Ports.kWrist;
            kWristServoConstants.kMasterConstants.invert_motor = false;
            kWristServoConstants.kMasterConstants.invert_sensor_phase = false;

            kWristServoConstants.kHomePosition = -9.598;
            kWristServoConstants.kMinUnitsLimit = -9.598;
            kWristServoConstants.kMaxUnitsLimit = 113.706;

            kWristServoConstants.kTicksPerUnitDistance = (2048.0 / 360.0) * 40.0; //encoder ticks per rev * gear ratio
            kWristServoConstants.kKp = 0.1; // Raw output / raw error
            kWristServoConstants.kKi = 0.0; // Raw output / sum of raw error
            kWristServoConstants.kKd = 0.0; // Raw output / (err - prevErr)
            kWristServoConstants.kKf = 0.0; // Raw output / velocity in ticks/100ms
            kWristServoConstants.kKa = 0.0; // Raw output / accel in (ticks/100ms) /
            kWristServoConstants.kMaxIntegralAccumulator = 0;
            kWristServoConstants.kIZone = 0; // Ticks
            kWristServoConstants.kDeadband = 0;

            kWristServoConstants.kPositionKp = 0.0;
            kWristServoConstants.kPositionKi = 0;
            kWristServoConstants.kPositionKd = 0.0;
            kWristServoConstants.kPositionKf = 0;
            kWristServoConstants.kPositionMaxIntegralAccumulator = 0;
            kWristServoConstants.kPositionIZone = 0; // Ticks
            kWristServoConstants.kPositionDeadband = 0; //

            kWristServoConstants.kCruiseVelocity = 25000; // Ticks / 100ms
            kWristServoConstants.kAcceleration = 250000; // Ticks / 100ms / s
            kWristServoConstants.kRampRate = 0.0; // s

            kWristServoConstants.kEnableSupplyCurrentLimit = true;
            kWristServoConstants.kSupplyContinuousCurrentLimit = 40; // amps
            kWristServoConstants.kSupplyPeakCurrentLimit = 40; // amps
            kWristServoConstants.kSupplyPeakCurrentDuration = 10; // milliseconds

            kWristServoConstants.kMaxVoltage = 12.0;

            kWristServoConstants.kRecoverPositionOnReset = false;
        }

        public static final double kAngleTolerance = 3.0;

    }

}
