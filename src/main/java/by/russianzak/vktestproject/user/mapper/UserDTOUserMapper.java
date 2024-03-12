package by.russianzak.vktestproject.user.mapper;

import by.russianzak.vktestproject.user.User;
import by.russianzak.vktestproject.user.dto.UserDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper
public interface UserDTOUserMapper {

  UserDTOUserMapper INSTANCE = Mappers.getMapper(UserDTOUserMapper.class);

  @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
  @Mapping(target = "username", source = "username", qualifiedByName = "encodeUsername")
  @Mapping(target = "accountNonExpired", constant = "true")
  @Mapping(target = "accountNonLocked", constant = "true")
  @Mapping(target = "credentialsNonExpired", constant = "true")
  @Mapping(target = "enabled", constant = "true")
  User userDTOToUser(UserDTO userDTO, @Context PasswordEncoder passwordEncoder);

  @Named("encodePassword")
  default String encodePassword(String password, @Context PasswordEncoder passwordEncoder) {
    return passwordEncoder.encode(password);
  }

  @Named("encodeUsername")
  default String encodeEmail(String email) {
    return email;
  }
}

