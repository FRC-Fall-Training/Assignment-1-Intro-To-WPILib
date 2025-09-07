package frc.robot;

import frc.robot.util.LoggedTracer;
import lombok.Getter;
import lombok.Setter;

public class RobotState {

  @Getter @Setter private static RobotMode mode;

  static {
  }

  public RobotState() {}

  private static RobotState instance;

  public static RobotState getInstance() {
    if (instance == null) instance = new RobotState();
    return instance;
  }

  public static void periodic() {
    LoggedTracer.record("RobotState");
  }

  public enum RobotMode {
    DISABLED,
    TELEOP,
    AUTO;

    public static boolean enabled(RobotMode mode) {
      return mode.equals(TELEOP) || mode.equals(AUTO);
    }

    public static boolean disabled(RobotMode mode) {
      return mode.equals(DISABLED);
    }

    public static boolean teleop(RobotMode mode) {
      return mode.equals(TELEOP);
    }

    public static boolean auto(RobotMode mode) {
      return mode.equals(AUTO);
    }

    public static boolean enabled() {
      return enabled(RobotState.getMode());
    }

    public static boolean disabled() {
      return disabled(RobotState.getMode());
    }

    public static boolean teleop() {
      return teleop(RobotState.getMode());
    }

    public static boolean auto() {
      return auto(RobotState.getMode());
    }
  }
}
