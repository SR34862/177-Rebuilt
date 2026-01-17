package frc.robot.subsystems.Intake;

import frc.robot.Constants;

public class IntakeState {

  public enum State {
    IDLE,
    MANUAL,
    FORWARD,
    REVERSE
  }

  private State currentState = State.IDLE;
  private double manualSpeed = 0.0;

  /** Set the intake to a predefined state */
  public void setState(State state) {
    this.currentState = state;
  }

  /** Set manual speed and switch to MANUAL mode */
  public void setManualSpeed(double speed) {
    manualSpeed = speed;
    currentState = State.MANUAL;
  }

  /** Returns the motor output based on the current state */
  public double getOutput() {
    return switch (currentState) {
      case IDLE -> Constants.IntakeConstants.idleRollerSpeed;
      case FORWARD -> Constants.IntakeConstants.forwardRollerSpeed;
      case REVERSE -> Constants.IntakeConstants.reverseRollerSpeed;
      case MANUAL -> manualSpeed;
    };
  }

  public State getCurrentState() {
    return currentState;
  }
}
