package sep.model.validators;

public class PhoneValidator {
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
