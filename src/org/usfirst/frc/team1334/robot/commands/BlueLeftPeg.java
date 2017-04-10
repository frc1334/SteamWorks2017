package org.usfirst.frc.team1334.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BlueLeftPeg extends CommandGroup {

    public BlueLeftPeg() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	addSequential(new UltraDistanceCommand(1665,500,0));
    	addSequential(new GyroTurn(-120));
    	addSequential(new VisionAlign());
    	addSequential(new UltraDistanceCommand(250,500,620));
    	addSequential(new PistonStateSwitch(2000));
    	addSequential(new AutonomousDriveCommand(1500,0.3));
        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
