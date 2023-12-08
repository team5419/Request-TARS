package com.team254.lib.drivers;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.controls.ControlRequest;
import com.ctre.phoenix6.hardware.TalonFX;

/**
 * This class is a thin wrapper around the CANTalon that reduces CAN bus / CPU overhead by skipping duplicate set
 * commands. (By default the Talon flushes the Tx buffer on every set call).
 */
public class LazyTalonFX extends TalonFX {
    protected ControlRequest mLastControlMode = null;

    public LazyTalonFX(int deviceNumber) {
        super(deviceNumber);
    }

    public LazyTalonFX(int deviceNumber, String canbus) {
        super(deviceNumber, canbus);
    }

    public ControlRequest getLastSet() {
        return mLastControlMode;
    }

    @Override
    public StatusCode setControl(ControlRequest mode) {
        if (mode != mLastControlMode) {
            mLastControlMode = mode;
            return super.setControl(mode);
        }

        return StatusCode.OK;
    }
}