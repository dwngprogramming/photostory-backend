package space.photostory.mapper.album;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import space.photostory.dto.album.AlbumRequest;
import space.photostory.dto.album.AlbumResponse;
import space.photostory.entity.album.Album;
import space.photostory.mapper.GlobalMapperConfig;
import space.photostory.mapper.story.StoryMapper;

@Mapper(config = GlobalMapperConfig.class, uses = {StoryMapper.class})
@DecoratedWith(AlbumDecorator.class)
public interface AlbumMapper {

    @Mapping(ignore = true, target = "tableOfContents")
    AlbumResponse toResponse(Album album);

    @Mapping(ignore = true, target = "tableOfContents")
    Album toEntity(AlbumRequest request);
}
