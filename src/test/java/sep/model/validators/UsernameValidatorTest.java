package sep.model.validators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsernameValidatorTest {

    @Test
    public void validUsernameDoesNotThrowException() {
        assertDoesNotThrow(() -> {
            UsernameValidator.validate("user123");
        });
    }

    @Test
    public void nullUsernameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            UsernameValidator.validate(null);
        });
    }

    @Test
    public void usernameTooShortThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            UsernameValidator.validate("us");
        });
    }

    @Test
    public void usernameTooLongThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            UsernameValidator.validate("thisusernameistoolonganditshouldthrowanexception");
        });
    }
}
