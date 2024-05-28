package sep.model.validators;

/**
 * The NameValidator class provides a method to validate the name.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class NameValidator {
    /**
     * Validates a given name.
     *
     * @param firstname The name to be validated
     * @throws IllegalArgumentException If the name is null, empty, or contains non-letter characters
     */
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
