package frc.robot.subsystems.Shooter;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Minute;
import static edu.wpi.first.units.Units.Rotation;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import frc.robot.Constants.ShooterConstants;;

public class ShooterReal implements ShooterIO {

  private final TalonFX positionMotor = new TalonFX(10);
  private final TalonFX velocityMotor = new TalonFX(11);

  private final VelocityTorqueCurrentFOC requestVelocity = new VelocityTorqueCurrentFOC(0);
  private final PositionTorqueCurrentFOC requestPosition = new PositionTorqueCurrentFOC(0);
  private StatusSignal<AngularVelocity> velocityRPS;
  private StatusSignal<Angle> poositionDeg;

  public ShooterReal() {
    // Top motor configurations
    TalonFXConfiguration topConfigs = new TalonFXConfiguration();
    positionMotor.getConfigurator().apply(topConfigs); // reset to default
    topConfigs.MotorOutput.Inverted = ShooterConstants.topMotorInvert;
    topConfigs.MotorOutput.NeutralMode = ShooterConstants.topMotorBrakeMode;
    topConfigs.Slot0.kP = ShooterConstants.kTopP;
    topConfigs.Slot0.kV = ShooterConstants.kTopV;
    topConfigs.Slot0.kS = ShooterConstants.kTopS;
    topConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
    topConfigs.CurrentLimits.StatorCurrentLimit = ShooterConstants.topCurrentLimit;
    positionMotor.getConfigurator().apply(topConfigs);
    // Bottom motor configurations
    TalonFXConfiguration bottomConfigs = new TalonFXConfiguration();
    velocityMotor.getConfigurator().apply(bottomConfigs); // reset to default
    bottomConfigs.MotorOutput.Inverted = ShooterConstants.bottomMotorInvert;
    bottomConfigs.MotorOutput.NeutralMode = ShooterConstants.bottomMotorBrakeMode;
    bottomConfigs.Slot0.kP = ShooterConstants.kBottomP;
    bottomConfigs.Slot0.kV = ShooterConstants.kBottomV;
    bottomConfigs.Slot0.kS = ShooterConstants.kBottomS;
    bottomConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
    bottomConfigs.CurrentLimits.StatorCurrentLimit = ShooterConstants.bottomCurrentLimit;
    velocityMotor.getConfigurator().apply(bottomConfigs);
    // Apply to signals
    velocityRPS = velocityMotor.getVelocity();
    // Set polling frequency and optimizations
    BaseStatusSignal.setUpdateFrequencyForAll(50, velocityRPS);
    positionMotor.optimizeBusUtilization();
    velocityMotor.optimizeBusUtilization();
  }

  @Override
  public void updateInputs(ShooterIOInputs inputs) {
    BaseStatusSignal.refreshAll(
        velocityRPS, poositionDeg);

    inputs.velocityRPM = velocityRPS.getValue().in(Rotation.per(Minute));
    inputs.positionDeg = poositionDeg.getValue().in(Degrees);
    inputs.velocityConnected = velocityMotor.isConnected();
    inputs.positionConnected = positionMotor.isConnected();
  }

  public void setPosition(double position) {
    requestPosition.withPosition(position);
  }

  public void setVelocity(double velocity) {
    requestVelocity.withVelocity(velocity);
  }

  public void holdPosition(){
     setPosition(getPosition());
  }

  public double getPosition() {
    return Rotation2d.fromRotations(positionMotor.getPosition().getValueAsDouble()).getDegrees();
  }

  public double getVelocity() {
    return positionMotor.getVelocity().getValueAsDouble();
  }

  @Override
  public void stop() {
    positionMotor.stopMotor();
    velocityMotor.stopMotor();
  }
}