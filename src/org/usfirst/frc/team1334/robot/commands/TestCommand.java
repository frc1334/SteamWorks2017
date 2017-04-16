package org.usfirst.frc.team1334.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team1334.robot.commands.autonomous.AutonomousDriveCommand;

/**
 *
 */
public class TestCommand extends CommandGroup
{
    public TestCommand()
    {
        addSequential(new AutonomousDriveCommand(3000, 0.75));
    }
}
