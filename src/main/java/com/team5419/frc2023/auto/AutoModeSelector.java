package com.team5419.frc2023.auto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Optional;

import com.team5419.frc2023.auto.modes.*;

public class AutoModeSelector {
    public enum DesiredMode {
        DO_NOTHING, 
        TEST_PATH_AUTO
    }

    private DesiredMode mCachedDesiredMode = DesiredMode.DO_NOTHING;

    private Optional<AutoModeBase> mAutoMode = Optional.empty();

    private static SendableChooser<DesiredMode> mModeChooser = new SendableChooser<>();

    public AutoModeSelector() {
        mModeChooser.setDefaultOption("Test Path", DesiredMode.TEST_PATH_AUTO);
        // mModeChooser.setDefaultOption("Do Nothing", DesiredMode.DO_NOTHING);
        SmartDashboard.putData("Auto Mode", mModeChooser);
    }

    public void updateModeCreator(boolean force_regen) {
        DesiredMode desiredMode = mModeChooser.getSelected();
        if (desiredMode == null) {
            desiredMode = DesiredMode.DO_NOTHING;
        }
        if (mCachedDesiredMode != desiredMode || force_regen) {
            System.out.println("Auto selection changed, updating creator: desiredMode -> " + desiredMode.name());
            mAutoMode = getAutoModeForParams(desiredMode);
        }
        mCachedDesiredMode = desiredMode;
    }

    private Optional<AutoModeBase> getAutoModeForParams(DesiredMode mode) {

        switch (mode) {
            case DO_NOTHING:
                return Optional.of(new DoNothingMode());
            
            case TEST_PATH_AUTO:
                return Optional.of(new TestPathMode());
        
            // case ONE_BALANCE:
            //     return Optional.of(new OneBalance());
            
            // case TWO_PICKUP_BALANCE:
            //     return Optional.of(new TwoPickupBalance());
            
            // case CC_ONE_PICKUP_BALANCE:
            //     return Optional.of(new OneBalanceCC());

            // case CC_TWO_PICKUP_BALANCE:
            //     return Optional.of(new TwoPickupBalanceCC());
        
            // case TWO_PICKUP:
            //     return Optional.of(new TwoPickup());
                
            // case TWO_BALANCE:
            //     return Optional.of(new TwoBalance());

            // case ONE_PIECE:
            //     return Optional.of(new OnePiece());

            // case CC_ONE_PIECE:
            //     return Optional.of(new OnePieceCC());
            
            // case MIDDLE_BALANCE:
            //     return Optional.of(new MiddleBalance());

            // case THREE_PIECE:
            //     return Optional.of(new ThreePiece());
                
            // case CC_THREE_PIECE:
            //     return Optional.of(new ThreePieceCC());
                
            // case TWO_MIDDLE_BALANCE:
            //     return Optional.of(new TwoMiddleBalance());
            default:
                System.out.println("ERROR: unexpected auto mode: " + mode);
                break;
        }

        System.err.println("No valid auto mode found for  " + mode);
        return Optional.empty();
    }

    public static SendableChooser<DesiredMode> getModeChooser() {
        return mModeChooser;
    }

    public DesiredMode getDesiredAutomode() {
        return mCachedDesiredMode;
    }

    public void reset() {
        mAutoMode = Optional.empty();
        mCachedDesiredMode = null;
    }

    public void outputToSmartDashboard() {
        SmartDashboard.putString("AutoModeSelected", mCachedDesiredMode.name());
    }

    public Optional<AutoModeBase> getAutoMode() {
        if (!mAutoMode.isPresent()) {
            return Optional.empty();
        }
        return mAutoMode;
    }

    public boolean isDriveByCamera() {
        return mCachedDesiredMode == DesiredMode.DO_NOTHING;
    }
}
