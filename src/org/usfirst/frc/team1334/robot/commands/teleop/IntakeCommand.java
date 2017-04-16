package org.usfirst.frc.team1334.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1334.robot.OI;
import org.usfirst.frc.team1334.robot.Robot;

public class IntakeCommand extends Command
{

    /**
     * IntakeCommand controls the intake during teleop.
     */
    public IntakeCommand()
    {
        super("IntakeCommand");
        requires(Robot.intakeSubsystem);
    }

    @Override
    protected void initialize()
    {
        Robot.intakeSubsystem.intakeDump.setControlMode(0);
        Robot.intakeSubsystem.intakePickup.setControlMode(0);

    }

    @Override
    protected void execute()
    {
        Robot.intakeSubsystem.runIntakeDump(OI.ODump());
        Robot.intakeSubsystem.runIntakePickup(OI.OIntake());
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
