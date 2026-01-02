package space.photostory.mapper.user;

import org.mapstruct.Mapper;
import space.photostory.dto.user.UserResponse;
import space.photostory.entity.user.User;
import space.photostory.mapper.GlobalMapperConfig;

@Mapper(config = GlobalMapperConfig.class)
public interface UserMapper {
    UserResponse toResponse(User user);
}
