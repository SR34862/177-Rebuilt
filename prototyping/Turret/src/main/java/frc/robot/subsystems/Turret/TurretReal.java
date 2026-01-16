package frc.robot.subsystems.Turret;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.Constants.TurretConstants;

public class TurretReal implements TurretIO {

  private final TalonFX turretMotor = new TalonFX(20);
  private final PositionVoltage positionRequest = new PositionVoltage(0);
  private StatusSignal<Current> turretMotorStatorCurrent;
  private StatusSignal<AngularVelocity> turretMotorVelocityRPS;
  private StatusSignal<Angle> turretMotorPositionDegrees;
  private StatusSignal<Voltage> turretMotorVolts;

  public TurretReal() {
    // Top motor configurations
    TalonFXConfiguration turretConfigs = new TalonFXConfiguration();
    turretMotor.getConfigurator().apply(turretConfigs); // reset to default
    turretConfigs.MotorOutput.Inverted = TurretConstants.intakeMotorInvert;
    turretConfigs.MotorOutput.NeutralMode = TurretConstants.intakeMotorBrakeMode;
    turretConfigs.Slot0.kP = TurretConstants.kTopP;
    turretConfigs.Slot0.kV = TurretConstants.kTopV;
    turretConfigs.Slot0.kS = TurretConstants.kTopS;
    turretConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
    turretConfigs.CurrentLimits.StatorCurrentLimit = TurretConstants.topCurrentLimit;
    turretMotor.getConfigurator().apply(turretConfigs);
    // Apply to signals

    turretMotorStatorCurrent = turretMotor.getStatorCurrent();
    turretMotorPositionDegrees = turretMotor.getPosition();
    turretMotorVelocityRPS = turretMotor.getVelocity();
    turretMotorVolts = turretMotor.getMotorVoltage();
    // Set polling frequency and optimizations
    BaseStatusSignal.setUpdateFrequencyForAll(50, turretMotorStatorCurrent, turretMotorPositionDegrees);
    turretMotor.optimizeBusUtilization();
  }

  @Override
  public void updateInputs(TurretIOInputs inputs) {
    BaseStatusSignal.refreshAll(
        turretMotorStatorCurrent,
        turretMotorVolts,
        turretMotorPositionDegrees, turretMotorVelocityRPS);

    inputs.positionDeg = turretMotorPositionDegrees.getValueAsDouble() * 360.0;
    inputs.velocityDegPerSec = turretMotorVelocityRPS.getValueAsDouble() * 360.0;
    inputs.statorCurrent = turretMotorStatorCurrent.getValueAsDouble();
    inputs.appliedVolts = turretMotorVolts.getValueAsDouble();
    ;
    inputs.motorConnected = turretMotor.isConnected();
  }

  @Override
  public void setPosition(double angleDeg) {
    turretMotor.setControl(positionRequest.withPosition(Rotation2d.fromDegrees(angleDeg).getRotations()));
  }

  @Override
  public void stop() {
    turretMotor.stopMotor();
  }
}
