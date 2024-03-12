package by.russianzak.vktestproject.jsonplaceholder.user.data;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userdata")
@Api(tags = "User Data Controller")
@RequiredArgsConstructor
public class UserDataController {

  private final UserDataService userService;

  @GetMapping
  @ApiOperation(value = "Get all users", notes = "Retrieve a list of all users")
  public ResponseEntity<List<UserData>> getAllUsers() {
    return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  @ApiOperation(value = "Get user by ID", notes = "Retrieve a user by their ID")
  public ResponseEntity<UserData> getUserById(@PathVariable Long id) {
    return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
  }

  @PostMapping
  @ApiOperation(value = "Create new user", notes = "Create a new user")
  public ResponseEntity<UserData> createUser(@RequestBody UserData user) {
    return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  @ApiOperation(value = "Update user", notes = "Update an existing user")
  public ResponseEntity<UserData> updateUser(@PathVariable Long id, @RequestBody UserData user) {
    return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  @ApiOperation(value = "Delete user", notes = "Delete an existing user")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
