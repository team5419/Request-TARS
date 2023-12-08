// package com.team5419.frc2023.subsystems;

// import com.team5419.frc2023.loops.ILooper;
// import com.team5419.frc2023.loops.Loop;
// import com.team5419.lib.requests.*;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// public class Superstructure extends Subsystem {
//     private static Superstructure mInstance = null;
//     public static Superstructure getInstance() {
//         if (mInstance == null) {
//             mInstance = new Superstructure();
//         }
//         return mInstance;
//     }

//     public final Arm arm = Arm.getInstance();
//     public final Wrist wrist = Wrist.getInstance();
//     public final Intake intake = Intake.getInstance();

//     private final RequestExecuter requestExecuter = new RequestExecuter();

//     public boolean getIsCube() {
//         return isCube;
//     }

//     public void setIsCube(boolean isCube) {
//         this.isCube = isCube;
//     }

//     public boolean isGroundIntaking() {
//         return isGroundIntaking;
//     }

//     public void setGroundIntaking(boolean groundIntaking) {
//         isGroundIntaking = groundIntaking;
//     }

//     public synchronized void request(Request r) {
//         requestExecuter.request(r);
//     }

//     public boolean areAllRequestsCompleted() {
//         return requestExecuter.isFinished();
//     }

//     private final Loop loop = new Loop() {
//         @Override
//         public void onStart(double timestamp) {
//             neutralState();
//         }

//         @Override
//         public void onLoop(double timestamp) {
//             synchronized (Superstructure.this) {
//                 requestExecuter.update();
//             }
//         }

//         @Override
//         public void onStop(double timestamp) {

//         }
//     };

//     @Override
//     public void stop() {
//         request(new EmptyRequest());
//     }

//     @Override
//     public void registerEnabledLoops(ILooper enabledLooper) {
//         enabledLooper.register(loop);
//     }

//     private boolean notifyDrivers = false;
//     public boolean needsToNotifyDrivers() {
//         if (notifyDrivers) {
//             notifyDrivers = false;
//             return true;
//         }
//         return false;
//     }

//     private boolean isCube = false;
//     private boolean isGroundIntaking = true;

//     public void neutralState() {
//         request(new ParallelRequest(
//                 new LambdaRequest(
//                         () -> {
//                             arm.stop();
//                             wrist.stop();
//                             intake.stop();
//                         }
//                 )
//         ));
//     }

//     public void stowState() {
//         SuperstructureGoal state = SuperstructureGoal.STOW;
//         request(new ParallelRequest(
//                 arm.angleRequest(state.getArm()),
//                 wrist.angleRequest(state.getWrist()),
//                 intake.stateRequest(Intake.State.IDLE)
//         ));
//     }

//     public void groundIntakeState() {
//         SuperstructureGoal state = isCube ? SuperstructureGoal.GROUND_INTAKE_CUBE : SuperstructureGoal.GROUND_INTAKE_CONE;
//         request(new SequentialRequest(
//                 new ParallelRequest(
//                         arm.angleRequest(state.getArm()),
//                         wrist.angleRequest(state.getWrist())
//                 ),
//                 intake.stateRequest(Intake.State.INTAKE)
//         ));
//     }

//     public void shelfIntakeState() {
//         SuperstructureGoal state = isCube ? SuperstructureGoal.SHELF_INTAKE_CUBE : SuperstructureGoal.SHELF_INTAKE_CONE;
//         request(new SequentialRequest(
//                 new ParallelRequest(
//                         arm.angleRequest(state.getArm()),
//                         wrist.angleRequest(state.getWrist())
//                 ),
//                 intake.stateRequest(Intake.State.INTAKE)
//         ));
//     }

//     public void scoreL1PoseState() {
//         SuperstructureGoal state = isCube ? SuperstructureGoal.SCORE_CUBE_L1 : SuperstructureGoal.SCORE_CONE_L1;
//         request(new ParallelRequest(
//                 arm.angleRequest(state.getArm()),
//                 wrist.angleRequest(state.getWrist())
//         ));
//     }

//     public void scoreL2PoseState() {
//         SuperstructureGoal state = isCube ? SuperstructureGoal.SCORE_CUBE_L2 : SuperstructureGoal.SCORE_CONE_L2;
//         request(new ParallelRequest(
//                 arm.angleRequest(state.getArm()),
//                 wrist.angleRequest(state.getWrist())
//         ));
//     }

//     public void scoreL3PoseState() {
//         SuperstructureGoal state = isCube ? SuperstructureGoal.SCORE_CUBE_L3 : SuperstructureGoal.SCORE_CONE_L3;
//         request(new ParallelRequest(
//                 arm.angleRequest(state.getArm()),
//                 wrist.angleRequest(state.getWrist())
//         ));
//     }

//     @Override
//     public void outputTelemetry(boolean disabled) {
//         SmartDashboard.putBoolean("Is Cube", isCube);
//         SmartDashboard.putBoolean("Is Ground Intaking", isGroundIntaking);
//     }

// }
