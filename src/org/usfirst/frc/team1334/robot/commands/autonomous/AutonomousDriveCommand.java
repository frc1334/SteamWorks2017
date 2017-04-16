package org.usfirst.frc.team1334.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1334.robot.Robot;

/**
 * AutonomousDriveCommand is the basic drive controller during autonomous.
 */
public class AutonomousDriveCommand extends Command
{
    private double Time, Speed, Start, End;

    /**
     * AutonomousDriveCommand
     * @param time Milliseconds to drive
     * @param speed Speed to run at. Bounded -1 to 1 inclusive.
     */
    public AutonomousDriveCommand(double time, double speed)
    {
        requires(Robot.driveSubsystem);
        Time = time;
        Speed = speed;
    }


    protected void initialize()
    {
        Start = System.currentTimeMillis();
        Robot.driveSubsystem.arcadeDrive(0, 0);
    }


    protected void execute()
    {
        Robot.driveSubsystem.usePIDOutput(Robot.driveSubsystem.getPIDController().get());
        Robot.driveSubsystem.arcadeDrive(Robot.driveSubsystem.rotateToAngleRate, Speed);
        End = System.currentTimeMillis();
    }

    /**
     * Make sure that the time delta is more than the minimum.
     * @return finished
     */
    protected boolean isFinished()
    {
        return End - Start > Time;
    }


    protected void end()
    {
        Robot.driveSubsystem.arcadeDrive(0, 0);
    }


    protected void interrupted()
    {
    }
}
