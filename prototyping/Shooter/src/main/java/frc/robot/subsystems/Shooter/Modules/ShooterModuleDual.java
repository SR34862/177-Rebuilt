package frc.robot.subsystems.Shooter.Modules;

import static edu.wpi.first.units.Units.Rotation;
import static edu.wpi.first.units.Units.Seconds;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Shooter.ShooterIO.ShooterIOInputs;
import frc.robot.subsystems.Shooter.ShooterState;;

public class ShooterModuleDual implements ShooterModuleInterface {

  private final TalonFX shooterMainMotorLeft = new TalonFX(17);
  private final TalonFX shooterMainMotorRight = new TalonFX(22);

  private final TalonFX shooterIntakeWheelMotor = new TalonFX(30);
  private final TalonFX backspinWheelMotor = new TalonFX(2);

  private final VelocityTorqueCurrentFOC velShooterRequest = new VelocityTorqueCurrentFOC(0);
  private final VelocityTorqueCurrentFOC velBackspinRequest = new VelocityTorqueCurrentFOC(0);
  private final VelocityTorqueCurrentFOC velIntakeRequest = new VelocityTorqueCurrentFOC(0);
  private StatusSignal<AngularVelocity> velocityOfMainFlywheeRightRPS;
  private StatusSignal<AngularVelocity> velocityOfMainFlywhelLeftRPS;
  private StatusSignal<AngularVelocity> velocityOfbackspinWheelMotorRPS;
  private StatusSignal<AngularVelocity> velocityOfIntakeWheelRPS;
  private StatusSignal<Current> statorCurrentOfMainFlywheelRightAmps;
  private StatusSignal<Current> statorCurrentOfMainFlywheelLeftAmps;
  private StatusSignal<Current> statorCurrentOfBackspinAmps;
  private StatusSignal<Current> statorCurrentofIntakeAmps;

  public ShooterModuleDual(Slot0Configs flywheel, Slot0Configs intake, Slot0Configs backspin) {
    configureShooterFlywheel(flywheel);
    configureIntakeWheel(intake);
    configurebackspinWheelMotor(backspin);
    // Apply to signals
    velocityOfMainFlywheeRightRPS = shooterMainMotorRight.getVelocity();
    velocityOfMainFlywhelLeftRPS = shooterMainMotorLeft.getVelocity();
    velocityOfbackspinWheelMotorRPS = backspinWheelMotor.getVelocity();
    velocityOfIntakeWheelRPS = shooterIntakeWheelMotor.getVelocity();
    statorCurrentOfMainFlywheelRightAmps = shooterMainMotorRight.getStatorCurrent();
    statorCurrentOfMainFlywheelLeftAmps = shooterMainMotorLeft.getStatorCurrent();
    statorCurrentOfBackspinAmps = backspinWheelMotor.getStatorCurrent();
    statorCurrentofIntakeAmps = shooterIntakeWheelMotor.getStatorCurrent();
    // Set polling frequency and optimizations
    BaseStatusSignal.setUpdateFrequencyForAll(50, velocityOfMainFlywheeRightRPS);
    BaseStatusSignal.setUpdateFrequencyForAll(50, velocityOfMainFlywhelLeftRPS);
    BaseStatusSignal.setUpdateFrequencyForAll(50, velocityOfbackspinWheelMotorRPS);
    BaseStatusSignal.setUpdateFrequencyForAll(50, velocityOfIntakeWheelRPS);
    BaseStatusSignal.setUpdateFrequencyForAll(50, statorCurrentOfMainFlywheelRightAmps);
    BaseStatusSignal.setUpdateFrequencyForAll(50, statorCurrentOfMainFlywheelLeftAmps);
    BaseStatusSignal.setUpdateFrequencyForAll(50, statorCurrentOfBackspinAmps);
    BaseStatusSignal.setUpdateFrequencyForAll(50, statorCurrentofIntakeAmps);
    shooterMainMotorLeft.optimizeBusUtilization();
    shooterMainMotorRight.optimizeBusUtilization();
    shooterIntakeWheelMotor.optimizeBusUtilization();
    backspinWheelMotor.optimizeBusUtilization();
    Logger.recordOutput("Motor/TopTopWheel/Licensed", backspinWheelMotor.getIsProLicensed().getValue());
    Logger.recordOutput("Motor/TopBottomWheel/Licensed", shooterIntakeWheelMotor.getIsProLicensed().getValue());
    Logger.recordOutput("Motor/BottomLeft/Licensed", shooterMainMotorLeft.getIsProLicensed().getValue());
    Logger.recordOutput("Motor/BottomRight/Licensed", shooterMainMotorRight.getIsProLicensed().getValue());
  }

