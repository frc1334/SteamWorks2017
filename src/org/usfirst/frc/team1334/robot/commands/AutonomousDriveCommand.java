package org.usfirst.frc.team1334.robot.commands;

import org.usfirst.frc.team1334.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonomousDriveCommand extends Command {
	double Time,Speed,Start,End;
    public AutonomousDriveCommand(double time, double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	Time = time;
    	Speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Start = System.currentTimeMillis();
    	Robot.driveSubsystem.arcadeDrive(0,0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveSubsystem.usePIDOutput(Robot.driveSubsystem.getPIDController().get());
    	Robot.driveSubsystem.arcadeDrive(Robot.driveSubsystem.rotateToAngleRate, Speed);
    	End = System.currentTimeMillis();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(End-Start > Time){
        	return true;
        }
    	return false;
        
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubsystem.arcadeDrive(0,0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
