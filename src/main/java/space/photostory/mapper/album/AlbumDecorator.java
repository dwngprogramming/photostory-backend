package space.photostory.mapper.album;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import space.photostory.dto.album.AlbumResponse;
import space.photostory.entity.album.Album;

@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class AlbumDecorator implements AlbumMapper {

    @Autowired
    AlbumMapper delegate;

    @Override
    public AlbumResponse toResponse(Album album) {
        AlbumResponse response = delegate.toResponse(album);
        if (response == null) {
            return null;
        }

        return response.toBuilder()
                .ownerId(album.getOwner().getId())
                .ownerName(album.getOwner().getFullName())
                .build();
    }
}
