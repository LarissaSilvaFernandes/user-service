package lavin.user_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Document(collection = "users")
public class User {
    @Id
    private long id;

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Pattern(regexp = "^[A-Za-z!@#$%^&*()_.?/]{5}$", message = "Password must be exactly 5 characters long and include uppercase, lowercase, and special characters: !@#$%^&*()_.?/")
    private String password;
}
