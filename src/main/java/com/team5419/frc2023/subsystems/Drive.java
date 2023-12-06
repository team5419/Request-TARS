package com.team5419.frc2023.subsystems;

import java.util.function.Supplier;

import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrain;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.mechanisms.swerve.utility.PhoenixPIDController;
import com.team5419.frc2023.constants.Constants;
import com.team5419.frc2023.constants.TunerConstants;
import com.team5419.frc2023.loops.ILooper;
import com.team5419.frc2023.loops.Loop;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class Drive extends Subsystem {
    public enum DriveControlState {
        OPEN_LOOP,
        FORCE_ORIENT,
        PATH_FOLLOWING,
        AUTOBALANCE
    }

    private SwerveDrivetrain swerve;
    private DriveControlState mControlState;
    private SwerveRequest mDesiredRequest;
    
    SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric().withIsOpenLoop(true);//.withDeadband(0.5).withRotationalDeadband(1); // I want field-centric driving in open loop
    SwerveRequest.SwerveDriveBrake stop = new SwerveRequest.SwerveDriveBrake();
    SwerveRequest.PointWheelsAt pointModsForward = new SwerveRequest.PointWheelsAt().withModuleDirection(Rotation2d.fromDegrees(0));
    SwerveRequest.FieldCentricFacingAngle facingAngle = new SwerveRequest.FieldCentricFacingAngle().withIsOpenLoop(true);
    // Telemetry logger = new Telemetry(MaxSpeed);

    private static Drive sInstance;

    public Rotation2d forceOrientSetpoint;
    private boolean hasExitedFacing = false;

    public static Drive getInstance() {
        if (sInstance == null) {
            sInstance = new Drive();
        }
        return sInstance;
    }

    private Drive() {
        swerve =  new SwerveDrivetrain(TunerConstants.DrivetrainConstants, TunerConstants.FrontLeft, TunerConstants.FrontRight, TunerConstants.BackRight, TunerConstants.BackLeft);
        forceOrientSetpoint = new Rotation2d();
        PhoenixPIDController headingController = facingAngle.HeadingController;
        headingController.setPID(4, 0, 0);
        headingController.setTolerance(Math.PI / 16, 0.5);
    }

    @Override
    public void registerEnabledLoops(ILooper enabledLooper) {
        enabledLooper.register(new Loop() {

            @Override
            public void onStart(double timestamp) {
                mDesiredRequest = drive;
                mControlState = DriveControlState.OPEN_LOOP;
                zeroGyro();
            }

            @Override
            public void onLoop(double timestamp) {
                synchronized(Drive.this) {
                    switch (mControlState) {
                        case OPEN_LOOP:
                        case AUTOBALANCE:
                            break;
                        case PATH_FOLLOWING:
                            updatePathFollower();
                            break;
                        case FORCE_ORIENT:
                            if(facingAngle.HeadingController.atSetpoint() && !hasExitedFacing) {
                                hasExitedFacing = true;
                                mControlState = DriveControlState.OPEN_LOOP;
                            }
                            break;
                        
                        default:
                            stop();
                            break;
                    }


                    swerve.setControl(mDesiredRequest);
                }
            }

            @Override
            public void onStop(double timestamp) {
                // mDesiredRequest = pointForward;
            }
            
        });
    }

    /**
     * All parameters expected to be normalized between 0 and 1;
     * @param x - x vel, in percent
     * @param y - y vel, in percent
     * @param rotation - rotation rate, in percent
     * @apiNote Values not checked, could exceed set max speed
     */
    public void feedTeleopSetpoints(Supplier<Double> x, Supplier<Double> y, Supplier<Double> rotation, Supplier<Boolean> slowMode) {
        if(mControlState != DriveControlState.OPEN_LOOP && mControlState != DriveControlState.FORCE_ORIENT) {
            mControlState = DriveControlState.OPEN_LOOP;
        }

        if(mControlState == DriveControlState.FORCE_ORIENT) {

            if (Math.abs(rotation.get()) > 0.5) { // If the controllers are giving an input over some value, the exit out of force orient
                mControlState = DriveControlState.OPEN_LOOP;
                return;
            } 

            setFaceAngleValues(x, y, slowMode); 
            mDesiredRequest = facingAngle;

        } else {

            setDriveRequestValues(x, y, rotation, slowMode);
            mDesiredRequest = drive;

        }

    }

    public void applyOpenLoop(Supplier<Double> x, Supplier<Double> y, Supplier<Double> rotation, Supplier<Boolean> slowMode) {
        if(mControlState != DriveControlState.OPEN_LOOP) {
            mControlState = DriveControlState.OPEN_LOOP;
        }

        setDriveRequestValues(x, y, rotation, slowMode);
        mDesiredRequest = drive;
    }

    public void setForceOrientSetpoint(double targetDegrees) {
      setForceOrientSetpoint(Rotation2d.fromDegrees(targetDegrees - 90));
    }

    public void setForceOrientSetpoint(Rotation2d target) {
        if(mControlState != DriveControlState.FORCE_ORIENT) {
            mControlState = DriveControlState.FORCE_ORIENT;
        }

        forceOrientSetpoint = target;
        facingAngle.withTargetDirection(target);
        facingAngle.HeadingController.reset();
        hasExitedFacing = false;
    }

    public synchronized void lockPose() {
        mDesiredRequest = stop;
    }

    public void zeroGyro(Translation2d translation, Rotation2d rotation) {
        swerve.seedFieldRelative(new Pose2d(translation, rotation));
    }

    public void zeroGyro(Rotation2d rotation) {
        zeroGyro(new Translation2d(), rotation);
    }

    public void zeroGyro() {
        swerve.seedFieldRelative();
    }

    private void updatePathFollower() {
        // TODO - probably with pathplanner
    }

    // @Override
    // public void outputTelemetry() {
    //     // TODO Auto-generated method stub
    // }

    // @Override
    // public void readPeriodicInputs() {
    //     // TODO Auto-generated method stub
    // }

    // @Override
    // public void writePeriodicOutputs() {
    //     // TODO Auto-generated method stub
    // }
    
    @Override
    public void stop() {
        // mDesiredRequest = stop;
    }

    @Override
    public void zeroSensors() {
        // swerve.tareEverything();
    }

    public void setDriveRequestValues(Supplier<Double> x, Supplier<Double> y, Supplier<Double> rotation, Supplier<Boolean> slowMode) {
        drive.withVelocityX(-x.get() * TunerConstants.kMaxSpeed * (slowMode.get() ? 0.25 : 1))
            .withVelocityY(y.get() * TunerConstants.kMaxSpeed * (slowMode.get() ? 0.25 : 1))
            .withRotationalRate(-rotation.get() * TunerConstants.kMaxAngularRate * (slowMode.get() ? 0.5 : 1));
    }

    public void setFaceAngleValues(Supplier<Double> x, Supplier<Double> y, Supplier<Boolean> slowMode) {
        facingAngle.withVelocityX(-x.get() * TunerConstants.kMaxSpeed * (slowMode.get() ? 0.25 : 1))
            .withVelocityY(y.get() * TunerConstants.kMaxSpeed * (slowMode.get() ? 0.25 : 1))
            .withTargetDirection(forceOrientSetpoint);
    }
}
