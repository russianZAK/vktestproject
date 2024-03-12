package by.russianzak.vktestproject.user.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

  @NotNull
  @NotEmpty
  private String username;

  @NotNull
  @NotEmpty
  private String password;
}