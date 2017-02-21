package org.usfirst.frc.team1374.robot;

import edu.wpi.first.wpilibj.buttons.Button;
//import edu.wpi.first.wpilibj.networktables.NetworkTable;
import Util.Xbox360Controller;
//import com.jcraft.jsch.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public static Xbox360Controller Driver = new Xbox360Controller(0,0.15);
    public static Xbox360Controller Operator = new Xbox360Controller(1,0.15);
    /*NetworkTable visionResultTable;
    public void tableInit(){
    	visiontResultTable = NetworkTable.getTable("PiProcessed");
    }*/

    

    
    public static double DgetSpeed() {return Driver.getRightTrigger() - Driver.getLeftTrigger();}
    public static double DgetTurn() {return Driver.getLeftXAxis();}
    public static boolean DgetA() {return Driver.getButtonA();}
    public static boolean DgetB() {return Driver.getButtonB();}
    public static boolean DgetY() {return Driver.getButtonY();}
    public static boolean DgetX() {return Driver.getButtonX();}
    public static boolean DgetRB() {return Driver.getButtonRB();}
    public static boolean DgetLB() {return Driver.getButtonLB();}
    public static boolean OgetA() {return Operator.getButtonA();}
    public static boolean OgetB() {return Operator.getButtonB();}
    public static boolean OgetX() {return Operator.getButtonX();}
    public static boolean OgetY() {return Operator.getButtonY();}
    public static boolean OgetRB(){return Operator.getButtonRB();}
    public static boolean OgetLB(){return Operator.getButtonLB();}
    public static boolean OgetRC(){return Operator.getClickRightStick();}
    public static boolean OgetLC(){return Operator.getClickLeftStick();}

}

