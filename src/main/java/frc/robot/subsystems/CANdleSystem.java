// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.



package frc.robot.subsystems;

import com.ctre.phoenix.led.Animation;
import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.CANdle.VBatOutputMode;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.ColorFlowAnimation;
import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.ctre.phoenix.led.FireAnimation;
import com.ctre.phoenix.led.LarsonAnimation;
import com.ctre.phoenix.led.LarsonAnimation.BounceMode;
import com.ctre.phoenix.led.RainbowAnimation;
import com.ctre.phoenix.led.RgbFadeAnimation;
import com.ctre.phoenix.led.SingleFadeAnimation;
import com.ctre.phoenix.led.StrobeAnimation;
import com.ctre.phoenix.led.TwinkleAnimation;
import com.ctre.phoenix.led.TwinkleAnimation.TwinklePercent;
import com.ctre.phoenix.led.TwinkleOffAnimation;
import com.ctre.phoenix.led.TwinkleOffAnimation.TwinkleOffPercent;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
             
public class CANdleSystem extends SubsystemBase {    //属性
    private final CANdle m_candle = new CANdle(Constants.Candle.CANdleID, "rio");
    private final int LedCount = 300; //灯珠的数量，设的比较小，就可能灯带只亮一半


    private Animation m_toAnimate = null;//要变换到的状态
                                         //一种灯效  →  一种状态
                                         //灯效的切换  →  不同状态的切换

    public enum AnimationTypes {   //包含所有要枚举的变量    //属性
        ColorFlow,
        Fire,
        Larson,
        Rainbow,
        RgbFade,
        SingleFade,
        Strobe,
        Twinkle,
        TwinkleOff,
        SetAll
    }
    private AnimationTypes m_currentAnimation;

    public CANdleSystem() {             //构造函数：名字要和类一致，没有
        //this.joystick = joy;
        changeAnimation(AnimationTypes.SetAll);
        CANdleConfiguration configAll = new CANdleConfiguration();
        configAll.statusLedOffWhenActive = true;
        configAll.disableWhenLOS = false;
        configAll.stripType = LEDStripType.GRB;
        configAll.brightnessScalar = 0.1;
        configAll.vBatOutputMode = VBatOutputMode.Modulated;
        m_candle.configAllSettings(configAll, 100);
    }

    /**
    一般情况下，我们里面如果涉及到几个固定的状态之间的切换
    通常，我们采用标志位
    

    */
    
    

    //this method is used to increase the animation state 
    public void incrementAnimation() {    //方法   //状态递增   //从状态1到状态2
        switch(m_currentAnimation) {
            case ColorFlow: changeAnimation(AnimationTypes.Fire); break;
            case Fire: changeAnimation(AnimationTypes.Larson); break;
            case Larson: changeAnimation(AnimationTypes.Rainbow); break;
            case Rainbow: changeAnimation(AnimationTypes.RgbFade); break;
            case RgbFade: changeAnimation(AnimationTypes.SingleFade); break;
            case SingleFade: changeAnimation(AnimationTypes.Strobe); break;
            case Strobe: changeAnimation(AnimationTypes.Twinkle); break;
            case Twinkle: changeAnimation(AnimationTypes.TwinkleOff); break;
            case TwinkleOff: changeAnimation(AnimationTypes.ColorFlow); break;
            case SetAll: changeAnimation(AnimationTypes.ColorFlow); break;
        }
    }

    // ColorFlow → Fire → Larson → Rainbow → RgbFade → SingleFade → Strobe → Twinkle → TwinkleOff → ColorFlow → Fire(顺序从上到下)

    //this method is used to decrease the animation state
    public void decrementAnimation() {    //方法   //状态递减   //从状态2到状态1
        switch(m_currentAnimation) {
            case ColorFlow: changeAnimation(AnimationTypes.TwinkleOff); break;
            case Fire: changeAnimation(AnimationTypes.ColorFlow); break;
            case Larson: changeAnimation(AnimationTypes.Fire); break;
            case Rainbow: changeAnimation(AnimationTypes.Larson); break;
            case RgbFade: changeAnimation(AnimationTypes.Rainbow); break;
            case SingleFade: changeAnimation(AnimationTypes.RgbFade); break;
            case Strobe: changeAnimation(AnimationTypes.SingleFade); break;
            case Twinkle: changeAnimation(AnimationTypes.Strobe); break;
            case TwinkleOff: changeAnimation(AnimationTypes.Twinkle); break;
            case SetAll: changeAnimation(AnimationTypes.ColorFlow); break;
        }
    }
    // ColorFlow → TwinkleOff → Twinkle → Strobe → SingleFade → RgbFade → Rainbow → Larson → Fire → ColorFlow → TwinkleOff(顺序从下到上)



