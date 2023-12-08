// package com.team5419.frc2023.subsystems;

// import com.ctre.phoenix6.hardware.TalonFX;
// import com.team5419.frc2023.Ports;
// import com.team5419.frc2023.loops.ILooper;
// import com.team5419.frc2023.loops.Loop;
// import com.team5419.lib.requests.Request;
// import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// public class Intake extends Subsystem {

//     public static Intake mInstance = null;

//     public static Intake getInstance() {
//         if (mInstance == null) {
//             return mInstance = new Intake();
//         }
//         return mInstance;
//     }

//     protected final TalonFX motor = new TalonFX(Ports.kIntake);

//     public Intake() {
//     }

//     public enum State {
//         IDLE(0.0), INTAKE(6.0), OUTTAKE(12.0);

//         public final double voltage;
//         State (double voltage) {
//             this.voltage = voltage;
//         }
//     }

//     private State currentState = State.IDLE;
//     public State getState() {
//         return currentState;
//     }

//     public void setState(State wantedState) {
//         if (currentState != wantedState) {
//             currentState = wantedState;
//         }
//     }

//     @Override
//     public void registerEnabledLoops(ILooper mEnabledLooper) {
//         mEnabledLooper.register(new Loop() {
//             @Override
//             public void onStart(double timestamp) {
//                 stop();
//             }

//             @Override
//             public void onLoop(double timestamp) {
//                 mPeriodicIO.demand = currentState.voltage;
//             }

//             @Override
//             public void onStop(double timestamp) {
//                 stop();
//             }
//         });
//     }

//     @Override
//     public void stop() {
//         motor.stopMotor();
//         setState(State.IDLE);
//     }

//     public void setOpenLoop(double voltage) {
//         mPeriodicIO.demand = voltage;
//     }

//     public Request stateRequest(State requestedState) {
//         return new Request() {
//             @Override
//             public void act() {
//                 setState(requestedState);
//             }

//             @Override
//             public boolean isFinished() {
//                 return mPeriodicIO.demand == requestedState.voltage;
//             }
//         };
//     }

//     public static PeriodicIO mPeriodicIO = new PeriodicIO();
//     public static class PeriodicIO {
//         // Inputs
//         private double timestamp;
//         private double voltage;
//         private double current;

//         // Outputs
//         private double demand;
//     }

//     @Override
//     public void writePeriodicOutputs() {
//         motor.setVoltage(mPeriodicIO.demand);
//     }

//     @Override
//     public void readPeriodicInputs() {
//         mPeriodicIO.timestamp = Timer.getFPGATimestamp();
//         mPeriodicIO.voltage = motor.getSupplyVoltage().getValue();
//         mPeriodicIO.current = motor.getSupplyCurrent().getValue();
//     }

//     @Override
//     public void outputTelemetry(boolean disabled) {
//         SmartDashboard.putNumber("Intake Demand", mPeriodicIO.demand);
//         SmartDashboard.putNumber("Intake Supplied Volts", mPeriodicIO.voltage);
//         SmartDashboard.putNumber("Intake Current", mPeriodicIO.current);
//         SmartDashboard.putString("Intake State", currentState.toString());
//     }
// }
