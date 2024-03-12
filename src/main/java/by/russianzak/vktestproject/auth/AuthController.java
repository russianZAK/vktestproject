package by.russianzak.vktestproject.auth;

import by.russianzak.vktestproject.user.User;
import by.russianzak.vktestproject.user.dto.UserDTO;
import by.russianzak.vktestproject.user.mapper.UserDTOUserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Api(tags = "Authentication Controller")
public class AuthController {

  private final PasswordEncoder bCryptPasswordEncoder;
  private final AuthService authService;

  @PostMapping("/register")
  @ApiOperation(value = "User registration", notes = "Register a new user")
  public ResponseEntity<User> register(@RequestBody @Validated UserDTO userDTO){
    User user = UserDTOUserMapper.INSTANCE.userDTOToUser(userDTO, bCryptPasswordEncoder);
    return new ResponseEntity<>(authService.saveNewUser(user), HttpStatus.CREATED);
  }
}
