package frc.robot.subsystems.Intake;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.AngularVelocity;

public interface IntakeIO {

  @AutoLog
  class IntakeIOInputs {
    public double intakeVelocityRPM = 0.0;

    public double intakeAppliedCurrent = 0.0;

    public boolean intakeConnected = false;
  }

  default void updateInputs(IntakeIOInputs inputs) {
  }

  default void setManualVelocity(double velocity) {
  }

  default void setSpeed(AngularVelocity velocity) {
  }

  default void setSpeed(double velocity) {
  }

  default void stop() {
  }
}
