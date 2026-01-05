package space.photostory.service.album;

import space.photostory.dto.album.AlbumRequest;
import space.photostory.dto.album.AlbumResponse;

public interface AlbumService {
    AlbumResponse getAlbumById(String id);
    void createAlbum(String userId, AlbumRequest request);
}
