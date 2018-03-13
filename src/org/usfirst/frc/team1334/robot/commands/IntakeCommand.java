package org.usfirst.frc.team1334.robot.commands;

import org.usfirst.frc.team1334.robot.OI;
import org.usfirst.frc.team1334.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

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
		Robot.intakeSubsystem.intakeDump.set(ControlMode.PercentOutput,0);
		Robot.intakeSubsystem.intakePickup.set(ControlMode.PercentOutput,0);
        
    }

    @Override
    protected void execute() {
    	Robot.intakeSubsystem.runIntakeDump(OI.ODump());
    	Robot.intakeSubsystem.runIntakePickup(OI.OIntake());
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
