package com.team5419.frc2023;

import com.team5419.frc2023.loops.ILooper;
import com.team5419.frc2023.loops.Loop;
import com.team5419.frc2023.subsystems.Subsystem;

import java.util.ArrayList;
import java.util.List;

public class SubsystemManager extends Subsystem implements ILooper, Loop {

    private final List<Subsystem> mAllSubsystems;
    private final List<Loop> mLoops = new ArrayList<>();

    public SubsystemManager(List<Subsystem> allSubsystems) {
        mAllSubsystems = allSubsystems;
        mAllSubsystems.forEach(s -> s.registerEnabledLoops(this));
    }
    @Override
    public void register(Loop loop) {
        mLoops.add(loop);
    }

    @Override
    public void onStart(double timestamp) {
        mLoops.forEach(loop -> loop.onStart(timestamp));
    }

    @Override
    public void onLoop(double timestamp) {
        mLoops.forEach(loop -> loop.onLoop(timestamp));
    }

    @Override
    public void onStop(double timestamp) {
        mLoops.forEach(loop -> loop.onStop(timestamp));
    }

    @Override
    public void readPeriodicInputs() {
        super.readPeriodicInputs();
        mAllSubsystems.forEach(Subsystem::readPeriodicInputs);
    }

    @Override
    public void writePeriodicOutputs() {
        super.writePeriodicOutputs();
        mAllSubsystems.forEach(Subsystem::writePeriodicOutputs);
    }

    @Override
    public void outputTelemetry(boolean disabled) {
        super.outputTelemetry(disabled);
        mAllSubsystems.forEach(s -> s.outputTelemetry(disabled));
    }

    @Override
    public void stop() {
        super.stop();
        mAllSubsystems.forEach(Subsystem::stop);
    }

    @Override
    public void zeroSensors() {
        super.zeroSensors();
        mAllSubsystems.forEach(Subsystem::zeroSensors);
    }
}
