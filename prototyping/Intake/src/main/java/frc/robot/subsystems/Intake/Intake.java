package frc.robot.subsystems.Intake;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  private final IntakeIO io;
  private final IntakeIOInputsAutoLogged inputs =
      new IntakeIOInputsAutoLogged();
  private IntakeState desiredState;
  public Intake(IntakeIO io) {
    this.io = io;
    desiredState = new IntakeState();
    desiredState.setState(IntakeState.State.IDLE);
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Intake", inputs);

    Logger.recordOutput("Intake/State", desiredState.getCurrentState());
  }

  public void setManualVelocity(double intakeSpeed) {
    io.setManualVelocity(intakeSpeed);
    desiredState.setState(IntakeState.State.MANUAL);
  }

  public void setSpeed(IntakeState.State state){
    desiredState.setState(state);
    io.setSpeed( desiredState.getOutput() );
  }

  public void setState(IntakeState.State state) {
    desiredState.setState(state);
  }
}