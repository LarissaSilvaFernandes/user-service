package lavin.user_service.dtos.response;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserResponse {

    private String id;
    private String username;
    private String email;
    private String role;

}
