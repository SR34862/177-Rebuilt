package frc.robot.subystems.Hopper;

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

public class HopperReal implements HopperIO {

  private final TalonFX positionMotor = new TalonFX(10);
  private final TalonFX velocityMotor = new TalonFX(11);

  private final VelocityTorqueCurrentFOC requestVelocity = new VelocityTorqueCurrentFOC(0);
  private final PositionTorqueCurrentFOC requestPosition = new PositionTorqueCurrentFOC(0);
  private StatusSignal<AngularVelocity> velocityRPS;
  private StatusSignal<Angle> poositionDeg;

  public HopperReal() {
    // Bottom motor configurations
    TalonFXConfiguration bottomConfigs = new TalonFXConfiguration();
    velocityMotor.getConfigurator().apply(bottomConfigs); // reset to default
    bottomConfigs.MotorOutput.Inverted = HopperConstants.bottomMotorInvert;
    bottomConfigs.MotorOutput.NeutralMode = HopperConstants.bottomMotorBrakeMode;
    bottomConfigs.Slot0.kP = HopperConstants.kBottomP;
    bottomConfigs.Slot0.kV = HopperConstants.kBottomV;
    bottomConfigs.Slot0.kS = HopperConstants.kBottomS;
    bottomConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
    bottomConfigs.CurrentLimits.StatorCurrentLimit = HopperConstants.bottomCurrentLimit;
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


  public void setVelocity(double velocity) {
    requestVelocity.withVelocity(velocity);
  }

  public double getVelocity() {
    return velocityMotor.getVelocity().getValueAsDouble();
  }

  @Override
  public void stop() {
    velocityMotor.stopMotor();
  }
}