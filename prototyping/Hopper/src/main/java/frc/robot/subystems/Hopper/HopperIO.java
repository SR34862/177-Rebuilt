package frc.robot.subystems.Hopper;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.AngularVelocity;

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

  public default void setManualVelocity(double velocity) {
  }

  public default void setSpeed(AngularVelocity velocity) {
  }

  public default void setSpeed(double velocity) {
  }

  public default void stop() {}
}