
// Copyright 2021-2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot;

// import frc.robot.subsystems.roller.RollerSubsystem;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Shooter.ShooterIO;
import frc.robot.subsystems.Shooter.ShooterReal;
import frc.robot.subsystems.Shooter.ShooterState;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Subsystems
  private final Shooter m_shooter;

  // Controller
  private final CommandXboxController controller = new CommandXboxController(0);

  // Dashboard inputs
  private final LoggedDashboardChooser<Command> autoChooser;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    switch (Constants.currentMode) {
      case REAL:
        // Real robot, instantiate hardware IO implementations
        m_shooter = new Shooter(new ShooterReal());
        break;
      case SIM:
        // Sim robot, instantiate physics sim IO implementations
        m_shooter = new Shooter(new ShooterIO() {
        });
        break;

      default:
        // Replayed robot, disable IO implementations
        m_shooter = new Shooter(new ShooterIO() {
        });
        break;
    }

    // Set up auto routines
    autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());

    // Set up SysId routines

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    ShooterState left = new ShooterState();
    left.currentState = ShooterState.State.MANUAL;
    left.setManualPosition(90);
    ShooterState right = new ShooterState();
    right.currentState = ShooterState.State.MANUAL;
    right.setManualPosition(270);
    ShooterState up = new ShooterState();
    up.currentState = ShooterState.State.MANUAL;
    up.setManualPosition(0);
    ShooterState down = new ShooterState();
    down.currentState = ShooterState.State.MANUAL;
    down.setManualPosition(180);
    ShooterState upleft = new ShooterState();
    upleft.currentState = ShooterState.State.MANUAL;
    upleft.setManualPosition(45);
    ShooterState upright = new ShooterState();
    upright.currentState = ShooterState.State.MANUAL;
    upright.setManualPosition(315);
    ShooterState downleft = new ShooterState();
    downleft.currentState = ShooterState.State.MANUAL;
    downleft.setManualPosition(135);
    ShooterState downright = new ShooterState();
    downright.currentState = ShooterState.State.MANUAL;
    downright.setManualPosition(225);

    ShooterState full = new ShooterState();
    full.currentState = ShooterState.State.MANUAL;
    full.setManualSpeed(5);
    Command runFlywheel = new RunCommand(() -> m_shooter.setVelocity(full.currentState));
    Command holdCommand = new RunCommand(() -> m_shooter.holdPosition());
    controller.povLeft()
        .whileTrue(new RunCommand(() -> m_shooter.setPosition(left.currentState)).alongWith(runFlywheel))
        .onFalse(holdCommand);
    controller.povUpLeft()
        .whileTrue(new RunCommand(() -> m_shooter.setPosition(left.currentState)).alongWith(runFlywheel))
        .onFalse(holdCommand);
    controller.povUpRight()
        .whileTrue(new RunCommand(() -> m_shooter.setPosition(left.currentState)).alongWith(runFlywheel))
        .onFalse(holdCommand);
    controller.povRight()
        .whileTrue(new RunCommand(() -> m_shooter.setPosition(right.currentState)).alongWith(runFlywheel))
        .onFalse(holdCommand);
    controller.povUp()
        .whileTrue(new RunCommand(() -> m_shooter.setPosition(up.currentState)).alongWith(runFlywheel))
        .onFalse(holdCommand);
    controller.povDown()
        .whileTrue(new RunCommand(() -> m_shooter.setPosition(down.currentState)).alongWith(runFlywheel))
        .onFalse(holdCommand);
    controller.povDownLeft()
        .whileTrue(new RunCommand(() -> m_shooter.setPosition(down.currentState)).alongWith(runFlywheel))
        .onFalse(holdCommand);
    controller.povDownRight()
        .whileTrue(new RunCommand(() -> m_shooter.setPosition(down.currentState)).alongWith(runFlywheel))
        .onFalse(holdCommand);

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.get();
  }

  public void teleopPeriodic() {
  }
}