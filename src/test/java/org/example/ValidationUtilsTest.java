package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidationUtilsTest {

    @Test
    public void testValidateTeamNameThrowsExceptionWhenNull() {
        String teamName = null;

        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateTeamName(teamName), "Validating a null team name should throw an exception");
    }

    @Test
    public void testValidateTeamNameThrowsExceptionWhenEmpty() {
        String teamName = "";

        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateTeamName(teamName), "Validating an empty team name should throw an exception");
    }

    @Test
    public void testValidateTeamNameThrowsExceptionWhenBlank() {
        String teamName = " ";

        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateTeamName(teamName), "Validating a blank team name should throw an exception");
    }

    @Test
    public void testValidateScoreThrowsExceptionWhenNegative() {
        int score = -1;

        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateScore(score), "Validating a negative score should throw an exception");
    }
}
