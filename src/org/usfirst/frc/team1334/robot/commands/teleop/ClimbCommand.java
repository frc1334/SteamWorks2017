package org.usfirst.frc.team1334.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1334.robot.OI;
import org.usfirst.frc.team1334.robot.Robot;

public class ClimbCommand extends Command
{


    /**
     *  ClimbCommand controls the climber during teleop.
     */
    public ClimbCommand()
    {
        super("ClimbCommand");
        requires(Robot.climberSubsystem);
    }

    @Override
    protected void initialize()
    {
        Robot.climberSubsystem.climber.setControlMode(0);
    }

    @Override
    protected void execute()
    {
        Robot.climberSubsystem.climb(OI.OClimbUp(), false);
        Robot.climberSubsystem.Eject(OI.OEject(), Robot.driveSubsystem.gear2.get());
        Robot.driveSubsystem.gear1.get();
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    public void end()
    {
    }

    @Override
    protected void interrupted()
    {
    }

}
