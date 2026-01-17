package frc.robot.subsystems.Turret;

import static edu.wpi.first.units.Units.Degrees;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase {

    private final TurretIO io;
    private final TurretIOInputsAutoLogged inputs = new TurretIOInputsAutoLogged();

    private TurretState desiredState = TurretState.IDLE;
    private boolean manualMode = false;
    private double manualVolts = 0.0;
    private Rotation2d manualPosition;

    public Turret(TurretIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Turret", inputs);

        Logger.recordOutput("Turret/State", desiredState.getCurrentState());
        Logger.recordOutput("Turret/ManualMode", manualMode);
    }

    public void setManualVoltage(double volts) {
        manualMode = true;
        manualVolts = volts;
    }

    public void exitManualMode() {
        manualMode = false;
    }

    public void setState(TurretState state) {
        manualMode = false;
        desiredState = state;
    }
    public void setState(TurretState state, double positionDeg) {
        manualMode = false;
        desiredState = state;
        manualPosition = Rotation2d.fromDegrees(positionDeg);
    }

    public boolean atSetpoint() {
        return Math.abs(inputs.positionDeg - manualPosition.getDegrees()) < 2.0;
    }
}