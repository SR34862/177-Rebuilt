package frc.robot.subystems.Hopper;

import org.littletonrobotics.junction.AutoLog;

public interface HopperIO {

  @AutoLog
  class ShooterIOInputs {
    public double velocityRPM = 0.0;
    public double positionDeg = 0.0;
    public boolean velocityConnected = false;
    public boolean positionConnected = false;
  }

  default void updateInputs(ShooterIOInputs inputs) {
  }

  public default void setVelocity(double velocity) {
  }
  default void stop() {
  }
}