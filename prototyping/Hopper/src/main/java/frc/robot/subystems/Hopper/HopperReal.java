package frc.robot.subystems.Hopper;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.Constants.HopperConstants;

public class HopperReal implements HopperIO {

  private final TalonFX hopperMotor = new TalonFX(20);
  private final PositionVoltage positionRequest = new PositionVoltage(0);
  private StatusSignal<Current> hopperMotorStatorCurrent;
  private StatusSignal<AngularVelocity> hopperMotorVelocityRPS;
  private StatusSignal<Angle> hopperMotorPositionDegrees;
  private StatusSignal<Voltage> hopperMotorVolts;

  public HopperReal() {
    // Top motor configurations
    TalonFXConfiguration hopperConfigs = new TalonFXConfiguration();
    hopperMotor.getConfigurator().apply(hopperConfigs); // reset to default
    hopperConfigs.MotorOutput.Inverted = HopperConstants.intakeMotorInvert;
    hopperConfigs.MotorOutput.NeutralMode = HopperConstants.intakeMotorBrakeMode;
    hopperConfigs.Slot0.kP = HopperConstants.kTopP;
    hopperConfigs.Slot0.kV = HopperConstants.kTopV;
    hopperConfigs.Slot0.kS = HopperConstants.kTopS;
    hopperConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
    hopperConfigs.CurrentLimits.StatorCurrentLimit = HopperConstants.topCurrentLimit;
    hopperMotor.getConfigurator().apply(hopperConfigs);
    // Apply to signals
    hopperMotorStatorCurrent = hopperMotor.getStatorCurrent();
    hopperMotorPositionDegrees = hopperMotor.getPosition();
    //*360 */
    hopperMotorVelocityRPS = hopperMotor.getVelocity();
    hopperMotorVolts = hopperMotor.getMotorVoltage();
    // Set polling frequency and optimizations
    BaseStatusSignal.setUpdateFrequencyForAll(50, hopperMotorStatorCurrent, hopperMotorPositionDegrees);
    hopperMotor.optimizeBusUtilization();
  }



  @Override
  public void updateInputs(HopperIOInputs inputs) {
    BaseStatusSignal.refreshAll(
        hopperMotorStatorCurrent,
    hopperMotorVolts,
        hopperMotorPositionDegrees,hopperMotorVelocityRPS);

    inputs.positionDeg = hopperMotorPositionDegrees.getValueAsDouble()* 360.0;
    inputs.velocityDegPerSec = hopperMotorVelocityRPS.getValueAsDouble() * 360.0;
    inputs.statorCurrent = hopperMotorStatorCurrent.getValueAsDouble();
    inputs.appliedVolts = hopperMotorVolts.getValueAsDouble();
    inputs.motorConnected = hopperMotor.isConnected();
  }

  @Override
  public void setPosition(double angleDeg) {
    hopperMotor.setControl(positionRequest.withPosition(angleDeg / 360.0));
  }

  @Override
  public void stop() {
    hopperMotor.stopMotor();
  }
}