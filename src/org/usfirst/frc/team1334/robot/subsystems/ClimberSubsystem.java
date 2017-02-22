package org.usfirst.frc.team1334.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Ultrasonic;

import org.usfirst.frc.team1334.robot.RobotMap;

import com.ctre.CANTalon;

public class ClimberSubsystem extends Subsystem{
	public CANTalon climber = new CANTalon(RobotMap.climber);
	public Solenoid climb1 = new Solenoid(RobotMap.climber1);
	public Solenoid climb2 = new Solenoid(RobotMap.climber2);
	//public static Ultrasonic rangetoclimb = new Ultrasonic(1,1);
	public static float climberstop = 0.0f;
	public boolean isReversed;
	@Override
	protected void initDefaultCommand() {
		climber.setControlMode(0);
		
	}
		
	public void climb(boolean isClimbing, boolean climbstopbypass){
		
		if(isClimbing && !climbstopbypass){
			climber.set(1.0);
		}else if(!climbstopbypass){
			climber.set(0.0);
		}
	}
	public boolean reverseClimb(boolean isReversed){
		if(isReversed){
			climber.set(-1.0);
			return true;
		}
			return false;
	}
	public void climbPiston(boolean out, boolean in){
		if(out){
			climb1.set(true);
			climb2.set(false);
		}else if(in){
			climb1.set(false);
			climb2.set(true);
		}
	}

}
