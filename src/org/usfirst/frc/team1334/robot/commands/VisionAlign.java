package org.usfirst.frc.team1334.robot.commands;

import org.usfirst.frc.team1334.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class VisionAlign extends Command {
	double Start,End;
    public VisionAlign() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Start = System.currentTimeMillis();
    	End = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveSubsystem.VisionDrive(Robot.x, Robot.x2);
    	Robot.driveSubsystem.setSetpoint(Robot.driveSubsystem.GyroDrive(0));
    	Robot.driveSubsystem.usePIDOutput(Robot.driveSubsystem.getPIDController().get());
    	Robot.driveSubsystem.arcadeDrive(Robot.driveSubsystem.rotateToAngleRate, 0);
    	if(Math.abs(Robot.driveSubsystem.error)>25){
    		Start = System.currentTimeMillis();
    	}else{
    		End = System.currentTimeMillis();
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(End-Start>500){
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
