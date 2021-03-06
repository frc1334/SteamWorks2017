package org.usfirst.frc.team1334.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Ultrasonic;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.usfirst.frc.team1334.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class ClimberSubsystem extends Subsystem{
	public TalonSRX climber = new TalonSRX(RobotMap.climber);
	public Solenoid climb1 = new Solenoid(RobotMap.eject1);
	public Solenoid climb2 = new Solenoid(RobotMap.eject2);
	public static float climberstop = 0.0f;
	public boolean isReversed;
	double Start = System.currentTimeMillis();
	double End = System.currentTimeMillis();
	LinkedList<Double> CurrentQueue = new LinkedList<Double>();
	@Override
	protected void initDefaultCommand() {
		climber.set(ControlMode.PercentOutput,0);
		
	}
	
	public void Eject(boolean isEject, boolean isSafe){
    	
    	
    	if(isSafe){
    		End = System.currentTimeMillis();
    	}else{
    		Start = System.currentTimeMillis();
    	}
    	
    	if(isEject && End-Start > 500){
    		climb1.set(true);
    		climb2.set(false);
    	}else{
    		climb1.set(false);
    		climb2.set(true);
    	}
    }
	
	public Double QueueSum(Queue<Double>Q){
		Double sum = 0.0;
		for(int i = 0; i<Q.size();i++){
			double n = Q.remove();
			sum+=n;
			Q.add(n);
		}
		return sum;
	}
	
	public void climb(boolean isClimbing, boolean climbstopbypass){
		
		if(isClimbing && !climbstopbypass){
			climber.set(ControlMode.PercentOutput,1.0);
		}else if(!climbstopbypass){
			climber.set(ControlMode.PercentOutput,0.0);
		}
	}
	public boolean reverseClimb(boolean isReversed){
		if(isReversed){
			climber.set(ControlMode.PercentOutput,-1.0);
			return true;
		}
			return false;
	}
	public void climbPiston(boolean out){
		if(out){
			climb1.set(true);
			climb2.set(false);
		}else{
			climb1.set(false);
			climb2.set(true);
		}
	}
	
	public boolean isStalledCap(double cap){
		double current = climber.getOutputCurrent();
		if(current > cap){
			return true;
		}
		return false;
	}
	
	public boolean isStalledCapAverage(int Queuelength,double cap){
		double current = climber.getOutputCurrent();
		
		CurrentQueue.add(current);
		while(CurrentQueue.size()>Queuelength){
			CurrentQueue.remove();
		}
		
		if(QueueSum(CurrentQueue)/CurrentQueue.size() < cap){
			return true;
		}
		return false;
	}
	
	public boolean isStalledRatio(double ratio){
		double current = climber.getOutputCurrent();
		
		CurrentQueue.add(current);
		while(CurrentQueue.size()>2){
			CurrentQueue.remove();
		}
		if(CurrentQueue.get(0)/CurrentQueue.get(1) > ratio){
			return true;
		}
		return false;
	}

}
