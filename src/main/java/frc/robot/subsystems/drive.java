// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.security.PublicKey;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.Led1OffColorValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;

import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


//基类：
public class drive extends SubsystemBase {

  //声明电机
  private final TalonFX m_test_motor1 = new TalonFX(Constants.Motor.motor1_id, "rio");
  private final TalonFX m_test_motor2 = new TalonFX(Constants.Motor.motor2_id, "rio");
  //特性：请求制，需要一个request
  private final MotionMagicVoltage m_test_motor1_request = new MotionMagicVoltage(0.0);
  private final VelocityTorqueCurrentFOC m_test_motor2_request = new VelocityTorqueCurrentFOC(0.0);

  private final CANcoder cancoder_fl = new CANcoder(Constants.Cancoder.cancoder1_id, "rio");

  public drive() {

    var motorEncoderConfigs = new CANcoderConfiguration();
    motorEncoderConfigs.MagnetSensor.MagnetOffset=0.0;//offset
    motorEncoderConfigs.MagnetSensor.AbsoluteSensorDiscontinuityPoint=0.5;
    motorEncoderConfigs.MagnetSensor.SensorDirection=SensorDirectionValue.Clockwise_Positive;
    cancoder_fl.getConfigurator().apply(motorEncoderConfigs);

    var motorConfigs = new TalonFXConfiguration();
    motorConfigs.Feedback.RotorToSensorRatio = 13;

    // 手动调整通常遵循以下过程：

    // 将所有增益设置为零。
    //克服重力的参数，kg从0开始逐渐增加，直到松手电梯能够大概稳定在当前位置，不会下坠

    // 确定是否使用电梯或手臂。
    //如果是速度控制，就用velocitysign，位置控制就用colsedloopsign

    // 为您的闭环类型选择适当的静态前馈符号。
    //逐步增加ks直到电机微微有反应，处在一种临界要动但是没动的状态

    // 增加直到电机移动之前。

    // 如果使用速度设定点，请增加速度，直到输出速度与速度设定点紧密匹配。
    //如果你使用速度控制并且需要设定速度到某个值，可以逐步增加kv直到你的速度到达设定值
    //kv是一个放大系数，当我的速度不够的时候，用这个来提高我的速度到预期值

    // 增加直到输出开始在设定点附近振荡。
    //逐步增加kp直到我的当前位置（设定速度）开始在设定的位置（设定速度）附近震动

    // 尽可能增加，不要给响应带来抖动。
    //逐步增加kd直到引入了新的震动

    motorConfigs.Slot0.kS = 0.2;
    motorConfigs.Slot0.kV = 0.0;
    motorConfigs.Slot0.kA = 0;
    motorConfigs.Slot0.kP = 3;
    motorConfigs.Slot0.kI = 0;
    motorConfigs.Slot0.kD = 0;
    motorConfigs.MotionMagic.MotionMagicAcceleration = 100; // Acceleration is around 40 rps/s
    motorConfigs.MotionMagic.MotionMagicCruiseVelocity = 200; // Unlimited cruise velocity
    motorConfigs.MotionMagic.MotionMagicExpo_kV = 0.12; // kV is around 0.12 V/rps
    motorConfigs.MotionMagic.MotionMagicExpo_kA = 0.1; // Use a slower kA of 0.1 V/(rps/s)
    motorConfigs.MotionMagic.MotionMagicJerk = 0; // Jerk is around 0

    motorConfigs.Feedback.FeedbackRemoteSensorID = cancoder_fl.getDeviceID();
    motorConfigs.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.FusedCANcoder;

    m_test_motor1.getConfigurator().apply(motorConfigs);


    var motorEncoderConfigs2 = new CANcoderConfiguration();
    motorEncoderConfigs2.MagnetSensor.MagnetOffset=0.0;//offset
    motorEncoderConfigs2.MagnetSensor.AbsoluteSensorDiscontinuityPoint=0.5;
    motorEncoderConfigs2.MagnetSensor.SensorDirection=SensorDirectionValue.Clockwise_Positive;

    var motorConfigs2 = new TalonFXConfiguration();

    motorConfigs2.Slot0.kS = 1.3;
    motorConfigs2.Slot0.kV = 0.0;
    motorConfigs2.Slot0.kA = 0;
    motorConfigs2.Slot0.kP = 7;
    motorConfigs2.Slot0.kI = 0;
    motorConfigs2.Slot0.kD = 0.1;
    motorConfigs2.MotionMagic.MotionMagicAcceleration = 100; // Acceleration is around 40 rps/s
    motorConfigs2.MotionMagic.MotionMagicCruiseVelocity = 200; // Unlimited cruise velocity
    motorConfigs2.MotionMagic.MotionMagicExpo_kV = 0.12; // kV is around 0.12 V/rps
    motorConfigs2.MotionMagic.MotionMagicExpo_kA = 0.1; // Use a slower kA of 0.1 V/(rps/s)
    motorConfigs2.MotionMagic.MotionMagicJerk = 0; // Jerk is around 0

    m_test_motor2.getConfigurator().apply(motorConfigs2);

    // m_test_motor2.getConfigurator().apply(motorConfigs);
    // m_test_motor3.getConfigurator().apply(motorConfigs);
    // m_test_motor4.getConfigurator().apply(motorConfigs);
  }

  public double getMotorPosition() {
    return m_test_motor1.getPosition().getValueAsDouble();
  }

  //实际控制
  //封装出来的方法
  //控制电机 1.控制电机位置
  //        2.控制电机速度
  //高级的控制方法  本质就是优化速度控制和位置控制

  //withPosition能够将高级的控制请求和底层的位置控制建立联系
  //withvelocity能够将高级的控制请求和底层的速度控制建立联系

  public void setmotorPosition(double pos) {
    m_test_motor1.setControl(m_test_motor1_request.withPosition(pos));
  }

  public void setmotorVelocity(double vol) {
    m_test_motor2.setControl(m_test_motor2_request.withVelocity(vol));
  }


  double motorPosition = 0.0;
  double targetPosition = 0.0;
  double acceptableError = 0.2;

  public boolean IsAtPosition(double Position){
    motorPosition = m_test_motor1.getPosition().getValueAsDouble(); // Get the current position of the motor

    if(Math.abs(motorPosition - Position) <= acceptableError) {
      return true; // The motor is within the acceptable error range of the target position
    } else {
      return false; // The motor is not at the target position
    }
  }

  public Command Motor_Position_Velocity_command(double  Velocity,double  Position){
    return run(()->{
      setmotorPosition(Position); // Set the motor to move at 1000 units per second
      setmotorVelocity(Velocity); 
    })
    .until(() -> IsAtPosition(Position))
    .finallyDo(() -> {
      setmotorVelocity(0);
      setmotorPosition(getMotorPosition());
    });
  }

  public Command Motor_Velocity_command(double  Velocity){
    return runOnce(()->{
      setmotorVelocity(Velocity);// Set the motor to move at 1000 units per second
    });
      
    
  }


}

//subsystem：
//控制机器人

//把所有部分的控制全部写在一起，全部写在一起没法调试某一部分的功能

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
