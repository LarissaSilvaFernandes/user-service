package lavin.user_service.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class UserRequestUpdate {

    private String username;
    @Email(message = "Email should be valid")
    private String email;
    @Pattern(regexp = "^[A-Za-z!@<3#$%^&*()_.?/]{5}$", message = "Password must be exactly 5 characters long and include uppercase, lowercase, and special characters: !@#$%^&*()_.?/")
    private String password;

}
