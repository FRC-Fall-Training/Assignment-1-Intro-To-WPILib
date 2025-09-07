package frc.robot.subsystems.example;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public final class ExampleConstants {
  public static final double GEAR_RATIO = 1;

  public static final boolean IS_INVERTED = false;
  public static final boolean IS_BRAKE = false;

  public static final CurrentLimits CURRENT_LIMITS;
  public static final MotorOutput MOTOR_OUTPUT;

  public static record CurrentLimits(double SUPPLY_CURRENT_LIMIT, double STATOR_CURRENT_LIMIT) {}

  public static record MotorOutput(NeutralModeValue NEUTRAL_MODE, InvertedValue INVERTED_VALUE) {}

  static {
    CURRENT_LIMITS = new CurrentLimits(40, 40);
    MOTOR_OUTPUT =
        new MotorOutput(
            IS_BRAKE ? NeutralModeValue.Brake : NeutralModeValue.Coast,
            IS_INVERTED
                ? InvertedValue.Clockwise_Positive
                : InvertedValue.CounterClockwise_Positive);
  }
}
