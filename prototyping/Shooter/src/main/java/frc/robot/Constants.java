// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final Mode simMode = Mode.SIM;
    public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;

    public static enum Mode {
        /** Running on a real robot. */
        REAL,

        /** Running a physics simulator. */
        SIM,

        /** Replaying from a log file. */
        REPLAY
    }

    public final static class ShooterConstants {
        public static final double idleFlywheelSpeedRPS = 0;
        public static final double idleIntakeSpeedRPS = 0;
        public static final double idleBackspinSpeedRPS = 0;
        // Max Speed values are 52.5 , 20 & 30
        public static final double targetFlywheelSpeedRPS = 52.5;
        public static final double targetIntakeSpeedRPS = 0.2;
        public static final double targetBackspinSpeedRPS = 30;
        // 76.5, -21.5, 0.3 - min values - Right
        // kP left: 1.27, kD left: 0.0175
        // kP Right: 1.3, kD Right: 0.03, kV: 0.07
        // Right: bsSpeed: -6.0, fW speed: 66, int speed: 50

        public final static class Left {
            // ID Constants
            public static final int FlywheelIDLeft = 29;
            public static final int BackspinIDLeft = 2;
            public static final int intakeIDLeft = 30;

            // Motor Constants
            public static final InvertedValue shooterMainMotorRightInvert = InvertedValue.CounterClockwise_Positive;
            public static final NeutralModeValue shooterMainMotorRightBrakeMode = NeutralModeValue.Coast;
            public static final InvertedValue shooterMainMotorLeftInvert = InvertedValue.Clockwise_Positive;
            public static final NeutralModeValue shooterMainMotorLeftBrakeMode = NeutralModeValue.Coast;
            public static final double kshooterMainkP = 1.25;
            public static final double kshooterMainkI = 0;
            public static final double kshooterMainkD = 0.0165;
            public static final double kshooterMainkS = 0;
            public static final double kshooterMainkV = 0;
            public static final double kshooterMainkA = 0;
            public static final double bottomRightCurrentLimit = 40;
            public static final double bottomLeftCurrentLimit = 40;

            public static final InvertedValue topTopMotorInvert = InvertedValue.Clockwise_Positive;
            public static final NeutralModeValue topTopMotorBrakeMode = NeutralModeValue.Coast;
            public static final double kBackspinMotorkP = 1.25;
            public static final double kBackspinMotorkI = 0;
            public static final double kBackspinMotorkD = 0.0165;
            public static final double kBackspinMotorkS = 0;
            public static final double kBackspinMotorkV = 0;
            public static final double kBackspinMotorkA = 0;
            public static final double topTopCurrentLimit = 40;

            public static final InvertedValue topBottomMotorInvert = InvertedValue.Clockwise_Positive;
            public static final InvertedValue RightIntakeMotorInvert = InvertedValue.CounterClockwise_Positive;
            public static final NeutralModeValue topBottomMotorBrakeMode = NeutralModeValue.Coast;
            public static final double kIntakeMotorkP = 5;
            public static final double kIntakeMotorkI = 0;
            public static final double kIntakeMotorkD = 0;
            public static final double kIntakeMotorkS = 0;
            public static final double kIntakeMotorkV = 0;
            public static final double kIntakeMotorkA = 0;
            public static final double topBottomCurrentLimit = 80;
        }

        public final static class Right {
            // ID Constants
            public static final int FlywheelIDRight = 17;
            public static final int BackspinIDRight = 20;
            public static final int intakeIDRight = 28;

            // Motor Constants
            public static final InvertedValue shooterMainMotorRightInvert = InvertedValue.CounterClockwise_Positive;
            public static final NeutralModeValue shooterMainMotorRightBrakeMode = NeutralModeValue.Coast;
            public static final InvertedValue shooterMainMotorLeftInvert = InvertedValue.Clockwise_Positive;
            public static final NeutralModeValue shooterMainMotorLeftBrakeMode = NeutralModeValue.Coast;
            public static final double kshooterMainkP = 1.25;
            public static final double kshooterMainkI = 0;
            public static final double kshooterMainkD = 0.0165;
            public static final double kshooterMainkS = 0;
            public static final double kshooterMainkV = 0;
            public static final double kshooterMainkA = 0;
            public static final double bottomRightCurrentLimit = 40;
            public static final double bottomLeftCurrentLimit = 40;

            public static final InvertedValue topTopMotorInvert = InvertedValue.Clockwise_Positive;
            public static final NeutralModeValue topTopMotorBrakeMode = NeutralModeValue.Coast;
            public static final double kBackspinMotorkP = 1.25;
            public static final double kBackspinMotorkI = 0;
            public static final double kBackspinMotorkD = 0.0165;
            public static final double kBackspinMotorkS = 0;
            public static final double kBackspinMotorkV = 0;
            public static final double kBackspinMotorkA = 0;
            public static final double topTopCurrentLimit = 40;

            public static final InvertedValue topBottomMotorInvert = InvertedValue.Clockwise_Positive;
            public static final InvertedValue RightIntakeMotorInvert = InvertedValue.CounterClockwise_Positive;
            public static final NeutralModeValue topBottomMotorBrakeMode = NeutralModeValue.Coast;
            public static final double kIntakeMotorkP = 5;
            public static final double kIntakeMotorkI = 0;
            public static final double kIntakeMotorkD = 0;
            public static final double kIntakeMotorkS = 0;
            public static final double kIntakeMotorkV = 0;
            public static final double kIntakeMotorkA = 0;
            public static final double topBottomCurrentLimit = 80;
        }
    }
}