  /**
   * Configures the left and right motors of the "main" flywheel these are the
   * forward bottom most motors.
   */
  public void configureShooterFlywheel(Slot0Configs slot0) {
    // Top motor configurations
    TalonFXConfiguration shooterLeftConfig = new TalonFXConfiguration();
    shooterMainMotorLeft.getConfigurator().apply(shooterLeftConfig); // reset to default
    shooterLeftConfig.MotorOutput.Inverted = ShooterConstants.shooterMainMotorLeftInvert;
    shooterLeftConfig.MotorOutput.NeutralMode = ShooterConstants.shooterMainMotorLeftBrakeMode;
    shooterLeftConfig.Slot0.kP = slot0.kP;
    shooterLeftConfig.Slot0.kI = slot0.kI;
    shooterLeftConfig.Slot0.kD = slot0.kD;
    shooterLeftConfig.Slot0.kV = slot0.kV;
    shooterLeftConfig.Slot0.kS = slot0.kS;
    shooterLeftConfig.CurrentLimits.StatorCurrentLimitEnable = true;
    shooterLeftConfig.CurrentLimits.StatorCurrentLimit = ShooterConstants.bottomLeftCurrentLimit;
    shooterMainMotorLeft.getConfigurator().apply(shooterLeftConfig);
    // Bottom motor configurations
    TalonFXConfiguration shooterRightConfig = new TalonFXConfiguration();
    shooterMainMotorRight.getConfigurator().apply(shooterRightConfig); // reset to default
    shooterRightConfig.MotorOutput.Inverted = ShooterConstants.shooterMainMotorRightInvert;
    shooterRightConfig.MotorOutput.NeutralMode = ShooterConstants.shooterMainMotorRightBrakeMode;
    shooterRightConfig.Slot0.kP = slot0.kP;
    shooterRightConfig.Slot0.kI = slot0.kI;
    shooterRightConfig.Slot0.kD = slot0.kD;
    shooterRightConfig.Slot0.kV = slot0.kV;
    shooterRightConfig.Slot0.kS = slot0.kS;
    shooterRightConfig.CurrentLimits.StatorCurrentLimitEnable = true;
    shooterRightConfig.CurrentLimits.StatorCurrentLimit = ShooterConstants.bottomRightCurrentLimit;
    shooterMainMotorRight.getConfigurator().apply(shooterRightConfig);
  }

  public void configureIntakeWheel(Slot0Configs slot0) {
    // Bottom motor configurations
    TalonFXConfiguration shooterIntakeWheelMotorConfig = new TalonFXConfiguration();
    shooterIntakeWheelMotor.getConfigurator().apply(shooterIntakeWheelMotorConfig); // reset to default
    shooterIntakeWheelMotorConfig.MotorOutput.Inverted = ShooterConstants.topBottomMotorInvert;
    shooterIntakeWheelMotorConfig.MotorOutput.NeutralMode = ShooterConstants.topBottomMotorBrakeMode;
    shooterIntakeWheelMotorConfig.Slot0.kP = slot0.kP;
    shooterIntakeWheelMotorConfig.Slot0.kV = slot0.kV;
    shooterIntakeWheelMotorConfig.Slot0.kS = slot0.kS;
    shooterIntakeWheelMotorConfig.CurrentLimits.StatorCurrentLimitEnable = true;
    shooterIntakeWheelMotorConfig.CurrentLimits.StatorCurrentLimit = ShooterConstants.topBottomCurrentLimit;
    shooterIntakeWheelMotor.getConfigurator().apply(shooterIntakeWheelMotorConfig);
  }

