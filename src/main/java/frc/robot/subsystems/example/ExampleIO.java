package frc.robot.subsystems.example;

import org.littletonrobotics.junction.AutoLog;

public interface ExampleIO {
  @AutoLog
  class ExampleIOInputs {
    public ExampleIOData data = new ExampleIOData(0, 0, 0, 0, 0, 0, false);
  }

  record ExampleIOData(
      double positionRotations,
      double velocityRotationsPerSec,
      double appliedVoltage,
      double supplyCurrentAmps,
      double torqueCurrentAmps,
      double tempCelsius,
      boolean connected) {}

  default void updateInputs(ExampleIOInputs inputs) {}

  default void runVolts(double volts) {}

  default void runTorqueCurrent(double amps) {}

  default void stop() {}

  default void setStatorCurrentLimit(double currentLimit) {}

  default void setBrakeMode(boolean enabled) {}
}
