// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.drivetrain.CommandSwerveDrivetrain;
import frc.robot.subsystems.drivetrain.DrivetrainConstants;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ReefAlign extends Command {
  private CommandSwerveDrivetrain s_drivetrain;
  private double lastTagID = 0;
  private boolean isCurrentTag = false;
  private boolean atReefPose = false;
  private Pose2d TargetPose = new Pose2d();

  /** Creates a new ReefAlign. */
  public ReefAlign(CommandSwerveDrivetrain drivetrain) {
    s_drivetrain = drivetrain;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(s_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    lastTagID = LimelightHelpers.getFiducialID("limelight-front");
    isCurrentTag = LimelightHelpers.getTV("limelight-front");
    TargetPose = DrivetrainConstants.ReefPose.get(lastTagID);

    new DriveToPose(s_drivetrain, TargetPose);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    atReefPose = s_drivetrain.comparePose2d(TargetPose, 0.1, 0.1, 5);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return atReefPose || !isCurrentTag;
  }
}
