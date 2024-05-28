package sep.model.validators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameValidatorTest {
    @Test
    public void inputtingNullIsNotValid()
    {
        assertThrows(IllegalArgumentException.class, () -> {
            NameValidator.validate(null);
        });
    }

    @Test
    public void inputtingEmptyStringIsNotValid()
    {
        assertThrows(IllegalArgumentException.class, () -> {
            NameValidator.validate("");
        });
    }

    @Test
    public void inputtingNumberIsNotValid()
    {
        assertThrows(IllegalArgumentException.class, () -> {
            NameValidator.validate("123Test");
        });
    }

    @Test
    public void inputtingSpecialCharactersIsNotValid()
    {
        assertThrows(IllegalArgumentException.class, () -> {
            NameValidator.validate("@#!#$Test");
        });
    }

    @Test
    public void inputtingWhiteSpaceIsNotValid()
    {
        assertThrows(IllegalArgumentException.class, () -> {
            NameValidator.validate("Test Test");
        });
    }

    @Test
    public void inputtingDashIsNotValid()
    {
        assertThrows(IllegalArgumentException.class, () -> {
            NameValidator.validate("Test-Test");
        });
    }

    @Test
    public void inputtingDotIsNotValid()
    {
        assertThrows(IllegalArgumentException.class, () -> {
            NameValidator.validate("Test.Test");
        });
    }

    @Test
    public void inputtingNameWithMixedInvalidCharactersThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> NameValidator.validate("Jane_Doe1!"));
    }

    @Test
    public void singleValidCharacterNameDoesNotThrowException() {
        assertDoesNotThrow(() -> {
            NameValidator.validate("J");
        });
    }

    @Test
    public void singleInvalidCharacterNameThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> NameValidator.validate("1"));
        assertEquals("Firstname must contain only letters!", exception.getMessage());
    }
}