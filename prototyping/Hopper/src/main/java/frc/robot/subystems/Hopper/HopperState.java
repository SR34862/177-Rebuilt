package frc.robot.subystems.Hopper;

public enum HopperState {
  IDLE(0.0),
  ON(0.0),
  OFF(45.0);

  public final double angleDeg;

  HopperState(double angleDeg) {
    this.angleDeg = angleDeg;
  }
}