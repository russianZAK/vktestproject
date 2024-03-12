package by.russianzak.vktestproject.user.role;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserRoleTestDataGeneratorService {

  private final UserRoleService userRoleService;

  public void generateTestData() {
    addUserRole(Role.ROLE_ADMIN);
    addUserRole(Role.ROLE_POSTS_VIEWER);
    addUserRole(Role.ROLE_POSTS_EDITOR);
    addUserRole(Role.ROLE_USERS_VIEWER);
    addUserRole(Role.ROLE_USERS_EDITOR);
    addUserRole(Role.ROLE_ALBUMS_VIEWER);
    addUserRole(Role.ROLE_ALBUMS_EDITOR);
  }

  private void addUserRole(Role role) {
    UserRole userRole = UserRole.builder()
        .role(role)
        .build();
    userRoleService.save(userRole);
  }
}
