package space.photostory.dto.photo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import space.photostory.constant.MediaType;
import space.photostory.constant.Orientation;

@Builder(toBuilder = true)
public record PhotoRequest(

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
