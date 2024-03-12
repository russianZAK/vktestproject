package by.russianzak.vktestproject;

import by.russianzak.vktestproject.user.User;
import by.russianzak.vktestproject.user.UserService;
import by.russianzak.vktestproject.user.role.Role;
import by.russianzak.vktestproject.user.role.UserRole;
import by.russianzak.vktestproject.user.role.UserRoleService;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private UserService userService;

  @Autowired
  private UserRoleService userRoleService;

  private UserRole rolePostsViewer;
  private UserRole roleUsersViewer;
  private UserRole roleAlbumsViewer;

  @BeforeEach
  public void setUpRoles() {
    rolePostsViewer = userRoleService.findUserRoleByRole(Role.ROLE_POSTS_VIEWER)
        .orElseThrow(() -> new IllegalStateException("Role ROLE_POSTS_VIEWER not found"));
    roleUsersViewer = userRoleService.findUserRoleByRole(Role.ROLE_USERS_VIEWER)
        .orElseThrow(() -> new IllegalStateException("Role ROLE_USERS_VIEWER not found"));
    roleAlbumsViewer = userRoleService.findUserRoleByRole(Role.ROLE_ALBUMS_VIEWER)
        .orElseThrow(() -> new IllegalStateException("Role ROLE_ALBUMS_VIEWER not found"));
  }

  @BeforeEach
  public void clearDatabase() {
    userService.deleteAll();
  }

  @Test
  public void testSave() {
    User user = new User();
    user.setUsername("testUser");
    user.setPassword("password");
    Set<UserRole> roles = new HashSet<>();
    roles.add(rolePostsViewer);
    user.setRoles(roles);

    User savedUser = userService.save(user);

    assertNotNull(savedUser.getId());
    assertEquals("testUser", savedUser.getUsername());
    assertEquals(1, savedUser.getRoles().size());
  }

  @Test
  public void testSaveNewUser_UsernameAlreadyExists() {
    User existingUser = new User();
    existingUser.setUsername("existingUser");
    existingUser.setPassword("password");
    addRolesToUser(existingUser);
    userService.save(existingUser);

    User newUser = new User();
    newUser.setUsername("existingUser");
    newUser.setPassword("password");
    addRolesToUser(newUser);

    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
        () -> userService.saveNewUser(newUser));
    assertEquals(HttpStatus.CONFLICT, exception.getStatus());
  }

  @Test
  public void testUpdate_UserNotFound() {
    User newUser = new User();
    newUser.setUsername("nonExistingUser");
    newUser.setPassword("password");
    addRolesToUser(newUser);

    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
        () -> userService.update(100L, newUser));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
  }

  @Test
  public void testDeleteById_UserNotFound() {
    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
        () -> userService.deleteById(100L));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
  }

  @Transactional
  @Test
  public void testGetById_UserNotFound() {
    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
        () -> userService.getById(100L));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
  }

  @Test
  public void testLoadUserByUsername_UserNotFound() {
    UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
        () -> userService.loadUserByUsername("nonExistingUser"));
    assertEquals("User with username 'nonExistingUser' not found", exception.getMessage());
  }

  @Test
  public void testLoadUserByUsername_UserFound() {
    User user = new User();
    user.setUsername("testUser");
    user.setPassword("password");
    addRolesToUser(user);
    userService.save(user);

    UserDetails userDetails = userService.loadUserByUsername("testUser");

    assertNotNull(userDetails);
    assertEquals("testUser", userDetails.getUsername());
  }

  @Test
  public void testGetByUsername_UserNotFound() {
    UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
        () -> userService.getByUsername("nonExistingUser"));
    assertEquals(UsernameNotFoundException.class, exception.getClass());
  }

  @Test
  public void testGetByUsername_UserFound() {
    User user = new User();
    user.setUsername("testUser");
    user.setPassword("password");
    addRolesToUser(user);
    userService.save(user);

    User foundUser = userService.getByUsername("testUser");

    assertNotNull(foundUser);
    assertEquals("testUser", foundUser.getUsername());
  }

  @Test
  public void testFindAll() {
    User user1 = new User();
    user1.setUsername("user1");
    user1.setPassword("password");
    addRolesToUser(user1);
    User user2 = new User();
    user2.setUsername("user2");
    user2.setPassword("password");
    addRolesToUser(user2);
    userService.save(user1);
    userService.save(user2);

    List<User> users = userService.findAll();

    assertNotNull(users);
    assertEquals(2, users.size());
  }

  @Test
  public void testDeleteById() {
    User user = new User();
    user.setUsername("testUser");
    user.setPassword("password");
    addRolesToUser(user);
    user = userService.save(user);

    User deletedUser = userService.deleteById(user.getId());

    assertNotNull(deletedUser);
    assertEquals("testUser", deletedUser.getUsername());

    User finalUser = user;
    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
        () -> userService.getById(finalUser.getId()));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
  }

  private void addRolesToUser(User user) {
    Set<UserRole> roles = new HashSet<>();
    roles.add(rolePostsViewer);
    roles.add(roleUsersViewer);
    roles.add(roleAlbumsViewer);
    user.setRoles(roles);
  }

}
