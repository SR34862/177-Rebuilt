package frc.robot.subsystems.Shooter;

import org.bobcatrobotics.Util.Tunables.TunableDouble;
import com.ctre.phoenix6.configs.Slot0Configs;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter.Modules.ShooterModuleDual;;

public class ShooterReal implements ShooterIO {

  private ShooterModuleDual dualMotorShooter;

  // Defines tunable values , particularly for configurations of motors ( IE PIDs
  // )
  private TunableDouble shooterMainMotorsPIDkP;
  private TunableDouble shooterMainMotorsPIDkI;
  private TunableDouble shooterMainMotorsPIDkD;
  private TunableDouble shooterMainMotorsPIDkV;
  private TunableDouble shooterMainMotorsPIDkS;
  private TunableDouble shooterMainMotorsPIDkA;
  private TunableDouble shooterIntakeMotorsPIDkP;
  private TunableDouble shooterIntakeMotorsPIDkI;
  private TunableDouble shooterIntakeMotorsPIDkD;
  private TunableDouble shooterIntakeMotorsPIDkV;
  private TunableDouble shooterIntakeMotorsPIDkS;
  private TunableDouble shooterIntakeMotorsPIDkA;
  private TunableDouble shooterBackspinMotorsPIDkP;
  private TunableDouble shooterBackspinMotorsPIDkI;
  private TunableDouble shooterBackspinMotorsPIDkD;
  private TunableDouble shooterBackspinMotorsPIDkV;
  private TunableDouble shooterBackspinMotorsPIDkS;
  private TunableDouble shooterBackspinMotorsPIDkA;

  public ShooterReal() {
    shooterMainMotorsPIDkP = new TunableDouble("/Shooter/MainMotor/PID/kP", Constants.ShooterConstants.kshooterMainP);
    shooterMainMotorsPIDkI = new TunableDouble("/Shooter/MainMotor/PID/kI", Constants.ShooterConstants.kshooterMainI);
    shooterMainMotorsPIDkD = new TunableDouble("/Shooter/MainMotor/PID/kD", Constants.ShooterConstants.kshooterMainD);
    shooterMainMotorsPIDkV = new TunableDouble("/Shooter/MainMotor/PID/kV", Constants.ShooterConstants.kshooterMainV);
    shooterMainMotorsPIDkS = new TunableDouble("/Shooter/MainMotor/PID/kS", Constants.ShooterConstants.kshooterMainS);
    shooterMainMotorsPIDkA = new TunableDouble("/Shooter/MainMotor/PID/kA", Constants.ShooterConstants.kshooterMainA);

    shooterIntakeMotorsPIDkP = new TunableDouble("/Shooter/Intake/PID/kP", Constants.ShooterConstants.kTopBottomP);
    shooterIntakeMotorsPIDkV = new TunableDouble("/Shooter/Intake/PID/kV", Constants.ShooterConstants.kTopBottomV);
    shooterIntakeMotorsPIDkS = new TunableDouble("/Shooter/Intake/PID/kS", Constants.ShooterConstants.kTopBottomS);

    shooterBackspinMotorsPIDkP = new TunableDouble("/Shooter/BackSpin/PID/kP", Constants.ShooterConstants.kTopTopP);
    shooterBackspinMotorsPIDkV = new TunableDouble("/Shooter/BackSpin/PID/kV", Constants.ShooterConstants.kTopTopV);
    shooterBackspinMotorsPIDkS = new TunableDouble("/Shooter/BackSpin/PID/kS", Constants.ShooterConstants.kTopTopS);
    
    Slot0Configs flyweelConfig = new Slot0Configs();
    flyweelConfig.kP = shooterMainMotorsPIDkP.get();
    flyweelConfig.kI = shooterMainMotorsPIDkI.get();
    flyweelConfig.kD = shooterMainMotorsPIDkD.get();
    flyweelConfig.kV = shooterMainMotorsPIDkV.get();
    flyweelConfig.kS = shooterMainMotorsPIDkS.get();
    flyweelConfig.kA = shooterMainMotorsPIDkA.get();
    Slot0Configs intakeConfig = new Slot0Configs();
    intakeConfig.kP = shooterIntakeMotorsPIDkP.get();
    intakeConfig.kV = shooterIntakeMotorsPIDkV.get();
    intakeConfig.kS = shooterIntakeMotorsPIDkS.get();
    Slot0Configs backspinConfig = new Slot0Configs();
    backspinConfig.kP = shooterBackspinMotorsPIDkP.get();
    backspinConfig.kV = shooterBackspinMotorsPIDkV.get();
    backspinConfig.kS = shooterBackspinMotorsPIDkS.get();
    dualMotorShooter = new ShooterModuleDual(flyweelConfig, intakeConfig, backspinConfig);
  }

