package com.team5419.frc2023;

import com.team5419.frc2023.loops.Loop;
import com.team5419.frc2023.subsystems.Arm;
import com.team5419.frc2023.subsystems.Wrist;
import com.team5419.lib.io.Xbox;

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

    @Override
    public void onStart(double timestamp) {

    }

    @Override
    public void onLoop(double timestamp) {

    }

    @Override
    public void onStop(double timestamp) {

    }
}
