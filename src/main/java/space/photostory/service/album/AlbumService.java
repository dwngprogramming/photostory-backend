package space.photostory.service.album;

import space.photostory.dto.album.AlbumRequest;
import space.photostory.dto.album.AlbumResponse;

public interface AlbumService {
    AlbumResponse getAlbumById(String id);

    AlbumResponse getPublicAlbumByKey(String key);

    void createAlbum(String userId, AlbumRequest request);

    boolean existsByCode(String code);
}
