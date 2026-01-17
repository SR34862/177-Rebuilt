package frc.robot.subystems.Hopper;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hopper extends SubsystemBase {

  private final HopperIO io;
  private final HopperIOInputsAutoLogged inputs = new HopperIOInputsAutoLogged();

  private HopperState desiredState;

  public Hopper(HopperIO io) {
    this.io = io;
    desiredState = new HopperState();
    desiredState.setState(State.IDLE);
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Shooter", inputs);
    Logger.recordOutput("Shooter/State", desiredState.currentState);
  }

  public void setState(HopperState.State state){
    setVelocity(state);
  }


  public void setVelocity(HopperState.State state) {
    desiredState.setState(state);
    io.setVelocity(desiredState.getOutput().speed);
  }


  public void stop() {
    io.stop();
  }
}