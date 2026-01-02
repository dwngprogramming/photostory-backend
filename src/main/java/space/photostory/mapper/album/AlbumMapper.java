package space.photostory.mapper.album;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import space.photostory.dto.album.AlbumRequest;
import space.photostory.dto.album.AlbumResponse;
import space.photostory.entity.album.Album;
import space.photostory.mapper.GlobalMapperConfig;
import space.photostory.mapper.story.StoryMapper;

@Mapper(config = GlobalMapperConfig.class, uses = {StoryMapper.class})
@DecoratedWith(AlbumDecorator.class)
public interface AlbumMapper {
    AlbumResponse toResponse(Album album);
    Album toEntity(AlbumRequest request);
}
