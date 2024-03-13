package by.russianzak.vktestproject;

import by.russianzak.vktestproject.user.User;
import by.russianzak.vktestproject.user.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthServiceIntegrationTest {

  @Autowired
  private UserService userService;

  @BeforeEach
  public void setUp() {
    userService.deleteAll();
  }

  @AfterEach
  public void tearDown() {
    userService.deleteAll();
  }


  @Test
  public void testSaveNewUser_InvalidData() {
    User user = new User();
    user.setUsername("");
    user.setPassword("");
    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.saveNewUser(user));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
  }

  @Test
  public void testSaveNewUser_ValidData() {
    User user = new User();
    user.setUsername("testUser");
    user.setPassword("testPassword");
    user.setEnabled(true);
    user.setAccountNonExpired(true);
    user.setAccountNonLocked(true);
    user.setCredentialsNonExpired(true);
    User savedUser = userService.saveNewUser(user);
    assertEquals(user.getUsername(), savedUser.getUsername());
  }
}
