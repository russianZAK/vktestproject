package by.russianzak.vktestproject.user;


import by.russianzak.vktestproject.user.role.UserRole;
import java.util.Collection;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users", schema = "public")
public class User implements UserDetails {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id", nullable = false)
  private Long id;

  @Basic
  @Column(name = "username", nullable = false, length = 255, unique = true)
  private String username;

  @Basic
  @Column(name = "password", nullable = false, length = 255)
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id", unique = false)
  )
  private Set<UserRole> roles;

  @Column(name = "accountnonexpired", nullable = false)
  private boolean accountNonExpired;

  @Column(name = "accountnonlocked", nullable = false)
  private boolean accountNonLocked;

  @Column(name = "credentialsnonexpired", nullable = false)
  private boolean credentialsNonExpired;

  @Column(name = "enabled", nullable = false)
  private boolean enabled;

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles.stream().map(UserRole::getRole).toList();
  }

}
