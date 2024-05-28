package sep.model.validators;

/**
 * The UsernameValidator class provides a method to validate usernames.
 * This class ensures that usernames meet certain length requirements.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class UsernameValidator {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 40;

    /**
     * Validates a given username.
     *
     * @param username The username to be validated
     * @throws IllegalArgumentException If the username is null or does not meet the specified length requirements
     */
    public static void validate(String username) throws IllegalArgumentException {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be empty!");
        }

        int len = username.length();
        if (len < MIN_LENGTH || len > MAX_LENGTH) {
            throw new IllegalArgumentException("Username should be between " + MIN_LENGTH +
                " and " + MAX_LENGTH + " characters!");
        }
    }
}
