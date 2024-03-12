package by.russianzak.vktestproject.user.role;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "roles", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id", nullable = false)
  private Long id;

  @Basic
  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false, length = 255)
  private Role role;

}