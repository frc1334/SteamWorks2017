package org.usfirst.frc.team1334.robot.commands;

import org.usfirst.frc.team1334.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class UltraDistanceCommand extends Command {
	double Distance,error,robotUSOut,Start,End, settleTime,SlowDistance,MaxTime;
	double targetDistance,Begin, Finish;
	boolean isForward;

    public UltraDistanceCommand(double TargetDistance,double SettleTime,double slowDistance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	targetDistance = TargetDistance;
    	settleTime = SettleTime;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	isForward = (targetDistance < Robot.Distance);
    	Start = System.currentTimeMillis();
    	End = System.currentTimeMillis();
    }

    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Distance = Robot.Distance;
    	System.out.println(Robot.Distance);
    	error = targetDistance - Distance;
    	System.out.println("error"+ error);
    	robotUSOut = error * 0.001;//0.0005
    	System.out.println("output" + robotUSOut);
    	if(Math.abs(error)>20){
    		Start = System.currentTimeMillis();
    		if(Distance < SlowDistance  && robotUSOut > 0){//0.9
    			Robot.driveSubsystem.speed = Robot.driveSubsystem.minimalvoltage*0.8;
    		}else if(robotUSOut < Robot.driveSubsystem.minimalvoltage * 0.8 && robotUSOut > 0){
	    		Robot.driveSubsystem.speed = Robot.driveSubsystem.minimalvoltage * 0.8;
	    	}else if (robotUSOut > -Robot.driveSubsystem.minimalvoltage * 0.8 && robotUSOut < 0){
	    		Robot.driveSubsystem.speed = -Robot.driveSubsystem.minimalvoltage * 0.8;
	    	}else if (robotUSOut > 0.45){
	    		Robot.driveSubsystem.speed = 0.45;
	    	}else if(robotUSOut < -0.45){
	    		Robot.driveSubsystem.speed = -0.45;
	    	}else{
	    		Robot.driveSubsystem.speed = robotUSOut;
	    	}
    	}else{
    		End = System.currentTimeMillis();
    		Robot.driveSubsystem.speed = 0;
    	}
    	
    	
    	Robot.driveSubsystem.usePIDOutput(Robot.driveSubsystem.getPIDController().get());
    	Robot.driveSubsystem.arcadeDrive(Robot.driveSubsystem.rotateToAngleRate,Robot.driveSubsystem.speed);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(End-Start > 500){
        	return true;
        }
        
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubsystem.arcadeDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
