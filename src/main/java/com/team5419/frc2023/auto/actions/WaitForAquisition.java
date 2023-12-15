package com.team5419.frc2023.auto.actions;

public class WaitForAquisition implements Action {
    // EndEffector effector;

    public WaitForAquisition() {
        // effector = EndEffector.getInstance();
    }

    @Override
    public boolean isFinished() {
        // return effector.hasGamePiece()
        return true;
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
