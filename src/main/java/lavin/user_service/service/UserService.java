package lavin.user_service.service;

import lavin.user_service.dtos.request.UserRequest;
import lavin.user_service.dtos.request.UserRequestUpdate;
import lavin.user_service.dtos.response.UserResponse;
import lavin.user_service.exceptions.ExceptionEmailAlreadyInUse;
import lavin.user_service.exceptions.UserExceptionsNotFound;
import lavin.user_service.model.User;
import lavin.user_service.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordService passwordService;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordService = passwordService;
    }

    public ResponseEntity<UserResponse> createUser(UserRequest userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new ExceptionEmailAlreadyInUse();
        }
        user.setPassword(passwordService.encodePassword(userRequest.getPassword()));
        User savedUser = userRepository.save(user);
        UserResponse savedUserResponse = modelMapper.map(savedUser, UserResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserResponse);
    }

    public ResponseEntity<List<UserResponse>> listUser() {
        List<User> userList = userRepository.findAll();
        List<UserResponse> userResponseList = userList.stream().map(user -> modelMapper.map(user, UserResponse.class)).toList();
        return ResponseEntity.status(HttpStatus.OK).body(userResponseList);
    }

    public ResponseEntity<UserResponse> updateUser(String id, UserRequestUpdate userRequestUpdate) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserExceptionsNotFound(id);
        }
        User user = userOptional.get();

        if (userRequestUpdate.getUsername() != null) {
            user.setUsername(userRequestUpdate.getUsername());
        }
        if (userRequestUpdate.getPassword() != null) {
            user.setPassword(passwordService.encodePassword(userRequestUpdate.getPassword()));
        }
        if (userRequestUpdate.getEmail() != null) {
            Optional<User> existingUserWithEmail = userRepository.findByEmail(userRequestUpdate.getEmail());
            if (existingUserWithEmail.isPresent()) {
                throw new ExceptionEmailAlreadyInUse();
            }
            user.setEmail(userRequestUpdate.getEmail());
        }
        User savedUser = userRepository.save(user);
        UserResponse updatedUserResponse = modelMapper.map(savedUser, UserResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUserResponse);
    }

    public ResponseEntity<UserResponse> deleteUser(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserExceptionsNotFound(id);
        }
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}