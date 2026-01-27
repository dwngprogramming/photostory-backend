package space.photostory.api;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.photostory.constant.ResourceType;
import space.photostory.docs.AlbumDocs;
import space.photostory.dto.ApiResponse;
import space.photostory.dto.album.AlbumRequest;
import space.photostory.dto.album.AlbumResponse;
import space.photostory.dto.sharing.SharingResponse;
import space.photostory.dto.sharing.VerifyPinRequest;
import space.photostory.exception.exts.ConflictException;
import space.photostory.service.album.AlbumService;
import space.photostory.service.sharing.SharingService;

@RestController
@RequestMapping("/api/v1/albums")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlbumApi implements AlbumDocs {
    AlbumService albumService;
    SharingService sharingService;

    @GetMapping(value = "/sharing/link/{id}", produces = "application/json")
    public ResponseEntity<ApiResponse<SharingResponse>> getSharingInfoBySharingLink(@PathVariable String id) {
        return null;
    }

    @GetMapping(value = "/sharing/code/{code}", produces = "application/json")
    public ResponseEntity<ApiResponse<SharingResponse>> getSharingInfoByCodeIfAlbumPublic(@Valid @PathVariable String code) {
        SharingResponse sharingResponse = sharingService.verifyAccessPermission(code);
        return ResponseEntity.ok().body(ApiResponse.getSuccess(sharingResponse));
    }

    @PostMapping(value = "/sharing/pin", produces = "application/json")
    public ResponseEntity<ApiResponse<SharingResponse>> getSharingInfoByVerifyPin(@Valid @RequestBody VerifyPinRequest request) {
        SharingResponse sharingResponse = sharingService.verifyAccessPin(request.code(), request.pin());
        return ResponseEntity.ok().body(ApiResponse.getSuccess(sharingResponse));
    }

    @GetMapping(value = "/view/public", produces = "application/json")
    public ResponseEntity<ApiResponse<AlbumResponse>> viewPublicAlbum(@RequestParam String key) {
        AlbumResponse albumResponse = albumService.getPublicAlbumByKey(key);
        return ResponseEntity.ok().body(ApiResponse.getSuccess(albumResponse));
    }

    @GetMapping(value = "/view/{id}", produces = "application/json")
    public ResponseEntity<ApiResponse<AlbumResponse>> viewPermissionAlbum(@PathVariable String id,
                                                                          @RequestHeader(value = "X-Album-Authorization") String albumToken) {
        String token = albumToken.replace("Bearer ", "");
        // Token error will be handled in SharingService
        sharingService.verifySharingToken(token, ResourceType.album);
        AlbumResponse albumResponse = albumService.getAlbumById(id);
        return ResponseEntity.ok().body(ApiResponse.getSuccess(albumResponse));
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<ApiResponse<Void>> createAlbum(@Valid @RequestBody AlbumRequest request) {
        // Sau này sẽ lấy từ Security Context
        String userId = "01KDSP7XYEG857SM3DBJJ720FA";
        String code = request.code();
        if (albumService.existsByCode(code)) {
            throw new ConflictException("album.code.exists", code);
        }
        albumService.createAlbum(userId, request);
        return ResponseEntity.ok().body(ApiResponse.createSuccess());
    }
}
