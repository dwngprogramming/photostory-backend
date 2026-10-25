package space.photostory.mapper.user;

import org.mapstruct.Mapper;
import space.photostory.dto.profile.ProfileResponse;
import space.photostory.entity.profile.Profile;
import space.photostory.mapper.GlobalMapperConfig;
import space.photostory.mapper.MapperHelper;

@Mapper(config = GlobalMapperConfig.class, uses = {MapperHelper.class})
public interface UserMapper {
    ProfileResponse toResponse(Profile profile);
}
