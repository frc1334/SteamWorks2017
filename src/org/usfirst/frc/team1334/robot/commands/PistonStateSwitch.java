package org.usfirst.frc.team1334.robot.commands;

import org.usfirst.frc.team1334.robot.OI;
import org.usfirst.frc.team1334.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PistonStateSwitch extends Command {
double Start,End,Delay;
    public PistonStateSwitch(double delay) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	requires(Robot.climberSubsystem);
    	Delay = delay;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Start = System.currentTimeMillis();
    	Robot.driveSubsystem.gearPiston(false);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	End = System.currentTimeMillis();
    	Robot.climberSubsystem.Eject(true, Robot.driveSubsystem.gear2.get());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(End-Start > Delay){
        	return true;
        }
    	
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
