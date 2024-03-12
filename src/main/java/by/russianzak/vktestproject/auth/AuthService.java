package by.russianzak.vktestproject.auth;

import by.russianzak.vktestproject.user.User;
import by.russianzak.vktestproject.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

  private final UserService userService;

  public User saveNewUser(User user) {
    if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "invalid data");
    }
    return userService.saveNewUser(user);
  }
}
