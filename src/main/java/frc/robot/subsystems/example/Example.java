package frc.robot.subsystems.example;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.util.LoggedTracer;
import lombok.Getter;
import lombok.Setter;
import org.littletonrobotics.junction.Logger;

public class Example extends SubsystemBase {
  ExampleIO io;
  @Getter @Setter private static ExampleState exampleState;

  protected final ExampleIOInputsAutoLogged inputs = new ExampleIOInputsAutoLogged();

  private final Debouncer motorConnectedDebouncer =
      new Debouncer(0.5, Debouncer.DebounceType.kFalling);
  private final Alert disconnected;

  public Example(ExampleIO io) {
    this.io = io;

    disconnected = new Alert(getName() + " motor disconnected!", Alert.AlertType.kWarning);
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Example", inputs);

    disconnected.set(
        !motorConnectedDebouncer.calculate(inputs.data.connected()) && !Robot.isJITing());

    LoggedTracer.record(
        "Example"); // Profiling a bit weird since we have a lot of interrupts that run their own
    // code while periodic doesnt really handle the state...
  }

  public Command setForwardsCommand() {
    System.out.println("Setting ExampleState to Forwards!");
    return new InstantCommand(() -> setExampleState(ExampleState.FORWARDS));
  }

  public Command runForwardsCommand(double voltage) {
    System.out.println("Example Running Forwards at " + voltage + " volts!");
    return new InstantCommand(() -> io.runVolts(voltage));
  }

  public Command setBackwardsCommand() {
    System.out.println("Setting ExampleState to Backwards!");
    return new InstantCommand(() -> setExampleState(ExampleState.BACKWARDS));
  }

  public Command runReverseCommand(double voltage) {
    System.out.println("Example Running Backwards at " + voltage + " volts!");
    return new InstantCommand(() -> io.runVolts(-voltage));
  }

  public Command stopCommand() {
    System.out.println("Example Stopping!");
    return new InstantCommand(() -> io.stop());
  }

  public Command runExampleCommand(double voltage) {
    return new ConditionalCommand(
        runForwardsCommand(voltage), // if true
        runReverseCommand(voltage), // if false
        () -> {
          return exampleState.equals(
              ExampleState
                  .FORWARDS); // if statement conditional if(exampleState == ExampleState.FORWARDS)
        });
  }

  public enum ExampleState {
    FORWARDS,
    BACKWARDS
  }
}
