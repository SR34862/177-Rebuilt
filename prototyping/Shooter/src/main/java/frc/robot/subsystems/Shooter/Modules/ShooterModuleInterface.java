package frc.robot.subsystems.Shooter.Modules;

import com.ctre.phoenix6.configs.Slot0Configs;

import frc.robot.subsystems.Shooter.ShooterState;

public interface ShooterModuleInterface {
    public default void configureShooterFlywheel(Slot0Configs slot0)  {
    }

    public default void configureIntakeWheel(Slot0Configs slot0) {
    }

    public default void configurebackspinWheelMotor(Slot0Configs slot0)  {

    }

    public default void setOutput(double shooterOutput, double backspinOutput, double angleOutput) {
    }

    public default void setVelocity(ShooterState desiredState) {
        setVelocity(desiredState.getFlywheelSpeed(), desiredState.getIntakeSpeed(), desiredState.getBackspinSpeed());
    }

    public default void setVelocity(double shooterFlywheelSpeed, double shooterIntakeSpeed,
            double shooterBackspinSpeed) {

    }

    public default void setMainWheelSpeed(double shooterFlywheelSpeed) {
    }

    public default void setBackspinSpeed(double shooterBackspinSpeed) {
    }

    public default void setIntakeSpeed(double shooterIntakeSpeed) {
    }

    public default void stopMainWheel() {
    }

    public default void stopBackspinWheel() {

    }

    public default void stopIntakeWheel() {
    }

}
