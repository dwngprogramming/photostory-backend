package space.photostory.contract;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.photostory.dto.DataResponse;
import space.photostory.dto.album.AlbumRequest;
import space.photostory.dto.album.AlbumResponse;
import space.photostory.dto.sharing.SharingResponse;
import space.photostory.dto.sharing.VerifyPinRequest;

/**
 * HTTP contract of the Album API: paths, inputs, validation and OpenAPI docs live here.
 * {@link space.photostory.api.AlbumApi} only implements the logic.
 * <p>
 * Do not redeclare constraints ({@code @Size}, {@code @Valid}...) in the implementation:
 * Bean Validation forbids it (HV000151).
 */
@Tag(name = "Album API", description = "API description for Album feature")
@RequestMapping("/api/v1/albums")
public interface AlbumContract {

    @GetMapping(value = "/sharing/link/{id}", produces = "application/json")
    ResponseEntity<DataResponse<SharingResponse>> getSharingInfoBySharingLink(@PathVariable String id);

    @Operation(summary = "Unwrap Album by Code",
            description = "Retrieve sharing information of an album using a unique code.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Get data successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Album not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class)
                    )
            )
    })
    @GetMapping(value = "/sharing/code/{code}", produces = "application/json")
    ResponseEntity<DataResponse<SharingResponse>> getSharingInfoByCodeIfAlbumPublic(@PathVariable
                                                                                    @Size(min = 5, max = 30)
                                                                                    @Pattern(regexp = "^[a-zA-Z0-9_.-]+$") String code);

    @Operation(summary = "Get Public Sharing Album by key",
            description = "Retrieve album details using a public access key (slug).")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Get data successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access due to invalid token",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden access",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Album not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class)
                    )
            )
    })
    @GetMapping(value = "/view/public", produces = "application/json")
    ResponseEntity<DataResponse<AlbumResponse>> viewPublicAlbum(@RequestParam
                                                                @Size(min = 16, max = 16)
                                                                String key);

    @Operation(summary = "Create new Photostory Album",
            description = """
                    Create a new album with the provided details.
                    
                    - `visibility` is required: `PUBLIC`, `PIN_PROTECTED` or `INVITE_ONLY`.
                    - `pin` (exactly 6 digits) is **required when visibility is `PIN_PROTECTED`** and is stored as a BCrypt hash.
                    - `relationshipType` is required. `customRelationship` and `customRelationshipLocale` are optional display labels.
                    - A `publicAccessKey` (16 chars) is generated automatically.""")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Album created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid album data provided",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Album code already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class)
                    )
            )
    })
    @PostMapping(produces = "application/json")
    ResponseEntity<DataResponse<Void>> createAlbum(@Valid @RequestBody AlbumRequest request);

    @Operation(summary = "Verify album PIN",
            description = """
                    Verify the 6-digit PIN of a `PIN_PROTECTED` album identified by its unwrap code.
                    On success, returns a short-lived sharing token. Send it in the `X-Album-Authorization` header \
                    to `GET /api/v1/albums/view/{id}`.""")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "PIN is valid, sharing token returned"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data (code or PIN format)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Incorrect PIN",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Album not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class)
                    )
            )
    })
    @PostMapping(value = "/sharing/pin", produces = "application/json")
    ResponseEntity<DataResponse<SharingResponse>> getSharingInfoByVerifyPin(@Valid @RequestBody VerifyPinRequest request);

    @Operation(summary = "View album with sharing token",
            description = """
                    Retrieve a `PIN_PROTECTED` album using the sharing token from `POST /api/v1/albums/sharing/pin`. \
                    The token is bound to a single album: using it for a different album id returns 403.""")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Get data successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Sharing token is invalid or expired",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Token was not issued for this album",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Album not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class)
                    )
            )
    })
    @GetMapping(value = "/view/{id}", produces = "application/json")
    ResponseEntity<DataResponse<AlbumResponse>> viewPermissionAlbum(
            @Parameter(description = "Album id (ULID)") @PathVariable String id,
            @Parameter(description = "Sharing token: `Bearer <token>`") @RequestHeader("X-Album-Authorization") String albumToken);
}
