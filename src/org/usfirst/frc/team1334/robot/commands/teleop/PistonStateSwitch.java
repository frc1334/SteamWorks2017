package org.usfirst.frc.team1334.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1334.robot.Robot;

/**
 *
 */
public class PistonStateSwitch extends Command
{
    private double Start, End, Delay;

    /**
     * PistonStateSwitch controls the pistons (Gear Mechanism) during teleop.
     * @param delay milliseconds to delay toggle.
     */
    public PistonStateSwitch(double delay)
    {
        requires(Robot.driveSubsystem);
        requires(Robot.climberSubsystem);
        Delay = delay;
    }


    protected void initialize()
    {
        Start = System.currentTimeMillis();
        Robot.driveSubsystem.gearPiston(false);
    }


    protected void execute()
    {
        End = System.currentTimeMillis();
        Robot.climberSubsystem.Eject(true, Robot.driveSubsystem.gear2.get());
    }


    protected boolean isFinished()
    {
        return End - Start > Delay;
    }


    protected void end()
    {
    }


    protected void interrupted()
    {
    }
}
