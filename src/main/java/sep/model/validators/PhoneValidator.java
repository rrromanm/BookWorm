package sep.model.validators;

public class PhoneValidator {
   public static void validate(Long phone){
       String number = phone.toString();
       if(number.length()!= 8){
           throw new IllegalArgumentException("Phone number must be 8 digits");
       }
       for(int i = 0; i < number.length(); i++){
           if(!Character.isDigit(number.charAt(i))){
               throw new IllegalArgumentException("Phone number must only contain digits");
           }
       }
   }
}
