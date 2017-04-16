package org.usfirst.frc.team1334.robot;

import org.usfirst.frc.team1334.robot.util.Xbox360Controller;

//import edu.wpi.first.wpilibj.networktables.NetworkTable;
//import com.jcraft.jsch.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI
{
    public static Xbox360Controller Driver = new Xbox360Controller(0, 0.15);
    public static Xbox360Controller Operator = new Xbox360Controller(1, 0.15);

    //Driver Controls
    public static double DgetSpeed()
    {
        return Driver.getRightTrigger() - Driver.getLeftTrigger();
    }

    public static double DgetTurn()
    {
        return Driver.getLeftXAxis();
    }

    public static boolean DLowGear()
    {
        return Driver.getButtonA();
    }

    public static boolean DHighGear()
    {
        return Driver.getButtonB();
    }

    public static boolean DgetY()
    {
        return Driver.getButtonY();
    }

    public static boolean DEnableGyro()
    {
        return Driver.getButtonX();
    }

    public static boolean DToggleRecord()
    {
        return Driver.getButtonRB();
    }

    public static boolean DToggleVision()
    {
        return Driver.getButtonLB();
    }

    public static boolean DRangeFinderRecord()
    {
        return Driver.getClickRightStick();
    }

    //Operator Controls
    public static double OgetPan()
    {
        return Operator.getLeftXAxis();
    }

    public static double OgetTilt()
    {
        return Operator.getLeftYAxis();
    }

    public static boolean OIntake()
    {
        return Operator.getButtonA();
    }

    public static boolean ODump()
    {
        return Operator.getButtonB();
    }

    public static boolean OClimbUp()
    {
        return Operator.getButtonX();
    }

    public static boolean OEject()
    {
        return Operator.getButtonY();
    }

    public static boolean OGearPistonToggle()
    {
        return Operator.getButtonRB();
    }

    public static boolean OClimbGuidePistonToggle()
    {
        return Operator.getButtonLB();
    }


}

