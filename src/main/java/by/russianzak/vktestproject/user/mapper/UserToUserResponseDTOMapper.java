package by.russianzak.vktestproject.user.mapper;

import by.russianzak.vktestproject.user.User;
import by.russianzak.vktestproject.user.dto.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserToUserResponseDTOMapper {

  UserToUserResponseDTOMapper INSTANCE = Mappers.getMapper(UserToUserResponseDTOMapper.class);

  @Mapping(target = "username", source = "username")
  @Mapping(target = "id", source = "id")
  UserResponseDTO userToUserResponseDTO(User user);

}
