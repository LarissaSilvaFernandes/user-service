package lavin.user_service.controller;


import jakarta.validation.Valid;
import lavin.user_service.dtos.request.UserRequest;
import lavin.user_service.dtos.request.UserRequestUpdate;
import lavin.user_service.dtos.response.UserResponse;
import lavin.user_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponse>> getUser() {
        return userService.listUser();
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String id, @RequestBody @Valid UserRequestUpdate userRequestUpdate) {
        return userService.updateUser(id, userRequestUpdate);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }
}
