package sep.model.validators;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EmailValidatorTest {
    @Test
    public void emailIsValidTest() {
        assertDoesNotThrow(() -> {
            EmailValidator.validate("test@gmail.com");
        });
    };

    @Test
    public void inputtingEmailWithNoAtSignThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> EmailValidator.validate("test.example.com"));
    }

    @Test
    public void inputtingNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> EmailValidator.validate(null));
    }

    @Test
    public void inputtingEmptyStringThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> EmailValidator.validate(""));
    }

    @Test
    public void inputtingNothingAfterDotThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> EmailValidator.validate("test@gmail."));
    }

    @Test
    public void inputtingEmailWithNoDotThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> EmailValidator.validate("test@gmailcom"));
    }

    @Test
    public void inputtingEmailWithDotImmediatelyAfterAtThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> EmailValidator.validate("test@.gmailcom"));
    }

    @Test
    public void inputtingEmailWithDotImmediatelyBeforeAtThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> EmailValidator.validate("test.@gmailcom"));
    }

    @Test
    public void inputtingEmailWithAtSignAtStartThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> EmailValidator.validate("@gmail.com"));
    }

    @Test
    public void inputtingEmailWithNoCharactersBeforeAtThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> EmailValidator.validate("@example.com"));
    }
}