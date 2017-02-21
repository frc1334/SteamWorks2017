package org.usfirst.frc.team1374.robot.commands;

import org.usfirst.frc.team1374.robot.OI;
import org.usfirst.frc.team1374.robot.Robot;
import org.usfirst.frc.team1374.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ClimbCommand extends Command{
	
	
	boolean isReversed;
	boolean isForward;
	
	public ClimbCommand(){
    	super("ClimbCommand");
        requires(Robot.climberSubsystem);
       
    }

    @Override
    protected void initialize() {
		Robot.climberSubsystem.climber.setControlMode(0);
        
    }

    @Override
    protected void execute() {
    	isReversed = Robot.climberSubsystem.reverseClimb(OI.OgetY());
    	/*if(OI.Operator.boolfalsetotruelistener(OI.OgetX(), OI.Operator.toggleBank.get("XP"))){
    		isForward = !isForward;
    	}*/
        Robot.climberSubsystem.climb(OI.OgetX(), isReversed);
        Robot.climberSubsystem.climbPiston(OI.OgetRC(), OI.OgetLC());
        //OI.Operator.toggleBank.put("XP", OI.OgetX());
    }

    @Override
    protected boolean isFinished() {
        
    	return false;
    }

    @Override
	public void end() {
    	
    }

    @Override
    protected void interrupted() {

    }

}
