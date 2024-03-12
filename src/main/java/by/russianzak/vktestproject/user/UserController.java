package by.russianzak.vktestproject.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
@Api(tags = "User Controller")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping()
  @ApiOperation(value = "Get all users", notes = "Retrieve a list of all users")
  public ResponseEntity<List<User>> findAllUsers() {
    return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  @ApiOperation(value = "Get user by ID", notes = "Retrieve a user by its ID")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
  }

  @PostMapping("")
  @ApiOperation(value = "Create user", notes = "Create a new user")
  public ResponseEntity<User> createUser(@RequestBody User user) {
    return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  @ApiOperation(value = "Update user", notes = "Update an existing user")
  public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
    return new ResponseEntity<>(userService.update(id, user), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  @ApiOperation(value = "Delete user", notes = "Delete an existing user")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
