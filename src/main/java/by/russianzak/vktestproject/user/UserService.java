package by.russianzak.vktestproject.user;


import by.russianzak.vktestproject.user.role.Role;
import by.russianzak.vktestproject.user.role.UserRole;
import by.russianzak.vktestproject.user.role.UserRoleService;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  public final UserRepository repository;
  public final UserRoleService userRoleService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = repository.findByUsername(username);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException(
          String.format("User with username '%s' not found", username));
    }
    return user.get();
  }

  public User getByUsername(String username) {
    Optional<User> user = repository.findByUsername(username);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException(
          String.format("User with username '%s' not found", username));
    }
    return user.get();
  }

  public User save(User entity) {
    Set<UserRole> savedRoles = entity.getRoles().stream()
        .map(userRoleService::save)
        .collect(Collectors.toSet());
    entity.setRoles(savedRoles);
    return repository.save(entity);
  }

  public User saveNewUser(User entity) {

    if (entity.getUsername().isEmpty() || entity.getPassword().isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    if (repository.existsByUsername(entity.getUsername())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "User with username '" + entity.getUsername() + "' already exists");
    }

    Set<UserRole> roles = new HashSet<>();

    roles.add(userRoleService.findUserRoleByRole(Role.ROLE_POSTS_VIEWER)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role ROLE_POSTS_VIEWER not found")));

    roles.add(userRoleService.findUserRoleByRole(Role.ROLE_USERS_VIEWER)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role ROLE_USERS_VIEWER not found")));

    roles.add(userRoleService.findUserRoleByRole(Role.ROLE_ALBUMS_VIEWER)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role ROLE_ALBUMS_VIEWER not found")));


    entity.setRoles(roles);

    repository.save(entity);

    return entity;
  }

  public User update(Long id, User entity) {
    Optional<User> optionalEntity = repository.findById(id);
    if (optionalEntity.isPresent()) {
      entity.setId(id);
      return repository.save(entity);
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with id " + id + " not found");
    }
  }

  public List<User> findAll() {
    return repository.findAll();
  }

  public User getById(Long id) {
    Optional<User> user = repository.findById(id);
    if (user.isPresent()) {
      return user.get();
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with id " + id + " not found");
    }
  }

  public void deleteAll() {
    repository.deleteAll();
  }

  public User deleteById(Long id) {
    User user = getById(id);
    repository.deleteById(id);
    return user;
  }
}
