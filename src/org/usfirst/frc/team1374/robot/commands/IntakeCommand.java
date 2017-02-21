package org.usfirst.frc.team1374.robot.commands;

import org.usfirst.frc.team1374.robot.OI;
import org.usfirst.frc.team1374.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeCommand extends Command{
	boolean isReversed;
	boolean isForward;
	
	public IntakeCommand(){
    	super("IntakeCommand");
        requires(Robot.intakeSubsystem);
       
    }

    @Override
    protected void initialize() {
		Robot.intakeSubsystem.intakeDump.setControlMode(0);
		Robot.intakeSubsystem.intakePickup.setControlMode(0);
        
    }

    @Override
    protected void execute() {
    	Robot.intakeSubsystem.runIntakeDump(OI.OgetA());
    	Robot.intakeSubsystem.runIntakePickup(OI.OgetB());
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