  /**
   * Configures the left and right motors of the "main" flywheel these are the
   * forward bottom most motors.
   */
  public void configureShooterFlywheel() {
    Slot0Configs slot0 = new Slot0Configs();
    slot0.kP = shooterMainMotorsPIDkP.get();
    slot0.kI = shooterMainMotorsPIDkI.get();
    slot0.kD = shooterMainMotorsPIDkD.get();
    slot0.kV = shooterMainMotorsPIDkV.get();
    slot0.kS = shooterMainMotorsPIDkS.get();
    dualMotorShooter.configureShooterFlywheel(slot0);
  }

  public void configureIntakeWheel() {
    Slot0Configs slot0 = new Slot0Configs();
    slot0.kP = shooterIntakeMotorsPIDkP.get();
    slot0.kV = shooterIntakeMotorsPIDkV.get();
    slot0.kS = shooterIntakeMotorsPIDkS.get();
    dualMotorShooter.configureIntakeWheel(slot0);
  }

  public void configurebackspinWheelMotor() {
    Slot0Configs slot0 = new Slot0Configs();
    slot0.kP = shooterBackspinMotorsPIDkP.get();
    slot0.kV = shooterBackspinMotorsPIDkV.get();
    slot0.kS = shooterBackspinMotorsPIDkS.get();
    dualMotorShooter.configurebackspinWheelMotor(slot0);
  }

  @Override
  public void updateInputs(ShooterIOInputs inputs) {
    dualMotorShooter.updateInputs(inputs);
  }

  public void setOutput(double shooterOutput, double backspinOutput, double angleOutput) {
    dualMotorShooter.setOutput(shooterOutput, backspinOutput, angleOutput);
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
    dualMotorShooter.setMainWheelSpeed(shooterFlywheelSpeed);
  }

  public void setBackspinSpeed(double shooterBackspinSpeed) {
    dualMotorShooter.setBackspinSpeed(shooterBackspinSpeed);
  }

  public void setIntakeSpeed(double shooterIntakeSpeed) {
    dualMotorShooter.setIntakeSpeed(shooterIntakeSpeed);
  }


  @Override
  public void stop() {
    stopMainWheel();
    stopBackspinWheel();
    stopIntakeWheel();
  }

  public void stopMainWheel() {
    dualMotorShooter.stopMainWheel();
  }

  public void stopBackspinWheel() {
    dualMotorShooter.stopBackspinWheel();

  }

  public void stopIntakeWheel() {
    dualMotorShooter.stopIntakeWheel();
  }

  @Override
  public void periodic() {
    if (shooterMainMotorsPIDkP.hasChanged()
        || shooterMainMotorsPIDkI.hasChanged()
        || shooterMainMotorsPIDkD.hasChanged()
        || shooterMainMotorsPIDkS.hasChanged()
        || shooterMainMotorsPIDkV.hasChanged()
        || shooterMainMotorsPIDkA.hasChanged()) {
      configureShooterFlywheel();
    }
    if (shooterIntakeMotorsPIDkP.hasChanged()
        || shooterIntakeMotorsPIDkS.hasChanged()
        || shooterIntakeMotorsPIDkV.hasChanged()) {
      configureIntakeWheel();
    }
    if (shooterBackspinMotorsPIDkP.hasChanged()
        || shooterBackspinMotorsPIDkS.hasChanged()
        || shooterBackspinMotorsPIDkV.hasChanged()) {
      configurebackspinWheelMotor();
    }
  }
}