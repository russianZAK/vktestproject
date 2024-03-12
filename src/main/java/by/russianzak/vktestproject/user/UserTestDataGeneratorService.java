package by.russianzak.vktestproject.user;

import by.russianzak.vktestproject.user.role.Role;
import by.russianzak.vktestproject.user.role.UserRole;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserTestDataGeneratorService {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;


  public void generateTestData() {
    addUser("admin@example.com", "admin", Role.ROLE_ADMIN);
    addUser("viewer@example.com", "viewer", Role.ROLE_POSTS_VIEWER, Role.ROLE_ALBUMS_VIEWER, Role.ROLE_USERS_VIEWER);
    addUser("editor@example.com", "editor", Role.ROLE_POSTS_EDITOR, Role.ROLE_ALBUMS_EDITOR, Role.ROLE_USERS_EDITOR);
    addUser("admin_user@example.com", "admin_user", Role.ROLE_ADMIN);
    addUser("viewer_editor@example.com", "viewer_editor", Role.ROLE_POSTS_VIEWER, Role.ROLE_POSTS_EDITOR);
    addUser("admin_viewer@example.com", "admin_viewer", Role.ROLE_ADMIN, Role.ROLE_POSTS_VIEWER);
    addUser("admin_editor@example.com", "admin_editor", Role.ROLE_ADMIN, Role.ROLE_POSTS_EDITOR);
  }

  private void addUser(String username, String password, Role... roles) {

    HashSet<UserRole> set = Arrays.stream(roles)
        .map(role -> UserRole.builder().role(role).build())
        .collect(Collectors.toCollection(HashSet::new));

    User user = User.builder()
        .username(username)
        .password(passwordEncoder.encode(password))
        .accountNonExpired(true)
        .accountNonLocked(true)
        .credentialsNonExpired(true)
        .enabled(true)
        .roles(set)
        .build();

    userService.save(user);
  }
}