    public void setColors() {
        changeAnimation(AnimationTypes.SetAll);
    }

    public void setOff() {
        m_candle.animate(null);       //像素最高值 255
        // m_candle.animate(null);
        // m_candle.setLEDs(0, 0, 0);
        m_candle.setLEDs(0, 0, 0);
        changeAnimation(AnimationTypes.SetAll);

    }

    public void setFire() {
        m_toAnimate = new FireAnimation(0.5, 0.7, LedCount, 0.7, 0.5);
    }

    public void FirewithMotor(){
        m_toAnimate = new FireAnimation(0.5, 0.7, LedCount, 0.7, 0.5);
    }

    public void setColorFlow() {
        m_toAnimate = new ColorFlowAnimation(128, 20, 70, 0, 0.7, LedCount, Direction.Forward);
    }

    /* Wrappers so we can access the CANdle from the subsystem */
    public double getVbat() { return m_candle.getBusVoltage(); }
    public double get5V() { return m_candle.get5VRailVoltage(); }
    public double getCurrent() { return m_candle.getCurrent(); }
    public double getTemperature() { return m_candle.getTemperature(); }
    public void configBrightness(double percent) { m_candle.configBrightnessScalar(percent, 0); }
    public void configLos(boolean disableWhenLos) { m_candle.configLOSBehavior(disableWhenLos, 0); }
    public void configLedType(LEDStripType type) { m_candle.configLEDType(type, 0); }
    public void configStatusLedBehavior(boolean offWhenActive) { m_candle.configStatusLedState(offWhenActive, 0); }

    public void changeAnimation(AnimationTypes toChange) {
        m_currentAnimation = toChange; //状态标志位的更新，时刻和机器人状态一起更新
        
        switch(toChange)//to change 就是一个标志位
        {
            case ColorFlow:
               m_toAnimate = new ColorFlowAnimation(128, 20, 70, 0, 0.7, LedCount, Direction.Forward); 
                break;
            case Fire:
                m_toAnimate = new FireAnimation(0.5, 0.7, LedCount, 0.7, 0.5);
                break;
            case Larson:
                m_toAnimate = new LarsonAnimation(0, 255, 46, 0, 1, LedCount, BounceMode.Front, 3);
                break;
            case Rainbow:
                m_toAnimate = new RainbowAnimation(1, 0.1, LedCount);
                break;
            case RgbFade:
                m_toAnimate = new RgbFadeAnimation(0.7, 0.4, LedCount);
                break;
            case SingleFade:
                m_toAnimate = new SingleFadeAnimation(50, 2, 200, 0, 0.5, LedCount);
                break;
            case Strobe:
                m_toAnimate = new StrobeAnimation(240, 10, 180, 0, 98.0 / 256.0, LedCount);
                break;
            case Twinkle:
                m_toAnimate = new TwinkleAnimation(30, 70, 60, 0, 0.4, LedCount, TwinklePercent.Percent6);
                break;
            case TwinkleOff:
                m_toAnimate = new TwinkleOffAnimation(70, 90, 175, 0, 0.8, LedCount, TwinkleOffPercent.Percent100);
                break;
            case SetAll:
                m_toAnimate = null;
                break;
        }
        System.out.println("Changed to " + m_currentAnimation.toString());
    }

    //创建了一个火焰灯效m_toAnimate
    //下一步用上去
    //.animate(灯效对象)

    //有一个炫酷的灯效，我想让他一直保持整场比赛，我应该怎么写
    //periodic() ： 每20ms调用一次，那我只需要把.animate写在periodic里面

    public Command setFireWithMotor() {

        return startEnd(
                () ->{
                    m_toAnimate = new FireAnimation(0.5, 0.7, LedCount, 0.7, 0.5);
                },
                () ->{
                    setOff();
                }

        );

    }

    public Command MattisGay(){
        return run(()->{
        FirewithMotor(); // Set the motor to move at 1000 units per second
        });
      }

      public Command ZikiisGay(){
        return run(()->{
        setOff(); // Set the motor to move at 1000 units per second
        });
      }

      
    //重写父类的方法
    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        // if(m_toAnimate == null) {
        //     m_candle.setLEDs((int)(joystick.getLeftTriggerAxis() * 255), 
        //                       (int)(joystick.getRightTriggerAxis() * 255), 
        //                       (int)(joystick.getLeftX() * 255));
        // } else {
        m_candle.animate(m_toAnimate);
        // }
       // m_candle.modulateVBatOutput(joystick.getRightY());
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}


//各部分的periodic从机器人启动之后，相互独立，并且同时开始运行
//一个子系统   →    一个线程

//机器人启动 → 就会带起所有的线程

//每个线程都有自己的periodic，有意每个线程都是同时开始轮询periodic里面的操作