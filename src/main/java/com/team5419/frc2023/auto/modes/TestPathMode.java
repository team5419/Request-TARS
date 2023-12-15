package com.team5419.frc2023.auto.modes;

import java.util.List;

import com.team5419.frc2023.Robot;
import com.team5419.frc2023.auto.AutoModeBase;
import com.team5419.frc2023.auto.AutoModeEndedException;
import com.team5419.frc2023.auto.AutoTrajectoryReader;
import com.team5419.frc2023.auto.actions.LambdaAction;
import com.team5419.frc2023.auto.actions.ParallelAction;
import com.team5419.frc2023.auto.actions.SeriesAction;
import com.team5419.frc2023.auto.actions.SwerveTrajectoryAction;
import com.team5419.frc2023.auto.actions.WaitAction;
import com.team5419.frc2023.auto.actions.WaitToPassXCoordinateAction;
import com.team5419.frc2023.constants.Constants;
import com.team5419.frc2023.subsystems.Drive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;

public class TestPathMode extends AutoModeBase{

    String pathA = "paths/Node9toCube.path";
    String pathB = "paths/CubeTo8.path";

    SwerveTrajectoryAction driveToCube;
    final Trajectory driveToCubeTraj;

    SwerveTrajectoryAction driveToNode;
    final Trajectory driveToNodeTraj;

    public TestPathMode() {
        driveToCubeTraj = AutoTrajectoryReader.generateTrajectoryFromFile(pathA, Constants.AutoConstants.createConfig(1.5, 7.5, 0, 0));
        driveToCube = new SwerveTrajectoryAction(driveToCubeTraj, Rotation2d.fromDegrees(0));

        driveToNodeTraj = AutoTrajectoryReader.generateTrajectoryFromFile(pathB, Constants.AutoConstants.createConfig(1.5, 7.5, 0, 0));
        driveToNode = new SwerveTrajectoryAction(driveToNodeTraj, Rotation2d.fromDegrees(0));

        Drive.getInstance().resetOdometry(getStartingPose());
    }


    @Override
    protected void routine() throws AutoModeEndedException {


        // runAction(new LambdaAction(() -> Drive.getInstance().resetOdometry(getStartingPose())));

        System.out.println("Running test path");
        runAction(new ParallelAction(List.of(
            driveToCube,
            new SeriesAction(List.of(
                new WaitToPassXCoordinateAction(1),
                new LambdaAction(() -> System.out.println("Passed x"))
            ))
        )));
        System.out.println("First path done");
        runAction(new WaitAction(1));
        System.out.println("Wait over");
        runAction(driveToNode);
        System.out.println("Second path done");

    }

    @Override
    public Pose2d getStartingPose() {
        Rotation2d startingRotation = Rotation2d.fromDegrees(180.0);
        if (Robot.is_red_alliance) {
            startingRotation = Rotation2d.fromDegrees(0.0);
        }
        return new Pose2d(driveToCubeTraj.getInitialPose().getTranslation(), startingRotation);
    }
}
