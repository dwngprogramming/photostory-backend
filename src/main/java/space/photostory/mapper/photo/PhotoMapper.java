package space.photostory.mapper.photo;

import org.mapstruct.Mapper;
import space.photostory.dto.photo.PhotoRequest;
import space.photostory.dto.photo.PhotoResponse;
import space.photostory.entity.photo.Photo;
import space.photostory.mapper.GlobalMapperConfig;
import space.photostory.mapper.story.StoryMapper;

@Mapper(config = GlobalMapperConfig.class, uses = {StoryMapper.class})
public interface PhotoMapper {
    PhotoResponse toResponse(Photo photo);
    Photo toEntity(PhotoRequest request);
}
