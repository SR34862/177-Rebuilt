package frc.robot.subystems.Hopper;

import static edu.wpi.first.units.Units.Minute;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.Second;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import frc.robot.Constants;

public class HopperReal implements HopperIO {

  private final TalonFX hopperMotor = new TalonFX(30);
  private VelocityDutyCycle rollerRequest = new VelocityDutyCycle(0);
  private StatusSignal<Current> hopperMotorStatorCurrent;
  private StatusSignal<AngularVelocity> hopperMotorVelocity;

  public HopperReal() {
    // Top motor configurations
    TalonFXConfiguration hopperConfigs = new TalonFXConfiguration();
    hopperMotor.getConfigurator().apply(hopperConfigs); // reset to default
    hopperConfigs.MotorOutput.Inverted = Constants.HopperConstants.hopperMotorInvert;
    hopperConfigs.MotorOutput.NeutralMode = Constants.HopperConstants.hopperMotorBrakeMode;
    hopperConfigs.Slot0.kP = Constants.HopperConstants.kTopP;
    hopperConfigs.Slot0.kV = Constants.HopperConstants.kTopV;
    hopperConfigs.Slot0.kS = Constants.HopperConstants.kTopS;
    hopperConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
    hopperConfigs.CurrentLimits.StatorCurrentLimit = Constants.HopperConstants.topCurrentLimit;
    hopperMotor.getConfigurator().apply(hopperConfigs);
    // Apply to signals
    hopperMotorStatorCurrent = hopperMotor.getStatorCurrent();
    hopperMotorVelocity = hopperMotor.getVelocity();
    // Set polling frequency and optimizations
    BaseStatusSignal.setUpdateFrequencyForAll(50, hopperMotorStatorCurrent, hopperMotorVelocity);
    hopperMotor.optimizeBusUtilization();

  }

  @Override
  public void updateInputs(HopperIOInputs inputs) {
    BaseStatusSignal.refreshAll(hopperMotorVelocity, hopperMotorStatorCurrent);
    hopperMotorStatorCurrent = hopperMotor.getStatorCurrent();
    hopperMotorVelocity = hopperMotor.getVelocity();
    inputs.statorCurrent = hopperMotorStatorCurrent.getValueAsDouble();
    inputs.velocityDegPerSec = hopperMotorVelocity.getValue().in(Rotations.per(Second));
  }

  @Override
  public void setManualVelocity(double velocity) {
    hopperMotor.set(velocity);
  }

  @Override
  public void setSpeed(AngularVelocity velocity) {
    hopperMotor.setControl(rollerRequest.withVelocity(velocity));
  }

  @Override
  public void setSpeed(double velocity) {
    hopperMotor.setControl(rollerRequest.withVelocity(velocity));
  }

  @Override
  public void stop() {
    hopperMotor.stopMotor();
  }
}