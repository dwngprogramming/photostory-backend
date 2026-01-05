package space.photostory.dto.location;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import space.photostory.constant.LocationTheme;

@Builder(toBuilder = true)
public record LocationRequest(

        @Schema(description = "Name of the location", example = "Eiffel Tower")
        String name,

        @Schema(description = "Theme of the location", example = "urban")
        LocationTheme locationTheme,

        @Schema(description = "URL of the map location", example = "https://maps.google.com/?q=Eiffel+Tower")
        String mapUrl,

        @Schema(description = "Display order of the location in the story", example = "1")
        Integer displayOrder
) {
}
