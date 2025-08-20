// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VoltageOut;
//Voltage Out 不受PID影响
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;
import com.ctre.phoenix6.hardware.CANcoder;

import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class drive extends SubsystemBase {
  
  // public static final Command Motor_Move_VelocityTorqueCurrentFOC = null;
  // //声明电机
  //  private final TalonFX m_test_motor = new TalonFX(1, "rio");

  //  private final TalonFX m_test_motor2 = new TalonFX(2, "rio");

  //  private final TalonFX m_test_motor3 = new TalonFX(3, "rio");

  //  private final TalonFX m_test_motor4 = new TalonFX(4, "rio");
  // //请求制，需要一个request
  // private final VelocityTorqueCurrentFOC m_test_motor_request = new VelocityTorqueCurrentFOC(0.0);

  // private final VelocityTorqueCurrentFOC m_test_motor_request2 = new VelocityTorqueCurrentFOC(0.0);
  
  // private final VelocityTorqueCurrentFOC m_test_motor_request3 = new VelocityTorqueCurrentFOC(0.0);

  // private final VelocityTorqueCurrentFOC m_test_motor_request4 = new VelocityTorqueCurrentFOC(0.0);
  // //电机控制：时间速度

  private final CANcoder cancoder_fl = new CANcoder(3,"rio");
  private final TalonFX m_test_motor = new TalonFX(5, "rio");
  private final TalonFX m_test_motor2 = new TalonFX(6, "rio");

  // //withPosition：高级的控制请求和底层的逻辑进行连接
  // //withVelocity: 高级的控制请求和底层的逻辑进行连接


  //请求制，需要一个request
  private final MotionMagicVoltage m_test_motor_request = new MotionMagicVoltage(0.0);
  private final VelocityTorqueCurrentFOC m_test_motor_request2 = new VelocityTorqueCurrentFOC(0.0);

  public void setmotorVelocity(double velocity) {
    m_test_motor2.setControl(m_test_motor_request2.withVelocity(velocity));

  }
  //withPosition：高级的控制请求和底层的逻辑进行连接
  //withVelocity: 高级的控制请求和底层的逻辑进行连接
  public void setmotorPosition(double Position) {
    m_test_motor.setControl(m_test_motor_request.withPosition(Position));

  }

  //记录预期的位置： 
  private final double wantedvalue = 50;
  private final double expected_error =1.0;

  //电机控制：时间速度


  //实际控制
  /** Creates a new ExampleSubsystem. */
  public drive() {

      var motorEncoderConfigs = new CANcoderConfiguration();
      motorEncoderConfigs.MagnetSensor.MagnetOffset=0.0;
      motorEncoderConfigs.MagnetSensor.AbsoluteSensorDiscontinuityPoint=0.5;//实际生活中的电机位置映射到什么范围
      motorEncoderConfigs.MagnetSensor.SensorDirection=SensorDirectionValue.Clockwise_Positive;
      cancoder_fl.getConfigurator().apply(motorEncoderConfigs);
      

      var motorConfigs = new TalonFXConfiguration();

      //每个电机都有的固定参数        
      motorConfigs.Slot0.kS = 0.14;
      motorConfigs.Slot0.kV = 0.0;            //直接控制
      motorConfigs.Slot0.kA = 0;
      motorConfigs.Slot0.kP = 10;
      motorConfigs.Slot0.kI = 0;
      motorConfigs.Slot0.kD = 0;
      motorConfigs.Slot0.GravityType = GravityTypeValue.Arm_Cosine;
      motorConfigs.Slot0.StaticFeedforwardSign = StaticFeedforwardSignValue.UseClosedLoopSign;


      //高级控制才用到下面的参数
      motorConfigs.MotionMagic.MotionMagicAcceleration = 100; // Acceleration is around 40 rps/s
      motorConfigs.MotionMagic.MotionMagicCruiseVelocity = 200; // Unlimited cruise velocity
      motorConfigs.MotionMagic.MotionMagicExpo_kV = 0.12; // kV is around 0.12 V/rps
      motorConfigs.MotionMagic.MotionMagicExpo_kA = 0.1; // Use a slower kA of 0.1 V/(rps/s)
      motorConfigs.MotionMagic.MotionMagicJerk = 0; // Jerk is around 0'
// 电机的配置参数 ： kS kV kA kP kI kD MotionMagicAcceleration MotionMagicCruiseVelocity MotionMagicExpo_kV MotionMagicExpo_kA MotionMagicJerk
//slot → 槽   一块区域 id：0


      //第二套

      var motorConfigs1 = new TalonFXConfiguration();

      motorConfigs1.Slot0.kS = 1.85;
      motorConfigs1.Slot0.kV = 0.0;
      motorConfigs1.Slot0.kA = 0;             
      motorConfigs1.Slot0.kP = 6;
      motorConfigs1.Slot0.kI = 0;
      motorConfigs1.Slot0.kD = 0.1;
      motorConfigs1.Slot0.GravityType = GravityTypeValue.Arm_Cosine;
      motorConfigs1.Slot0.StaticFeedforwardSign = StaticFeedforwardSignValue.UseVelocitySign;
      //高级控制才用到下面的参数
      motorConfigs1.MotionMagic.MotionMagicAcceleration = 100; // Acceleration is around 40 rps/s
      motorConfigs1.MotionMagic.MotionMagicCruiseVelocity = 200; // Unlimited cruise velocity
      motorConfigs1.MotionMagic.MotionMagicExpo_kV = 0.12; // kV is around 0.12 V/rps
      motorConfigs1.MotionMagic.MotionMagicExpo_kA = 0.1; // Use a slower kA of 0.1 V/(rps/s)
      motorConfigs1.MotionMagic.MotionMagicJerk = 0; // Jerk is around 0'     
    


      //少了一环：电机和CANcoder建立联系
      motorConfigs.Feedback.FeedbackRemoteSensorID = cancoder_fl.getDeviceID();
      motorConfigs.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.FusedCANcoder;
      motorConfigs.Feedback.RotorToSensorRatio = 13;

      m_test_motor.getConfigurator().apply(motorConfigs);
      m_test_motor2.getConfigurator().apply(motorConfigs1);
      // m_test_motor3.getConfigurator().apply(motorConfigs);
      // m_test_motor4.getConfigurator().apply(motorConfigs);

      

  }


//Voltage_Out 原理：
//假设最大输出电压是12v， 那么12v对应 1000r/min
//6v                                500r/min
//0v                                0r/min

//开环控制：敞开的系统，他不准确，他也不知道自己准不准确，比较盲目
//闭环控制：闭合的控制，相对准确，当他不准确的时候，他知道自己不准确，并且他自己知道他的状态和预期有差距
//他就能根据这个差距，调整自己，然后接近我们的预期
//闭环有一个反馈




//想要控制速度到50
//40-50-40-50-40-50   →    不稳定
//想要控制位置到100
//90-80-100-90-80-100    →    不稳定



//想要控制速度到50
//48-50-49-50-48-50    →   稳定
//想要控制位置到100
//100-98-97-100    →   稳定


//电机不稳定
//1.不安全    →   电机不受控
//2.


  // //直驱
  // public Command Motor_Move_VelocityTorqueCurrentFOC (double Velocity){
  //   return run(()->{
  //                     setmotorVelocity(Velocity); // Set the motor to move at 1000 units per second
  //                 })
  //                 .until(()->{
  //                               return (Math.abs(m_test_motor.getPosition().getValueAsDouble()-wantedvalue) < expected_error);
  //                            });
  // }

  //转向
  public Command Motor_Move_MotionMagicVoltage (double Position,double velocity){
    return run(()->{
                      setmotorPosition(Position); // Set the motor to move at 1000 units per second
                      setmotorVelocity(velocity);
                  })
                  .until(()->{
                                return (Math.abs(m_test_motor.getPosition().getValueAsDouble()-Position) < expected_error);
                             });
  }


  public Command Motor_stop (){
    return runOnce(()->{
                      setmotorVelocity(0);
                  });
  }

  /**andthen()
   * until()
   * run()
   * runonce()
   * runend
   * 
   * 小的一步步组成，复杂的
   * 
   * whileTrue
   * onTrue
  */

  // public Command Motor_Velocity_withRunend(double velocity){
  //   return runEnd(()->{
  //     setmotorVelocity(velocity);
  //   },
  //   ()->{
  //     setmotorVelocity(0);
  //   });
  // }

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



/**
电机参数的说明

Position Control
kG - output to overcome gravity (output)
克服重力的输出（输出）
kS - Velocity Sign: unused; Closed-Loop Sign: output to overcome static friction (output)
闭环：克服静摩擦的输出（输出）
kV - unused, as there is no target velocity
未使用，因为没有目标速度
kA - unused, as there is no target acceleration
未使用，因为没有目标加速度
kP - output per unit of error in position (output/rotation)
每单位位置误差的输出（输出/旋转）
kI - output per unit of integrated error in position (output/(rotation*s))
每单位积分误差的输出（输出/（旋转*s））
kD - output per unit of error derivative in position (output/rps)
每单位位置误差导数的输出（输出/rps

电机参数的调试方法
1.Set all gains to zero.
将所有参数设为0

2.Determine kG if using an elevator or arm.
克服重力的参数，kG从0开始增加，知道松手电梯能大概稳定在当前位置，不会下坠

3.Select the appropriate Static Feedforward Sign for your closed-loop type.
如果是速度控制就用“UseVelocitySign", 位置控制就用"UseClosedLoopSign"

4.Increase kS until just before the motor moves.
逐步增加kS直到电机微微有反应，处在一种动与不动的临界点之间

5.If using velocity setpoints, increase kV until the output velocity closely matches the velocity setpoints.
如果你用速度控制并且需要设定速度到某一个值，可以逐步增加kV知道速度达到设定值
kV是一个放大系数，当我的速度不够时，用这个来提高我的速度设定值

6.Increase kP until the output starts to oscillate around the setpoint.
逐步增加kP直到我的当前位置（预期设定的速度）开始在设定位置的（设定速度）附近震动

7.Increase kD as much as possible without introducing jittering to the response.
逐步增加kD直到引入了新的震动
*/