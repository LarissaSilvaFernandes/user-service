package lavin.user_service.business;


import lavin.user_service.dtos.request.UserRequest;
import lavin.user_service.dtos.request.UserRequestUpdate;
import lavin.user_service.dtos.response.UserResponse;
import lavin.user_service.model.Role;
import lavin.user_service.model.User;
import lavin.user_service.repository.UserRepository;
import lavin.user_service.service.PasswordService;
import lavin.user_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;




@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final String ID = "313476877";
    private static final String USERNAME = "LARI";
    private static final String PASSWORD = "LeV<3";
    private static final String EMAIL = "lari@gmail.com";
    private static final Role ROLE = Role.ADMIN;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordService passwordService;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void testCreateUser() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(USERNAME);
        userRequest.setPassword(PASSWORD);
        userRequest.setEmail(EMAIL);
        userRequest.setRole(ROLE);

        User user = new User();
        user.setId(ID);
        user.setUsername(USERNAME);
        user.setPassword("hashedPassword");
        user.setEmail(EMAIL);
        user.setRole(ROLE);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(ID);
        userResponse.setUsername(USERNAME);
        userResponse.setEmail(EMAIL);
        userResponse.setRole(ROLE.toString());


        when(passwordService.encodePassword(PASSWORD)).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(userRequest, User.class)).thenReturn(user);
        when(modelMapper.map(user, UserResponse.class)).thenReturn(userResponse);

        ResponseEntity<UserResponse> createdUserResponse = userService.createUser(userRequest);

        assertEquals(userResponse.getId(), createdUserResponse.getBody().getId());
        assertEquals(userResponse.getUsername(), createdUserResponse.getBody().getUsername());
        assertEquals(userResponse.getEmail(), createdUserResponse.getBody().getEmail());
        assertEquals(userResponse.getRole(), createdUserResponse.getBody().getRole());

    }

    @Test
    void testUpdateUser() {
        UserRequestUpdate userRequestUpdate = new UserRequestUpdate();
        userRequestUpdate.setUsername(USERNAME);
        userRequestUpdate.setPassword(PASSWORD);
        userRequestUpdate.setEmail(EMAIL);

        User user = new User();
        user.setId(ID);
        user.setUsername("mariazinha");
        user.setEmail("mariazinhadejesus@gmail.com");
        user.setPassword("2vEl#");
        user.setRole(ROLE);

        User updatedUser = new User();
        updatedUser.setId(ID);
        updatedUser.setUsername(USERNAME);
        updatedUser.setPassword(PASSWORD);
        updatedUser.setEmail(EMAIL);
        updatedUser.setRole(ROLE);


        UserResponse userResponse = new UserResponse();
        userResponse.setId(ID);
        userResponse.setUsername(USERNAME);
        userResponse.setEmail(EMAIL);
        userResponse.setRole(ROLE.toString());


        when(userRepository.findById(ID)).thenReturn(Optional.of(user));
        when(passwordService.encodePassword(PASSWORD)).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        when(modelMapper.map(any(User.class), eq(UserResponse.class))).thenReturn(userResponse);


        ResponseEntity<UserResponse> UpdateResponseEntity = userService.updateUser(ID, userRequestUpdate);

        assertEquals(userResponse.getId(), UpdateResponseEntity.getBody().getId());
        assertEquals(userResponse.getUsername(), UpdateResponseEntity.getBody().getUsername());
        assertEquals(userResponse.getEmail(), UpdateResponseEntity.getBody().getEmail());
        assertEquals(userResponse.getRole(), UpdateResponseEntity.getBody().getRole());

    }
}
