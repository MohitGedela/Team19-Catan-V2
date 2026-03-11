package boundary;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DiceTest {

    // Boundary Testing: The minimum possible roll with two dice is 2, so the roll should never be below the lower boundary of 2.
    @Test
    void testRollIsAtLeastTwo() {
        Dice dice = new Dice();
        for (int i = 0; i < 100; i++) {
            int roll = dice.roll();
            assertTrue(roll >= 2, "The roll should always be at least 2 since the minimum value of each die is 1.");
        }
    }

    // Boundary Testing: The maximum possible roll with two dice is 12, so the roll should never exceed the upper boundary of 12.
    @Test
    void testRollIsAtMostTwelve() {
        Dice dice = new Dice();
        for (int i = 0; i < 100; i++) {
            int roll = dice.roll();
            assertTrue(roll <= 12, "The roll should never exceed 12 since the maximum value of each die is 6.");
        }
    }

    // Boundary Testing: Each call to roll should return a value within the valid range of 2 to 12, which confirms consistent dice behaviour across multiple rolls.
    @Test
    void testRollAlwaysWithinValidRange() {
        Dice dice = new Dice();
        for (int i = 0; i < 100; i++) {
            int roll = dice.roll();
            assertTrue(roll >= 2 && roll <= 12, "The codebase is written in a way such that every roll must fall within the valid range of 2 to 12.");
        }
    }
}