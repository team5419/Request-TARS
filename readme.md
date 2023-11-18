# Team 5419 Example Code

This code is intended to be a learning repository that gives an example of how I would have written the 5419 clone
robot's arm code.

## Package Functions

### Custom Code

- [`com.team5419.frc2023`](src/main/java/com/team5419/frc2023)

  Contains the robot's central functions and holds a class with all numerical constants used throughout the code (
  see [`Constants.java`](src/main/java/com/team5419/frc2023/Constants.java)). For example,
  the [`Robot`](src/main/java/com/team5419/frc2023/Robot.java) class controls all routines depending on the robot mode.

- [`com.team5419.frc2023.loops`](src/main/java/com/team5419/frc2023/loops)

  Contains codes for loops, which are routines that run periodically on the robot, such as for calculating robot pose,
  processing vision feedback, or updating subsystems. All loops implement
  the [`Loop`](src/main/java/com/team5419/frc2023/loops/Loop.java) interface and are handled (started, stopped, added)
  by the [`Looper`](src/main/java/com/team5419/frc2023/loops/Looper.java) class, which runs at 100 Hz.
  The [`Robot`](src/main/java/com/team5419/frc2023/Robot.java) class has one main looper, `mEnabledLooper`, that runs
  all loops when the robot is enabled.

- [`com.team5419.frc2023.subsystems`](src/main/java/com/team5419/frc2023/subsystems)

  Contains code for subsystems, which are consolidated into one central class per subsystem, all of which extend
  the [`Subsystem`](src/main/java/com/team5419/frc2023/subsystems/Subsystem.java) abstract class. Each subsystem uses
  state machines for control and is a singleton, meaning that there is only one instance of each. Subsystems also
  contain an enabled loop, a read periodic inputs method, and a write periodic outputs method, which are controlled by
  the [`SubystemManager`](src/main/java/com/team5419/frc2023/SubsystemManager.java) class.

- [`com.team5419.frc2023.Ports`](src/main/java/com/team5419/frc2023/Ports.java)

  Contains a list of IDs for each motor on the robot.

- [`com.team5419.frc2023.DriverControls`](src/main/java/com/team5419/frc2023/DriverControls.java)

  Contains calls that decipher inputs from [`Xbox Controllers`](src/main/java/com/team5419/lib/io/Xbox.java) and runs on
  the enabled loop. This class gets updated prior to the subsystem manager so the robot will respond with your inputs on
  the same loop.

### Team 254 Code

- [`com.team254.lib.drivers`](src/main/java/com/team254/lib/drivers)

  Contains a set of custom classes for motor controllers, color sensors, and solenoids for simplifying motor
  configuration, reducing CAN Bus usage, and checking motors.

- [`com.team254.lib.motion`](src/main/java/com/team254/lib/motion)

  Contains all motion profiling code. Trapezoidal motion profiles are used for smooth acceleration and minimal slip.

- [`com.team254.lib.util`](src/main/java/com/team254/lib/util)

  Contains a collection of assorted utilities classes used in the robot code. Check each file for more information.