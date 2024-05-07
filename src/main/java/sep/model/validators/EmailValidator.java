package sep.model.validators;

public class EmailValidator {

    public static void validate(String email) throws IllegalArgumentException {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Please fill the email field!");
        }

        int atPosition = email.indexOf('@');
        int dotPosition = email.lastIndexOf('.');

        if (atPosition == -1 || dotPosition == -1 || dotPosition <= atPosition) {
            throw new IllegalArgumentException("Email format is invalid!");
        }

        if (atPosition == 0) {
            throw new IllegalArgumentException("Email format is invalid!");
        }


        if (dotPosition == atPosition + 1 || dotPosition == email.length() - 1) {
            throw new IllegalArgumentException("Email format is invalid!");
        }

    }

}
