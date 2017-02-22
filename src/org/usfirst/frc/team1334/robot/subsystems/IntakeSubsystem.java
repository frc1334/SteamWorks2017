package org.usfirst.frc.team1334.robot.subsystems;

import org.usfirst.frc.team1334.robot.RobotMap;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeSubsystem extends Subsystem{
	public CANTalon intakePickup = new CANTalon(RobotMap.intakePickup);
	public CANTalon intakeDump = new CANTalon(RobotMap.intakeDump);
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public void runIntakePickup(boolean isSpinning){
		if(isSpinning){
			intakePickup.set(-1.0);
		}else{
			intakePickup.set(0.0);
		}
	}
	
	public void runIntakeDump(boolean isSpinning){
		if(isSpinning){
			intakeDump.set(-1.0);
		}else{
			intakeDump.set(0.0);
		}
	}

}
