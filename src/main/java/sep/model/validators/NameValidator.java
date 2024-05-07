package sep.model.validators;

public class NameValidator {
    public static void validate(String firstname){
        if (firstname == null || firstname.isEmpty()) {
            throw new IllegalArgumentException("Firstname must not be empty!");
        }
        for (char c : firstname.toCharArray()) {

            if (!Character.isLetter(c)) {
                throw new IllegalArgumentException("Firstname must contain only letters!");
            }
        }
    }
}
