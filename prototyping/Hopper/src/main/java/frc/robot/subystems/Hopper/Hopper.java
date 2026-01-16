package frc.robot.subystems.Hopper;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

|

public class Hopper extends SubsystemBase {

  private final HopperIO io;
  private final HopperIOInputsAutoLogged inputs =
      new HopperIOInputsAutoLogged();

  private HopperState desiredState = HopperState.IDLE;
  private boolean manualMode = false;
  private double manualVolts = 0.0;

  public Hopper(HopperIO io) {
    this.io = io;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Hopper", inputs);

    Logger.recordOutput("Hopper/State", desiredState.name());
    Logger.recordOutput("Hopper/ManualMode", manualMode);

    if (manualMode) {
      io.setVoltage(manualVolts);
    } else {
      io.setPosition(desiredState.angleDeg);
    }
  }

  public void setManualVoltage(double volts) {
        manualMode = true;
    manualVolts = volts;
  }

  public void exitManualMode() {
      manualMode = false;
  }

  public void setState(HopperState state) {
        manualMode = false;
    desiredState = state;
  }

  public boolean atSetpoint() {
    return Math.abs(inputs.positionDeg - desiredState.angleDeg) < 2.0;
  }

  public void stop() {
    io.stop();
  }
}