  public void configurebackspinWheelMotor(Slot0Configs slot0) {
    // Top motor configurations
    TalonFXConfiguration shooterBackspinWheelConfig = new TalonFXConfiguration();
    backspinWheelMotor.getConfigurator().apply(shooterBackspinWheelConfig); // reset to default
    shooterBackspinWheelConfig.MotorOutput.Inverted = ShooterConstants.topTopMotorInvert;
    shooterBackspinWheelConfig.Slot0.kP = slot0.kP;
    shooterBackspinWheelConfig.Slot0.kV = slot0.kV;
    shooterBackspinWheelConfig.Slot0.kS = slot0.kS;
    shooterBackspinWheelConfig.CurrentLimits.StatorCurrentLimitEnable = true;
    shooterBackspinWheelConfig.CurrentLimits.StatorCurrentLimit = ShooterConstants.topTopCurrentLimit;
    backspinWheelMotor.getConfigurator().apply(shooterBackspinWheelConfig);

  }

  public void updateInputs(ShooterIOInputs inputs) {
    BaseStatusSignal.refreshAll(
        velocityOfMainFlywhelLeftRPS, velocityOfMainFlywheeRightRPS);

    inputs.velocityOfMainFlywhelLeftRPS = velocityOfMainFlywhelLeftRPS.getValue().in(Rotation.per(Seconds));

    inputs.velocityOfMainFlywhelRightRPS = velocityOfMainFlywheeRightRPS.getValue().in(Rotation.per(Seconds));

    inputs.velocityOfbackspinWheelMotorRPS = velocityOfbackspinWheelMotorRPS.getValueAsDouble();

    inputs.velocityOfIntakeWheelRPS = velocityOfIntakeWheelRPS.getValue().in(Rotation.per(Seconds));

    inputs.backspinConnected = backspinWheelMotor.isConnected();
    inputs.intakeConnected = shooterIntakeWheelMotor.isConnected();
    inputs.mainFlywhelLeftConnected = shooterMainMotorLeft.isConnected();
    inputs.mainFlywhelRightConnected = shooterMainMotorRight.isConnected();

    inputs.mainFlywheelLeftStatorCurrent = 0.0;
    inputs.mainFlywheelRightStatorCurrent = 0.0;
    inputs.mainBackspinStatorCurrent = 0.0;
    inputs.mainIntakeStatorCurrent = 0.0;
  }

  public void setOutput(double shooterOutput, double backspinOutput, double angleOutput) {
    shooterMainMotorLeft.set(shooterOutput);
    shooterMainMotorRight.set(shooterOutput);

    backspinWheelMotor.set(backspinOutput);
    shooterIntakeWheelMotor.set(angleOutput);
  }

  public void setVelocity(ShooterState desiredState) {
    setVelocity(desiredState.getFlywheelSpeed(), desiredState.getIntakeSpeed(), desiredState.getBackspinSpeed());
  }

  public void setVelocity(double shooterFlywheelSpeed, double shooterIntakeSpeed, double shooterBackspinSpeed) {
    setMainWheelSpeed(shooterFlywheelSpeed);
    setIntakeSpeed(shooterIntakeSpeed);
    setBackspinSpeed(shooterBackspinSpeed);
  }

  public void setMainWheelSpeed(double shooterFlywheelSpeed) {
    shooterMainMotorLeft.setControl(velShooterRequest.withVelocity(shooterFlywheelSpeed));
    shooterMainMotorRight.setControl(velShooterRequest.withVelocity(shooterFlywheelSpeed));
  }

  public void setBackspinSpeed(double shooterBackspinSpeed) {
    backspinWheelMotor.setControl(velBackspinRequest.withVelocity(shooterBackspinSpeed));
  }

  public void setIntakeSpeed(double shooterIntakeSpeed) {
    shooterIntakeWheelMotor.setControl(velIntakeRequest.withVelocity(shooterIntakeSpeed));
  }

  public void holdPosition() {
  }

  public void stopMainWheel() {
    shooterMainMotorLeft.stopMotor();
    shooterMainMotorRight.stopMotor();
  }

  public void stopBackspinWheel() {
    backspinWheelMotor.stopMotor();

  }

  public void stopIntakeWheel() {
    shooterIntakeWheelMotor.stopMotor();
  }
}