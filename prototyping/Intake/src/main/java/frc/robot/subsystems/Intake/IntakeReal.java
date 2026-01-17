package frc.robot.subsystems.Intake;

import static edu.wpi.first.units.Units.Minute;
import static edu.wpi.first.units.Units.Rotations;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import frc.robot.Constants;

public class IntakeReal implements IntakeIO {

  private final TalonFX intakeMotor = new TalonFX(30);
  private VelocityDutyCycle rollerRequest = new VelocityDutyCycle(0);
  private StatusSignal<Current> intakeMotorStatorCurrent;
  private StatusSignal<AngularVelocity> intakeMotorVelocity;

  public IntakeReal() {
    // Top motor configurations
    TalonFXConfiguration intakeConfigs = new TalonFXConfiguration();
    intakeMotor.getConfigurator().apply(intakeConfigs); // reset to default
    intakeConfigs.MotorOutput.Inverted = Constants.IntakeConstants.intakeMotorInvert;
    intakeConfigs.MotorOutput.NeutralMode = Constants.IntakeConstants.intakeMotorBrakeMode;
    intakeConfigs.Slot0.kP = Constants.IntakeConstants.kTopP;
    intakeConfigs.Slot0.kV = Constants.IntakeConstants.kTopV;
    intakeConfigs.Slot0.kS = Constants.IntakeConstants.kTopS;
    intakeConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
    intakeConfigs.CurrentLimits.StatorCurrentLimit = Constants.IntakeConstants.topCurrentLimit;
    intakeMotor.getConfigurator().apply(intakeConfigs);
    // Apply to signals
    intakeMotorStatorCurrent = intakeMotor.getStatorCurrent();
    intakeMotorVelocity = intakeMotor.getVelocity();
    // Set polling frequency and optimizations
    BaseStatusSignal.setUpdateFrequencyForAll(50, intakeMotorStatorCurrent, intakeMotorVelocity);
    intakeMotor.optimizeBusUtilization();

  }

  @Override
  public void updateInputs(IntakeIOInputs inputs) {
    BaseStatusSignal.refreshAll(intakeMotorVelocity, intakeMotorStatorCurrent);
    intakeMotorStatorCurrent = intakeMotor.getStatorCurrent();
    intakeMotorVelocity = intakeMotor.getVelocity();
    inputs.intakeAppliedCurrent = intakeMotorStatorCurrent.getValueAsDouble();
    inputs.intakeVelocityRPM = intakeMotorVelocity.getValue().in(Rotations.per(Minute));
  }

  @Override
  public void setManualVelocity(double velocity) {
    intakeMotor.set(velocity);
  }

  @Override
  public void setSpeed(AngularVelocity velocity) {
    intakeMotor.setControl(rollerRequest.withVelocity(velocity));
  }

  @Override
  public void setSpeed(double velocity) {
    intakeMotor.setControl(rollerRequest.withVelocity(velocity));
  }

  @Override
  public void stop() {
    intakeMotor.stopMotor();
  }
}