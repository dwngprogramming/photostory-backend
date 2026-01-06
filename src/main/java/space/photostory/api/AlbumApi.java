package space.photostory.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.photostory.constant.ResourceType;
import space.photostory.dto.ApiResponse;
import space.photostory.dto.album.AlbumRequest;
import space.photostory.dto.album.AlbumResponse;
import space.photostory.dto.sharing.SharingResponse;
import space.photostory.exception.exts.NotFoundException;
import space.photostory.security.SharingService;
import space.photostory.service.album.AlbumService;

@RestController
@RequestMapping("/api/v1/albums")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Album API", description = "API for Album photo-story feature")
public class AlbumApi {
    AlbumService albumService;
    SharingService sharingService;

    @GetMapping("/unwrap/{code}")
    public ResponseEntity<ApiResponse<SharingResponse>> unwrapAlbumByCode(@PathVariable String code) {
        SharingResponse sharingResponse = albumService.generateAccessPermission(code);
        return ResponseEntity.ok().body(ApiResponse.getSuccess(sharingResponse));
    }

    @GetMapping("/sharing/{id}")
    public ResponseEntity<ApiResponse<AlbumResponse>> getSharingAlbum(@PathVariable String id, @RequestParam String token) {
        // Token error will be handled in SharingService
        sharingService.verifySharingToken(token, ResourceType.album);
        AlbumResponse albumResponse = albumService.getAlbumById(id);
        return ResponseEntity.ok().body(ApiResponse.getSuccess(albumResponse));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createAlbum(@Valid @RequestBody AlbumRequest request) {
        // Sau này sẽ lấy từ Security Context
        String userId = "01KDSP7XYEG857SM3DBJJ720FA";
        String code = request.code();
        if (albumService.existsByCode(code)) {
            throw new NotFoundException("album.code.exists", code);
        }
        albumService.createAlbum(userId, request);
        return ResponseEntity.ok().body(ApiResponse.createSuccess());
    }
}
