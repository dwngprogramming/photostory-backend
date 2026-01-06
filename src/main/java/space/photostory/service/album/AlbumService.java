package space.photostory.service.album;

import space.photostory.dto.album.AlbumRequest;
import space.photostory.dto.album.AlbumResponse;
import space.photostory.dto.sharing.SharingResponse;

public interface AlbumService {
    AlbumResponse getAlbumById(String id);

    void createAlbum(String userId, AlbumRequest request);

    SharingResponse generateAccessPermission(String code);

    boolean existsByCode(String code);
}
