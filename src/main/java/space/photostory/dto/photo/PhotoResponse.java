package space.photostory.dto.photo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import space.photostory.constant.MediaType;
import space.photostory.constant.Orientation;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PhotoResponse(

        @Schema(description = "Unique identifier of the photo (ULID-based)", example = "01KDN7C9F9KZPCHCXYXG22CZA6")
        String id,

        @Schema(description = "URL of the media (photo or video)", example = "https://example.com/photo.jpg")
        String mediaUrl,

        @Schema(description = "Type of the media", example = "photo")
        MediaType mediaType,

        @Schema(description = "Orientation of the media", example = "landscape")
        Orientation orientation,

        @Schema(description = "Caption or description of the photo", example = "Sunset at the beach")
        String caption,

        @Schema(description = "Display order of the photo in the story", example = "1")
        Integer displayOrder
) {
}
