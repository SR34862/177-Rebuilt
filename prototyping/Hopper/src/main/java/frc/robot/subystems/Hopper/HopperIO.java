package frc.robot.subystems.Hopper;

import org.littletonrobotics.junction.AutoLog;

public interface HopperIO {

  @AutoLog
  class HopperIOInputs {
    public double positionDeg = 0.0;
    public double velocityDegPerSec = 0.0;
    public double statorCurrent = 0.0;

    public double appliedVolts = 0.0;
    public boolean motorConnected = false;
  }

  default void updateInputs(HopperIOInputs inputs) {
  }

  default void setVoltage(double volts) {}
  default void setPosition(double angleDeg) {}

  default void stop() {}
}