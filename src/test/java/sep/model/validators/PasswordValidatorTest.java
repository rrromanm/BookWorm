package sep.model.validators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {

    @Test
    public void validPasswordDoesNotThrowException() {
        assertDoesNotThrow(() -> {
            PasswordValidator.validate("Valid123!");
        });
    }

    @Test
    public void passwordTooShortThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            PasswordValidator.validate("Test1!");
        });
    }

    @Test
    public void passwordTooLongThrowsException() {
        String longPassword = "A".repeat(41) + "1!";
        assertThrows(IllegalArgumentException.class, () -> {
            PasswordValidator.validate(longPassword);
        });
    }

    @Test
    public void passwordWithNoUppercaseThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            PasswordValidator.validate("lowercase1!");
        });
    }

    @Test
    public void passwordWithNoLowercaseThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            PasswordValidator.validate("UPPERCASE1!");
        });
    }

    @Test
    public void passwordWithNoDigitThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            PasswordValidator.validate("NoDigit!");
        });
    }

    @Test
    public void passwordWithNoSpecialCharacterThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            PasswordValidator.validate("NoSpecial1");
        });
    }

    @Test
    public void nullPasswordThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            PasswordValidator.validate(null);
        });
    }

    @Test
    public void passwordWithOnlyUppercaseThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            PasswordValidator.validate("ONLYUPPERCASE");
        });
    }

    @Test
    public void passwordWithOnlyLowercaseThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            PasswordValidator.validate("onlylowercase");
        });
    }

    @Test
    public void passwordWithOnlyDigitsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            PasswordValidator.validate("1234567");
        });
    }

    @Test
    public void passwordWithOnlySpecialCharactersThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            PasswordValidator.validate("!@#$%^&*");
        });
    }

    @Test
    public void passwordWithSpecialCharacterDoesNotThrowException() {
        assertDoesNotThrow(() -> {
            PasswordValidator.validate("Password123!");
        });
    }

    @Test
    public void testSpecialCharacters() {
        String specialCharacters = "!@#$%^&*()-_=+|,.<>?";

        for (char specialChar : specialCharacters.toCharArray()) {
            String password = "Password123" + specialChar;
            assertDoesNotThrow(() -> {
                PasswordValidator.validate(password);
            }, "Password with special character '" + specialChar + "' failed validation.");
        }
    }
}