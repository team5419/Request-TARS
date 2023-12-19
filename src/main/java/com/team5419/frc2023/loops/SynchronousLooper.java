package com.team5419.frc2023.loops;

import com.team5419.frc2023.SubsystemManager;

import edu.wpi.first.wpilibj.DriverStation;

import java.util.ArrayList;
import java.util.List;

public class SynchronousLooper implements ILooper {
    private final SubsystemManager subsystems;
    private final List<Loop> autoLoops = new ArrayList<>();
    private final List<Loop> teleopLoops = new ArrayList<>();
    private final List<Loop> disabledLoops = new ArrayList<>();
    private EnabledMode lastEnabledMode = EnabledMode.TELEOP;

    public SynchronousLooper(SubsystemManager subsystems) {
        this.subsystems = subsystems;
    }

    public void registerAutoLoop(Loop loop) {
        autoLoops.add(loop);
    }

    public void registerTeleopLoop(Loop loop) {
        teleopLoops.add(loop);
    }

    public void registerDisabledLoop(Loop loop) {
        disabledLoops.add(loop);
    }

    /**
     * Register a Loop that will run throughout all periods
     * (auto, teleop, and disabled).
     */
    @Override
    public void register(Loop loop) {
        registerAutoLoop(loop);
        registerTeleopLoop(loop);
        registerDisabledLoop(loop);
    }

    private void startEnabled(double timestamp, List<Loop> auxiliaryLoops) {
        disabledLoops.forEach(l -> l.onStop(timestamp));
        subsystems.onStart(timestamp);
        auxiliaryLoops.forEach(l -> l.onStart(timestamp));
    }

    public void startAuto(double timestamp) {
        startEnabled(timestamp, autoLoops);
        lastEnabledMode = EnabledMode.AUTO;
    }

    public void startTeleop(double timestamp) {
        startEnabled(timestamp, teleopLoops);
        lastEnabledMode = EnabledMode.TELEOP;
    }

    public void startDisabled(double timestamp) {
        switch (lastEnabledMode) {
            case AUTO:
                autoLoops.forEach(l -> l.onStop(timestamp));
                break;
            case TELEOP:
                teleopLoops.forEach(l -> l.onStop(timestamp));
                break;
        }
        subsystems.onStop(timestamp);
        subsystems.stop();
        disabledLoops.forEach(l -> l.onStart(timestamp));
    }

    private void onEnabledLoop(double timestamp, List<Loop> auxiliaryLoops) {
        subsystems.readPeriodicInputs();
        auxiliaryLoops.forEach(l -> l.onLoop(timestamp));
        subsystems.onLoop(timestamp);
        subsystems.writePeriodicOutputs();
        subsystems.outputTelemetry(false);
    }

    public void onAutoLoop(double timestamp) {
        onEnabledLoop(timestamp, autoLoops);
    }

    public void onTeleopLoop(double timestamp) {
        onEnabledLoop(timestamp, teleopLoops);
    }

    public void onDisabledLoop(double timestamp) {
        subsystems.readPeriodicInputs();
        disabledLoops.forEach(l -> l.onLoop(timestamp));
        subsystems.writePeriodicOutputs();
        subsystems.outputTelemetry(true);
    }

    private enum EnabledMode {
        AUTO, TELEOP
    }
}
