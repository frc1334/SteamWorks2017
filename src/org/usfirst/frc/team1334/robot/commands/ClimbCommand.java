package org.usfirst.frc.team1334.robot.commands;

import org.usfirst.frc.team1334.robot.OI;
import org.usfirst.frc.team1334.robot.Robot;
import org.usfirst.frc.team1334.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ClimbCommand extends Command{
	
	
	boolean isReversed = false;
	boolean pistonOut;
	
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
    	//isReversed = Robot.climberSubsystem.reverseClimb(OI.OClimbDown());
        Robot.climberSubsystem.climb(OI.OClimbUp(), isReversed);
        //Robot.climberSubsystem.climbPiston(OI.OEject());
        Robot.climberSubsystem.Eject(OI.OEject(), Robot.driveSubsystem.gear2.get());
        Robot.driveSubsystem.gear1.get();
        //OI.Operator.toggleBank.put("LBC", OI.Operator.boolfalsetotruelistener(OI.OClimbGuidePistonToggle(),OI.Operator.toggleBank.get("LBP")));
        //OI.Operator.toggleBank.put("LBP", OI.OClimbGuidePistonToggle());
        //if(OI.Operator.toggleBank.get("LBC")){
        //	pistonOut = !pistonOut;
       // }
        
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
