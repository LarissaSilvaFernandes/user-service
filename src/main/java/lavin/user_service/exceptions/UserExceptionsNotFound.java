package lavin.user_service.exceptions;

public class UserExceptionsNotFound extends RuntimeException {
    public UserExceptionsNotFound(String id) {
        super("User " + id + " not found");
    }
}
