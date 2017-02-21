package Util;

import java.util.HashMap;

import edu.wpi.first.wpilibj.Joystick;

public class Xbox360Controller extends Joystick {
    private double deadzone = 0.15;
    // a HashMap which holds a pair of variables for each button on the Xbox360Controller which can be pressed,
    // one for holding the result of this button's respective booleanfalsetotruelistener
    // another for holding the button's previous state
    // the convention for using this bank is that the boolean which stores the change is " 'buttonname'C"
    // E.G. 
    // the button which stores the previous value is" 'buttonname'P"
    // I will create a method to update all of the buttons' boolfalsetotruelisteners when called once within a loop
    // and probably another one for true to false
    // kind of like the keypressed and keyreleased methods in a KeyListener
    public HashMap<String,Boolean> toggleBank = new HashMap<String,Boolean>();
    public void toggleInit(){
    	toggleBank.put("AC",false);
    	toggleBank.put("BC",false);
    	toggleBank.put("XC",false);
    	toggleBank.put("YC",false);
    	toggleBank.put("RBC",false);
    	toggleBank.put("LBC",false);
    	toggleBank.put("BackC",false);
    	toggleBank.put("StartC",false);
    	toggleBank.put("LeftClickC",false);
    	toggleBank.put("RightClickC",false);
    	toggleBank.put("AP",false);
    	toggleBank.put("BP",false);
    	toggleBank.put("XP",false);
    	toggleBank.put("YP",false);
    	toggleBank.put("RBP",false);
    	toggleBank.put("LBP",false);
    	toggleBank.put("BackP",false);
    	toggleBank.put("StartP",false);
    	toggleBank.put("LeftClickP",false);
    	toggleBank.put("RightClickP",false);
    }
    
    public boolean boolfalsetotruelistener(boolean bool,boolean previous){
    	if(bool != previous && bool == true){
    		return true;
    	}else{
    		return false;
    	}   	
    }
    
    
    
    public Xbox360Controller(int port) {super(port);}

    public Xbox360Controller(int port, double deadzone)
    {
    super(port);
        this.deadzone = deadzone;
        this.toggleInit();
    }
    
    /**
     * Left Stick X Axis
     *
     * @return value
     */
    public double getLeftXAxis(){return deadzone(this.getRawAxis(0));}


    /**
     * Left Stick Y Axis
     *
     * @return value
     */
    public double getLeftYAxis()
    {
        return deadzone(this.getRawAxis(1));
    }

    /**
     * Left Trigger
     *
     * @return value
     */
    public double getLeftTrigger()
    {
        return deadzone(this.getRawAxis(2));
    }

    /**
     * Right Trigger
     *
     * @return value
     */
    public double getRightTrigger()
    {
        return deadzone(this.getRawAxis(3));
    }

    /**
     * Right Stick X Axis
     *
     * @return value
     */
    public double getRightXAxis()
    {
        return deadzone(this.getRawAxis(4));
    }

    /**
     * Right Stick Y Axis
     *
     * @return value
     */
    public double getRightYAxis()
    {
        return deadzone(this.getRawAxis(5));
    }
    
    
    public boolean getButtonA()
    {
        return this.getRawButton(1);
    }

    public boolean getButtonB()
    {
        return this.getRawButton(2);
    }

    public boolean getButtonX()
    {
        return this.getRawButton(3);
    }

    public boolean getButtonY()
    {
        return this.getRawButton(4);
    }

    public boolean getButtonLB()
    {
        return this.getRawButton(5);
    }

    public boolean getButtonRB()
    {
        return this.getRawButton(6);
    }

    public boolean getButtonBack()
    {
        return this.getRawButton(7);
    }

    public boolean getButtonStart()
    {
        return this.getRawButton(8);
    }

    public boolean getClickLeftStick()
    {
        return this.getRawButton(9);
    }

    public boolean  getClickRightStick()
    {
        return this.getRawButton(10);
    }

    private double deadzone(double in, double deadzone)
    {
        return ((Math.abs(in) <= deadzone) ? 0 : in);
    }

    private double deadzone(double in)
    {
        return deadzone(in, this.deadzone);
    }
}
