package com.team5419.frc2023.subsystems;

/*
   Angles given such that ccw is negative and cw is positive.
   Arm zero is vertical with the wrist pivot closest to the ground.
   Wrist zero is vertical with the intake furthest from the ground when the arm is vertical.
*/
public class SuperstructureGoal {
    private static SuperstructureGoal mInstance;
    public static SuperstructureGoal getInstance() {
        if (mInstance == null) {
            mInstance = new SuperstructureGoal();
        }
        return mInstance;
    }

    private double arm;
    private double wrist;

    public SuperstructureGoal(double arm, double wrist) {
        this.arm = arm;
        this.wrist = wrist;
    }

    // Empty superstructure goal that will be used as the default constructor
    public SuperstructureGoal() {this(0,0);}

    public double getArm() {
        return arm;
    }

    public double getWrist() {
        return wrist;
    }

    public static final SuperstructureGoal STOW = new SuperstructureGoal();

    public static final SuperstructureGoal GROUND_INTAKE_CUBE = new SuperstructureGoal(-15, 30);

    public static final SuperstructureGoal SCORE_CUBE_L2 = new SuperstructureGoal(45, -30);
    public static final SuperstructureGoal SCORE_CONE_L2 = new SuperstructureGoal(60, -180);

}
