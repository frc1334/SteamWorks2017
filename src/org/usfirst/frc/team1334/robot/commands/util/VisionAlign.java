package org.usfirst.frc.team1334.robot.commands.util;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1334.robot.Robot;

/**
 *
 */
public class VisionAlign extends Command
{
    private double Start, End;

    public VisionAlign()
    {


        requires(Robot.driveSubsystem);
    }


    protected void initialize()
    {
        Start = System.currentTimeMillis();
        End = System.currentTimeMillis();
    }


    protected void execute()
    {
        Robot.driveSubsystem.VisionDrive(Robot.x, Robot.x2);
        Robot.driveSubsystem.setSetpoint(Robot.driveSubsystem.GyroDrive(0));
        Robot.driveSubsystem.usePIDOutput(Robot.driveSubsystem.getPIDController().get());
        Robot.driveSubsystem.arcadeDrive(Robot.driveSubsystem.rotateToAngleRate, 0);
        if (Math.abs(Robot.driveSubsystem.error) > 25)
        {
            Start = System.currentTimeMillis();
        }
        else
        {
            End = System.currentTimeMillis();
        }

    }


    protected boolean isFinished()
    {
        return End - Start > 1000;
    }


    protected void end()
    {
    }

    protected void interrupted()
    {
    }
}
