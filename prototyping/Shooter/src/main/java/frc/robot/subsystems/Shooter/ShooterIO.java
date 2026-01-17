package frc.robot.subsystems.Shooter;

import org.littletonrobotics.junction.AutoLog;

public interface ShooterIO {

  @AutoLog
  class ShooterIOInputs {
    public double velocityRPM = 0.0;
    public double positionDeg = 0.0;
    public boolean velocityConnected = false;
    public boolean positionConnected = false;
  }

  default void updateInputs(ShooterIOInputs inputs) {
  }

  public default void setPosition(double position) {
  }

  public default void setVelocity(double velocity) {
  }
  public default void holdPosition(){
    
  }
  default void stop() {
  }
}