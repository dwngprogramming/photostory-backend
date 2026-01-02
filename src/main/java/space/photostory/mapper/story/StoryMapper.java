package space.photostory.mapper.story;

import org.mapstruct.Mapper;
import space.photostory.dto.story.StoryRequest;
import space.photostory.dto.story.StoryResponse;
import space.photostory.entity.story.Story;
import space.photostory.mapper.GlobalMapperConfig;
import space.photostory.mapper.location.LocationMapper;
import space.photostory.mapper.photo.PhotoMapper;

@Mapper(config = GlobalMapperConfig.class, uses = {PhotoMapper.class, LocationMapper.class})
public interface StoryMapper {
    StoryResponse toResponse(Story story);
    Story toEntity(StoryRequest request);
}
