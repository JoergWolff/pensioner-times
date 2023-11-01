package de.wolffclan.backend.controllers.user;

import de.wolffclan.backend.models.user.NewUser;
import de.wolffclan.backend.models.user.User;
import de.wolffclan.backend.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        try {
            List<User> getAllUsers = userService.getAllUsers();
            return ResponseEntity.ok(getAllUsers);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<Object> postUser(@RequestBody NewUser newUser) {

        try {
            User saveUser = userService.saveUser(newUser);
            return ResponseEntity.ok(saveUser);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getUserById(@PathVariable String id){
        try {
           User searchUser = userService.getUserById(id);
            return ResponseEntity.ok(searchUser);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> putUser(@PathVariable String id, @RequestBody User user) {
        try {
            User savedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(savedUser);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<Object> deleteUser(@PathVariable String id){
        try{
            User deletedUser = userService.deleteUser(id);
            return ResponseEntity.ok(deletedUser);
        }catch (NoSuchElementException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());

        }
    }
}
