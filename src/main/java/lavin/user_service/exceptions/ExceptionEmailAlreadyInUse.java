package lavin.user_service.exceptions;

public class ExceptionEmailAlreadyInUse extends RuntimeException {

   public ExceptionEmailAlreadyInUse() {
       super("Email already in use");
   }
}
