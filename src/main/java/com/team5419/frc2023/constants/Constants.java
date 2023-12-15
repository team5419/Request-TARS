package com.team5419.frc2023.constants;

import com.team254.lib.drivers.CanDeviceId;
import com.team5419.frc2023.Ports;
import com.team5419.frc2023.subsystems.ServoMotorSubsystem.ServoMotorSubsystemConstants;

import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.constraint.CentripetalAccelerationConstraint;

// import static com.team5419.frc2023.subsystems.ServoMotorSubsystem.ServoMotorSubsystemConstants;

public class Constants {

    // Time step for your system
    public static final double kLooperDt = 0.02;

    public static final class Arm {
        public static final ServoMotorSubsystemConstants kArmServoConstants = new ServoMotorSubsystemConstants();

        static {
            kArmServoConstants.kName = "Arm";

            kArmServoConstants.kMasterConstants.id = new CanDeviceId(Ports.kArm);
            kArmServoConstants.kMasterConstants.counterClockwisePositive = false;
            kArmServoConstants.kMasterConstants.invert_sensor_phase = false;

            kArmServoConstants.kHomePosition = 0;
            kArmServoConstants.kMinUnitsLimit = -80;
            kArmServoConstants.kMaxUnitsLimit = 80;

            // kArmServoConstants.kTicksPerUnitDistance = (2048.0 / 360.0) * 240.0; //encoder ticks per rev * gear ratio
            kArmServoConstants.kRotationsPerUnitDistance = 240.0; 
            kArmServoConstants.kKp = 0.1; // Raw output / raw error
            kArmServoConstants.kKi = 0.0; // Raw output / sum of raw error
            kArmServoConstants.kKd = 0.0; // Raw output / (err - prevErr)
            kArmServoConstants.kKf = 0.0; // Raw output / velocity in ticks/100ms
            kArmServoConstants.kKa = 0.0; // Raw output / accel in (ticks/100ms) /
            kArmServoConstants.kDeadband = 0;

            kArmServoConstants.kPositionKp = 0.0;
            kArmServoConstants.kPositionKi = 0;
            kArmServoConstants.kPositionKd = 0.0;
            kArmServoConstants.kPositionKf = 0;
            kArmServoConstants.kPositionDeadband = 0; //

            kArmServoConstants.kCruiseVelocity = 25000; // Ticks / 100ms
            kArmServoConstants.kAcceleration = 250000; // Ticks / 100ms / s
            kArmServoConstants.kRampRate = 0.0; // s

            kArmServoConstants.kEnableSupplyCurrentLimit = true;
            kArmServoConstants.kSupplyCurrentLimit = 40; // amps
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

            kWristServoConstants.kMasterConstants.id = new CanDeviceId(Ports.kWrist);
            kWristServoConstants.kMasterConstants.counterClockwisePositive = false;
            kWristServoConstants.kMasterConstants.invert_sensor_phase = false;

            kWristServoConstants.kHomePosition = 0;
            kWristServoConstants.kMinUnitsLimit = -215;
            kWristServoConstants.kMaxUnitsLimit = 215;

            // kWristServoConstants.kTicksPerUnitDistance = (2048.0 / 360.0) * 40.0; //encoder ticks per rev * gear ratio
            kWristServoConstants.kRotationsPerUnitDistance = 40.0;
            kWristServoConstants.kKp = 0.1; // Raw output / raw error
            kWristServoConstants.kKi = 0.0; // Raw output / sum of raw error
            kWristServoConstants.kKd = 0.0; // Raw output / (err - prevErr)
            kWristServoConstants.kKf = 0.0; // Raw output / velocity in ticks/100ms
            kWristServoConstants.kKa = 0.0; // Raw output / accel in (ticks/100ms) /
            kWristServoConstants.kDeadband = 0;

            kWristServoConstants.kPositionKp = 0.0;
            kWristServoConstants.kPositionKi = 0;
            kWristServoConstants.kPositionKd = 0.0;
            kWristServoConstants.kPositionKf = 0;
            kWristServoConstants.kPositionDeadband = 0; //

            kWristServoConstants.kCruiseVelocity = 25000; // Ticks / 100ms
            kWristServoConstants.kAcceleration = 250000; // Ticks / 100ms / s
            kWristServoConstants.kRampRate = 0.0; // s

            kWristServoConstants.kEnableSupplyCurrentLimit = true;
            kWristServoConstants.kSupplyCurrentLimit = 40; // amps

            kWristServoConstants.kMaxVoltage = 12.0;

            kWristServoConstants.kRecoverPositionOnReset = false;
        }

        public static final double kAngleTolerance = 3.0;

    }

    public static final class AutoConstants {
        public static final double kPXController = 6.7;
        public static final double kPYController = 6.7;

        public static final double kDXController = 0.0;
        public static final double kDYController = 0.0;

        public static final double kPThetaController = 2.0;

        // Constraint for the motion profilied robot angle controller (Radians)
        public static final double kMaxAngularSpeed = 2.0 * Math.PI; 
        public static final double kMaxAngularAccel = 2.0 * Math.PI * kMaxAngularSpeed;

        public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
                        kMaxAngularSpeed, kMaxAngularAccel);

        // Static factory for creating trajectory configs
        public static TrajectoryConfig createConfig(double maxSpeed, double maxAccel, double startSpeed, double endSpeed) {
                TrajectoryConfig config = new TrajectoryConfig(maxSpeed, maxAccel);
                config.setStartVelocity(startSpeed);
                config.setEndVelocity(endSpeed);
                config.addConstraint(new CentripetalAccelerationConstraint(10.0));
                return config;
        }
    }

    public static final class SnapConstants {
        public static final double kP = 6.0;
        public static final double kI = 0.5;
        public static final double kD = 0.2;
        public static final double snapTimeout = 0.25;
        public static final double snapEpsilon = 1.0;
}

}
