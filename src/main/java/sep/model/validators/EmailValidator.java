package sep.model.validators;

public class EmailValidator {

    public static boolean validate(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        int atPosition = email.indexOf('@');
        int dotPosition = email.lastIndexOf('.');

        if (atPosition == -1 || dotPosition == -1 || dotPosition <= atPosition) {
            return false;
        }

        if (atPosition == 0) {
            return false;
        }


        if (dotPosition == atPosition + 1 || dotPosition == email.length() - 1) {
            return false;
        }

        return true;
    }

}
