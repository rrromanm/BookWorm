package sep.model.validators;
import sep.model.Patron;

//TODO: Check if username is taken
public class UsernameValidator {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 40;

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
