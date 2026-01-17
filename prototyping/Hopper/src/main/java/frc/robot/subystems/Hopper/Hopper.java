package frc.robot.subystems.Hopper;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hopper extends SubsystemBase {
  private final HopperIO io;
  private final HopperIOInputsAutoLogged inputs =
      new HopperIOInputsAutoLogged();
  private HopperState desiredState;
  public Hopper(HopperIO io) {
    this.io = io;
    desiredState = new HopperState();
    desiredState.setState(HopperState.State.IDLE);
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Hopper", inputs);

    Logger.recordOutput("Hopper/State", desiredState.getCurrentState());
  }

  public void setManualVelocity(double intakeSpeed) {
    io.setManualVelocity(intakeSpeed);
    desiredState.setState(HopperState.State.MANUAL);
  }

  public void setSpeed(HopperState.State state){
    desiredState.setState(state);
    io.setSpeed( desiredState.getOutput() );
  }

  public void setState(HopperState.State state) {
    desiredState.setState(state);
  }
}