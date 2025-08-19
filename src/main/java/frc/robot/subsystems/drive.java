// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.SensorDirectionValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


//基类：
public class drive extends SubsystemBase {
  //声明电机
  private final TalonFX m_test_motor = new TalonFX(11, "rio");
  // private final TalonFX m_test_motor2 = new TalonFX(2, "rio");
  // private final TalonFX m_test_motor3 = new TalonFX(3, "rio");
  // private final TalonFX m_test_motor4 = new TalonFX(4, "rio");
  //特性：请求制，需要一个request
  private final MotionMagicVoltage m_test_motor_request = new MotionMagicVoltage(0.0);

  
  //实际控制
  //封装出来的方法
  //控制电机 1.控制电机位置
  //        2.控制电机速度
  //高级的控制方法  本质就是优化速度控制和位置控制

  //withPosition能够将高级的控制请求和底层的位置控制建立联系
  //withvelocity能够将高级的控制请求和底层的速度控制建立联系

  public void setmotorPosition(double vol) {
    m_test_motor.setControl(m_test_motor_request.withPosition(vol));
    // m_test_motor2.setControl(m_test_motor_request.withPosition(vol));

  // public void setmotorPosition2(double vol) {
  //   m_test_motor3.setControl(m_test_motor_request.withPosition(vol));
  //   m_test_motor4.setControl(m_test_motor_request.withPosition(vol));

  }

  public Command Motor_Position_command(double  position){
    return runEnd(()->{
      setmotorPosition(position); // Set the motor to move at 1000 units per second
                   },
    () -> {
      setmotorPosition(0);
    
    });
  
  
  }

  // public Command Motor_Position_command2(double  position){
  //   return run(()->{
  //     setmotorPosition2(position); // Set the motor to move at 1000 units per second
  //   });
  // }
  public drive() {

    var motorEncoderConfigs = new CANcoderConfiguration();
    motorEncoderConfigs.MagnetSensor.MagnetOffset=0.0;//offset
    motorEncoderConfigs.MagnetSensor.AbsoluteSensorDiscontinuityPoint=0.5;
    motorEncoderConfigs.MagnetSensor.SensorDirection=SensorDirectionValue.Clockwise_Positive;

    var motorConfigs = new TalonFXConfiguration();



    //每个电机都需要的配置
    motorConfigs.Slot0.kS = 0.14;   //slot 槽 一块区域
    motorConfigs.Slot0.kV = 0.0;
    motorConfigs.Slot0.kA = 0;
    motorConfigs.Slot0.kP = 10;
    motorConfigs.Slot0.kI = 0;
    motorConfigs.Slot0.kD = 0;

  

    //高级的控制方法才会用到的参数
    motorConfigs.MotionMagic.MotionMagicAcceleration = 100; // Acceleration is around 40 rps/s
    motorConfigs.MotionMagic.MotionMagicCruiseVelocity = 200; // Unlimited cruise velocity
    motorConfigs.MotionMagic.MotionMagicExpo_kV = 0.12; // kV is around 0.12 V/rps
    motorConfigs.MotionMagic.MotionMagicExpo_kA = 0.1; // Use a slower kA of 0.1 V/(rps/s)
    motorConfigs.MotionMagic.MotionMagicJerk = 0; // Jerk is around 0

    //点击不受控制
    //1.危险
    //2.电机受到损坏


    //voltage控制
    //电压控制   --开环
    //开环控制：敞开的系统，不准确，也不知道自己不准确
    //闭环控制：闭合的系统，相对准确，不准确时自己知道，并且自己知道和目标之间的差距，调整自己接近目标状态

    m_test_motor.getConfigurator().apply(motorConfigs);
    // m_test_motor2.getConfigurator().apply(motorConfigs);
    // m_test_motor3.getConfigurator().apply(motorConfigs);
    // m_test_motor4.getConfigurator().apply(motorConfigs);
  }
}



//subsystem：
//控制机器人

//把所有部分的控制全部写在一起，全部写在一起没法调试某一部分的功能

//假设我的底盘是正常的，底盘的控制程序就不需要改，只需要改其他的部分

//什么叫做子系统：主程序（整个机器人） → 子系统（机器人的的某个部分）

//底盘，抬升机构，发射机构

//子系统1：底盘
//子系统2：抬升机构
//子系统3：发射机构

//把小的部分联合起来，就能实现整个机器人的控制 


//command：
//命令，指令：告诉机器人执行什么操作
//能够把我们的代码转换成机器人的实际操作

//封装好了自己的方法
//commend里面调用我们自己写好的方法，实现机器人的控制

//简单的command的写法完全类似于一个方法的封装

//复杂的command：多个简单的command组合起来

//frc编程不同其他的一些特点
//1.其他情况下的编程，for whlie
//但是在frc里面，没有显式的for和while，受限于电脑当时的情况或者配置
////受限于电脑当时的情况或者配置
//periodic()：每隔20毫秒循环一次
//periodic()：检查仪一下的视觉检测效果，那我就可以在periodic里面写一个for和whlie


//电机不受控制
//1.危险
//2.电机受到损坏

//电机参数调试方法:{


//将所有增益设置为零。

//确定是否使用电梯或手臂。Kg
//Kg:克服重力的参数，从零开始逐渐增加，知道把手松开电梯不会下坠位置

//为您的闭环类型选择适当的静态前馈符号。
//如果是速度控制，就用velocitysign,位置控制就用positionSign

//增加直到电机移动之前。Ks
//Ks：逐步增加直到电机处于临界平衡状态

//如果使用速度设定点，请增加速度，直到输出速度与速度设定点紧密匹配。Kv
//如果你用速度控制并且需要设定速度到某个值，可以逐步增加知道你的速度达到设定值
//Kv是一个放大系数，单给们的速度不够时，用这个来提高速度使其达到设定值

//增加直到输出开始在设定点附近振荡。Kp
//逐步增加Kp值直到我的当前的位置开始在设定位置附近振荡，取他的前一个值

//尽可能增加，不要给响应带来抖动。Kd
//逐步增加Kd直到引入了新的震动，取他的前一个值

//Kp决定了电机的力量大还是小
//Kp要尽可能的大，但不能震颤
//Ki一般不用
//}
