package sep.model.validators;

/**
 * The PhoneValidator class provides a method to validate the phone.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class PhoneValidator {
  /**
   * Validates a given phone number.
   *
   * @param phone The phone number to be validated
   * @throws IllegalArgumentException If the phone number does not consist of exactly 8 digits or contains non-numeric characters
   */
   public static void validate(String phone){
       if(phone.length()!= 8){
           throw new IllegalArgumentException("Phone number must be 8 digits");
       }
       for(int i = 0; i < phone.length(); i++){
           if(!Character.isDigit(phone.charAt(i))){
               throw new IllegalArgumentException("Phone number must only contain digits");
           }
       }
   }
}
