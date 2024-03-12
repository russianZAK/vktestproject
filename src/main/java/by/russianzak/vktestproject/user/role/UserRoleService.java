package by.russianzak.vktestproject.user.role;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserRoleService {

  public final UserRoleRepository repository;

  public UserRole save(Role role) {
    Optional<UserRole> existingUserRole = findUserRoleByRole(role);
    return existingUserRole.orElseGet(() -> repository.save(UserRole.builder().role(role).build()));
  }

  public List<UserRole> findAll() {
    return repository.findAll();
  }

  public UserRole getById(Long id) {
    Optional<UserRole> userRole = repository.findById(id);
    if (userRole.isPresent()) {
      return userRole.get();
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User role with id " + id + " not found");
    }
  }

  public UserRole update(Long id, UserRole entity) {
    Optional<UserRole> optionalEntity = repository.findById(id);
    if (optionalEntity.isPresent()) {
      entity.setId(id);
      return repository.save(entity);
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User role with id " + id + " not found");
    }
  }

  public UserRole save(UserRole userRole) {
    Optional<UserRole> existingUserRole = findUserRoleByRole(userRole.getRole());
    return existingUserRole.orElseGet(() -> repository.save(userRole));
  }

  public Optional<UserRole> findUserRoleByRole(Role role) {
    return repository.findUserRoleByRole(role);
  }

  public UserRole deleteById(Long id) {
    UserRole userRole = getById(id);
    repository.deleteById(id);
    return userRole;
  }
}
