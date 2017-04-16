package org.usfirst.frc.team1334.robot.commands.util;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1334.robot.Robot;

/**
 *
 */
public class GyroTurn extends Command
{
    private double Angle;
    private double Start, End;

    public GyroTurn(double angle)
    {
        requires(Robot.driveSubsystem);
        Angle = angle;
    }


    protected void initialize()
    {
        Start = System.currentTimeMillis();
        End = System.currentTimeMillis();
        Robot.driveSubsystem.setSetpoint(Robot.driveSubsystem.GyroDrive(Angle));
    }


    protected void execute()
    {

        Robot.driveSubsystem.usePIDOutput(Robot.driveSubsystem.getPIDController().get());
        Robot.driveSubsystem.arcadeDrive(Robot.driveSubsystem.rotateToAngleRate, 0);
        if (Math.abs(Robot.driveSubsystem.getPIDController().getError()) > 1)
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
        return End - Start > 500;
    }


    protected void end()
    {
    }


    protected void interrupted()
    {
    }
}
