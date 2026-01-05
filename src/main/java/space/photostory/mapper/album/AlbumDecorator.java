package space.photostory.mapper.album;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import space.photostory.dto.album.AlbumRequest;
import space.photostory.dto.album.AlbumResponse;
import space.photostory.dto.toc.TOCRequest;
import space.photostory.dto.toc.TOCResponse;
import space.photostory.entity.album.Album;
import space.photostory.exception.exts.InternalServerException;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class AlbumDecorator implements AlbumMapper {

    @Autowired
    AlbumMapper delegate;

    @Autowired
    ObjectMapper mapper;

    @Override
    public AlbumResponse toResponse(Album album) {
        AlbumResponse response = delegate.toResponse(album);
        if (response == null) {
            return null;
        }

        List<TOCResponse> tocResponses = new ArrayList<>();
        String tocJson = album.getTableOfContents();
        if (tocJson != null && !tocJson.trim().isEmpty()) {
            try {
                tocResponses = mapper.readValue(tocJson, mapper.getTypeFactory().constructCollectionType(List.class, TOCResponse.class));
            } catch (JsonProcessingException e) {
                throw new InternalServerException(e);
            }
        }

        return response.toBuilder()
                .ownerId(album.getOwner().getId())
                .ownerName(album.getOwner().getFullName())
                .tableOfContents(tocResponses)
                .build();
    }

    @Override
    public Album toEntity(AlbumRequest request) {
        Album album = delegate.toEntity(request);
        if (album == null) {
            return null;
        }

        List<TOCRequest> tocRequests = request.tableOfContents();
        String tocJson = null;
        if (tocRequests != null) {
            try {
                tocJson = mapper.writeValueAsString(tocRequests);
            } catch (JsonProcessingException e) {
                throw new InternalServerException(e);
            }
        }
        album.setTableOfContents(tocJson);
        return album;
    }
}
