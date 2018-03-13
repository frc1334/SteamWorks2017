package org.usfirst.frc.team1334.robot.subsystems;

import org.usfirst.frc.team1334.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeSubsystem extends Subsystem{
	public TalonSRX intakePickup = new TalonSRX(RobotMap.intakePickup);
	public TalonSRX intakeDump = new TalonSRX(RobotMap.intakeDump);
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public void runIntakePickup(boolean isSpinning){
		if(isSpinning){
			intakePickup.set(ControlMode.PercentOutput,-1.0);
		}else{
			intakePickup.set(ControlMode.PercentOutput,0.0);
		}
	}
	
	public void runIntakeDump(boolean isSpinning){
		if(isSpinning){
			intakeDump.set(ControlMode.PercentOutput,-1.0);
		}else{
			intakeDump.set(ControlMode.PercentOutput,0.0);
		}
	}

}
