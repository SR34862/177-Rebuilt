package frc.robot.subsystems.Turret;

import org.littletonrobotics.junction.AutoLog;

public interface TurretIO {

  @AutoLog
  class TurretIOInputs {
    public double positionDeg = 0.0;
    public double velocityDegPerSec = 0.0;
    public double statorCurrent = 0.0;

    public double appliedVolts = 0.0;
    public boolean motorConnected = false;
  }

  default void updateInputs(TurretIOInputs inputs) {
  }

  default void setVoltage(double volts) {}
  default void setPosition(double angleDeg) {}

  default void stop() {}
}
