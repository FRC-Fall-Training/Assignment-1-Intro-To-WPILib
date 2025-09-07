package frc.robot.subsystems.example;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import frc.robot.Constants;

public class ExampleIOSim implements ExampleIO {
  private final DCMotorSim motor;
  private final DCMotor gearbox;
  private double appliedVoltage = 0.0;

  public ExampleIOSim() {
    gearbox = DCMotor.getKrakenX60Foc(1);
    motor =
        new DCMotorSim(
            LinearSystemId.createDCMotorSystem(gearbox, 0.01, ExampleConstants.GEAR_RATIO),
            gearbox); // idk moi
  }

  @Override
  public void updateInputs(ExampleIOInputs inputs) {
    if (DriverStation.isDisabled()) {
      runVolts(0.0);
    }

    motor.update(Constants.loopPeriodSecs);
    inputs.data =
        new ExampleIOData(
            motor.getAngularPositionRotations(),
            motor.getAngularVelocityRPM(),
            appliedVoltage,
            motor.getCurrentDrawAmps(),
            gearbox.getCurrent(motor.getAngularVelocityRPM(), appliedVoltage),
            0.0,
            false);
  }

  @Override
  public void runVolts(double volts) {
    appliedVoltage = MathUtil.clamp(volts, -12.0, 12.0);
    motor.setInputVoltage(appliedVoltage);
  }

  @Override
  public void runTorqueCurrent(double amps) {
    runVolts(gearbox.getVoltage(gearbox.getTorque(amps), motor.getAngularVelocityRadPerSec()));
  }

  @Override
  public void stop() {
    runVolts(0.0);
  }
}
