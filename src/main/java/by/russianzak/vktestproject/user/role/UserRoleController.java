package by.russianzak.vktestproject.user.role;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/roles")
@Api(tags = "User Role Controller")
@RequiredArgsConstructor
public class UserRoleController {

  private final UserRoleService userRoleService;

  @GetMapping()
  @ApiOperation(value = "Get all user roles", notes = "Retrieve a list of all user roles")
  public ResponseEntity<List<UserRole>> findAllUsersRoles() {
    return new ResponseEntity<>(userRoleService.findAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  @ApiOperation(value = "Get user role by ID", notes = "Retrieve a user role by its ID")
  public ResponseEntity<UserRole> getUserRoleById(@PathVariable Long id) {
    return new ResponseEntity<>(userRoleService.getById(id), HttpStatus.OK);
  }

  @PostMapping("")
  @ApiOperation(value = "Create user role", notes = "Create a new user role")
  public ResponseEntity<UserRole> createUserRole(@RequestBody Role role) {
    return new ResponseEntity<>(userRoleService.save(role), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  @ApiOperation(value = "Update user role", notes = "Update an existing user role")
  public ResponseEntity<UserRole> updateUserRole(@PathVariable Long id, @RequestBody UserRole userRole) {
    return new ResponseEntity<>(userRoleService.update(id, userRole), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  @ApiOperation(value = "Delete user role", notes = "Delete an existing user role")
  public ResponseEntity<Void> deleteUserRole(@PathVariable Long id) {
    userRoleService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
