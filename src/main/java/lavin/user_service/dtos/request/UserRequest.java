package lavin.user_service.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lavin.user_service.model.Role;
import lombok.Data;


@Data
public class UserRequest {

    @NotEmpty(message = "Username cannot be empty")
    private String username;
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    @Pattern(regexp = "^[A-Za-z!@<3#$%^&*()_.?/]{5}$", message = "Password must be exactly 5 characters long and include uppercase, lowercase, and special characters: !@#$%^&*()_.?/")
    private String password;
    private Role role = Role.USER;

}
