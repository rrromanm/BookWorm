package sep.model.validators;

public class UsernameValidator {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 40;

    public static boolean validate(String username) {
        if (username == null) {
            return false;
        }

        int len = username.length();
        if (len < MIN_LENGTH || len > MAX_LENGTH) {
            return false;
        }

        return true;
    }

}
