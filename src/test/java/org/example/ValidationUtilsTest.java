package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidationUtilsTest {

    @Test
    public void testValidateTeamNameThrowsExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateTeamName(null));
    }

    @Test
    public void testValidateTeamNameThrowsExceptionWhenEmpty() {
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateTeamName(""));
    }

    @Test
    public void testValidateTeamNameThrowsExceptionWhenBlank() {
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateTeamName(" "));
    }

    @Test
    public void testValidateScoreThrowsExceptionWhenNegative() {
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateScore(-1));
    }
}
