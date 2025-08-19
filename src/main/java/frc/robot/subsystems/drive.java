// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class drive extends SubsystemBase {
  public static final Command Motor_Move_Voltage = null;
  //声明电机
   private final TalonFX m_test_motor = new TalonFX(2, "rio");

   private final TalonFX m_test_motor2 = new TalonFX(1, "rio");

   private final TalonFX m_test_motor3 = new TalonFX(3, "rio");

   private final TalonFX m_test_motor4 = new TalonFX(4, "rio");
  //请求制，需要一个request
  private final VoltageOut m_test_motor_request = new VoltageOut(0.0);

  private final VoltageOut m_test_motor_request2 = new VoltageOut(0.0);
  
  private final VoltageOut m_test_motor_request3 = new VoltageOut(0.0);

  private final VoltageOut m_test_motor_request4 = new VoltageOut(0.0);
  //电机控制：时间速度

  //withPosition：高级的控制请求和底层的逻辑进行连接
  //withVelocity: 高级的控制请求和底层的逻辑进行连接
  public void setmotorVoltage(double vol) {
    m_test_motor.setControl(m_test_motor_request.withOutput(vol));
    m_test_motor2.setControl(m_test_motor_request2.withOutput(vol));

  }

  public void setmotorVoltage2(double vol) {
    m_test_motor3.setControl(m_test_motor_request3.withOutput(vol));
    m_test_motor4.setControl(m_test_motor_request4.withOutput(vol));
  }


  //实际控制
  /** Creates a new ExampleSubsystem. */
  public drive() {
      var motorConfigs = new TalonFXConfiguration();

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
      motorConfigs.MotionMagic.MotionMagicJerk = 0; // Jerk is around 0'

      m_test_motor.getConfigurator().apply(motorConfigs);
      m_test_motor2.getConfigurator().apply(motorConfigs);
      m_test_motor3.getConfigurator().apply(motorConfigs);
      m_test_motor4.getConfigurator().apply(motorConfigs);
  }


  public Command Motor_Move_Voltage(double voltage){
    return run(()->{
      setmotorVoltage(voltage); // Set the motor to move at 1000 units per second
    });
  }

  public Command Motor_Move_Voltage2(double voltage){
    return run(()->{
      setmotorVoltage2(voltage); // Set the motor to move at 1000 units per second
    });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}

//拆分，把复杂问题简单化



//Subsystem：
//控制机器人

//假设底盘是在正常的，就只用调试其他子系统

//主系统（机器人）- 子系统（机器人的某个部分）

//把机器人的各个部分拆分开

//子系统1：底盘
//子系统2：电梯
//子系统3：claw

//把小的部分联合起来，就能实现整个机器人的控制 subsystem

//底盘，电梯，Claw


//command：
//命令，指令：告诉机器人执行什么动作
//能把我们的指令转换成机器人的实际动作

//封装好了一些自己的方法
//command里调用我们自己写好的方法，实现机器人的控制

//简单的command写法，完全类似一个方法的封装

//复杂的command：多个简单的command组合起来

//frc编程不同于其他编程的一些特点
//1.其他编程，for while，每一次运行的时间不一样，受限于电脑
//但是frc，没有显式的for 和 while
//periodic(): 每隔20ms轮询一次
//2.