package sep.model.validators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneValidatorTest {

    @Test
    public void validPhoneNumberDoesNotThrowException() {
        assertDoesNotThrow(() -> {
            PhoneValidator.validate("12345678");
        });
    }

    @Test
    public void phoneNumberWithNonDigitsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            PhoneValidator.validate("1234567A");
        });
    }

    @Test
    public void phoneNumberTooShortThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            PhoneValidator.validate("1234567");
        });
    }

    @Test
    public void phoneNumberTooLongThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            PhoneValidator.validate("123456789");
        });
    }

    @Test
    public void nullPhoneNumberThrowsException() {
        assertThrows(NullPointerException.class, () -> {
            PhoneValidator.validate(null);
        });
    }
}
