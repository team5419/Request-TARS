package com.team5419.frc2023.auto.actions;

import com.team5419.frc2023.subsystems.Superstructure;

public class WaitForSuperstructureAction implements Action {
    private Superstructure superstructure;

    public WaitForSuperstructureAction() {
        superstructure = Superstructure.getInstance();
    }

    @Override
    public boolean isFinished() {
        return superstructure.areAllRequestsCompleted();
    }

    @Override
    public void start() {
    }

    @Override
    public void update() {
    }

    @Override
    public void done() {
    }

}
