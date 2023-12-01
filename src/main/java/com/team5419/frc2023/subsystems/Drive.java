package com.team5419.frc2023.subsystems;

import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrain;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.team5419.frc2023.constants.Constants;
import com.team5419.frc2023.constants.TunerConstants;
import com.team5419.frc2023.loops.ILooper;
import com.team5419.frc2023.loops.Loop;

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
    SwerveRequest.PointWheelsAt lock = new SwerveRequest.PointWheelsAt();
    // Telemetry logger = new Telemetry(MaxSpeed);

    private static Drive sInstance;

    public static Drive getInstance() {
        if (sInstance == null) {
            sInstance = new Drive();
        }
        return sInstance;
    }

    private Drive() {
        swerve =  new SwerveDrivetrain(TunerConstants.DrivetrainConstants, Constants.kLooperDt, TunerConstants.FrontLeft, TunerConstants.FrontRight, TunerConstants.FrontRight, TunerConstants.BackLeft);
    }

    @Override
    public void registerEnabledLoops(ILooper enabledLooper) {
        enabledLooper.register(new Loop() {

            @Override
            public void onStart(double timestamp) {
                mDesiredRequest = drive;
                mControlState = DriveControlState.OPEN_LOOP;
            }

            @Override
            public void onLoop(double timestamp) {
                synchronized(Drive.this) {
                    switch (mControlState) {
                        case OPEN_LOOP:
                        case AUTOBALANCE:
                            break;
                        case PATH_FOLLOWING:
                            //Todo: update path following
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
                mDesiredRequest = stop;
            }
            
        });
    }

    @Override
    public void outputTelemetry() {
        // TODO Auto-generated method stub
        super.outputTelemetry();
    }

    @Override
    public void readPeriodicInputs() {
        // TODO Auto-generated method stub
        super.readPeriodicInputs();
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        mDesiredRequest = stop;
        super.stop();
    }

    @Override
    public void writePeriodicOutputs() {
        // TODO Auto-generated method stub
        super.writePeriodicOutputs();
    }

    @Override
    public void zeroSensors() {
        // TODO Auto-generated method stub
        super.zeroSensors();
    }
}
