package space.photostory.api;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import space.photostory.constant.ResourceType;
import space.photostory.contract.AlbumContract;
import space.photostory.dto.DataResponse;
import space.photostory.dto.album.AlbumRequest;
import space.photostory.dto.album.AlbumResponse;
import space.photostory.dto.sharing.SharingResponse;
import space.photostory.dto.sharing.VerifyPinRequest;
import space.photostory.exception.exts.ConflictException;
import space.photostory.service.album.AlbumService;
import space.photostory.service.sharing.SharingService;

// Paths, request bindings and validation are declared in AlbumContract
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlbumApi implements AlbumContract {
    AlbumService albumService;
    SharingService sharingService;

    @Override
    public ResponseEntity<DataResponse<SharingResponse>> getSharingInfoBySharingLink(String id) {
        return null;
    }

    @Override
    public ResponseEntity<DataResponse<SharingResponse>> getSharingInfoByCodeIfAlbumPublic(String code) {
        SharingResponse sharingResponse = sharingService.verifyAccessPermission(code);
        return ResponseEntity.ok().body(DataResponse.getSuccess(sharingResponse));
    }

    @Override
    public ResponseEntity<DataResponse<SharingResponse>> getSharingInfoByVerifyPin(VerifyPinRequest request) {
        SharingResponse sharingResponse = sharingService.verifyAccessPin(request.code(), request.pin());
        return ResponseEntity.ok().body(DataResponse.getSuccess(sharingResponse));
    }

    @Override
    public ResponseEntity<DataResponse<AlbumResponse>> viewPublicAlbum(String key) {
        AlbumResponse albumResponse = albumService.getPublicAlbumByKey(key);
        return ResponseEntity.ok().body(DataResponse.getSuccess(albumResponse));
    }

    @Override
    public ResponseEntity<DataResponse<AlbumResponse>> viewPermissionAlbum(String id, String albumToken) {
        String token = albumToken.replace("Bearer ", "");
        // Token error will be handled in SharingService
        sharingService.verifySharingToken(token, ResourceType.album, id);
        AlbumResponse albumResponse = albumService.getAlbumById(id);
        return ResponseEntity.ok().body(DataResponse.getSuccess(albumResponse));
    }

    @Override
    public ResponseEntity<DataResponse<Void>> createAlbum(AlbumRequest request) {
        // Sau này sẽ lấy từ Security Context
        String userId = "01KDSP7XYEG857SM3DBJJ720FA";
        String code = request.code();
        if (albumService.existsByCode(code)) {
            throw new ConflictException("album.code.exists", code);
        }
        albumService.createAlbum(userId, request);
        return ResponseEntity.ok().body(DataResponse.createSuccess());
    }
}
