package sep.model.validators;

/**
 * The PasswordValidator class provides a method to validate the password.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class PasswordValidator {

    private static final int MIN_LENGTH = 7;
    private static final int MAX_LENGTH = 40;
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+|,.<>?";

    /**
     * Validates a given password.
     *
     * @param password The password to be validated
     * @throws IllegalArgumentException If the password does not meet the specified criteria
     */
    public static void validate(String password) throws IllegalArgumentException {
        if (password == null || password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Password must be at least " + MIN_LENGTH + " characters!");
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (SPECIAL_CHARACTERS.indexOf(c) >= 0) {
                hasSpecial = true;
            }
        }

        if(!hasUpper && !hasLower && !hasDigit && !hasSpecial){
            throw new IllegalArgumentException("Password must have at least one uppercase character, " +
                    "one digit and one special character!");
        };
    }

}
