package space.photostory.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.photostory.dto.ApiResponse;
import space.photostory.dto.album.AlbumRequest;
import space.photostory.dto.album.AlbumResponse;
import space.photostory.service.album.AlbumService;

@RestController
@RequestMapping("/api/v1/albums")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Album API", description = "API for Album photo-story feature")
public class AlbumApi {
    AlbumService albumService;

    @GetMapping("/{code}")
    public ResponseEntity<ApiResponse<AlbumResponse>> getAlbumByCode(@PathVariable String code) {
        AlbumResponse albumResponse = albumService.getAlbumByCode(code);
        return ResponseEntity.ok().body(ApiResponse.getSuccess(albumResponse));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createAlbum(@Valid @RequestBody AlbumRequest request) {
        // Sau này sẽ lấy từ Security Context
        String userId = "01KDSP7XYEG857SM3DBJJ720FA";
        albumService.createAlbum(userId, request);
        return ResponseEntity.ok().body(ApiResponse.createSuccess());
    }
}
