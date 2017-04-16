package org.usfirst.frc.team1334.robot.commands.autonomous.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team1334.robot.commands.autonomous.AutonomousDriveCommand;
import org.usfirst.frc.team1334.robot.commands.teleop.PistonStateSwitch;
import org.usfirst.frc.team1334.robot.commands.util.GyroTurn;
import org.usfirst.frc.team1334.robot.commands.util.UltraDistanceCommand;
import org.usfirst.frc.team1334.robot.commands.util.VisionAlign;

/**
 *
 */
public class RedRightPeg extends CommandGroup
{

    public RedRightPeg()
    {
        addSequential(new UltraDistanceCommand(1665, 500, 0));
        addSequential(new GyroTurn(120));
        addSequential(new VisionAlign());
        addSequential(new UltraDistanceCommand(250, 500, 620));
        addSequential(new PistonStateSwitch(2000));
        addSequential(new AutonomousDriveCommand(500, 1));
    }
}
