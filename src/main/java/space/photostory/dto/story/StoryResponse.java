package space.photostory.dto.story;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import space.photostory.constant.WeatherType;
import space.photostory.dto.location.LocationResponse;
import space.photostory.dto.photo.PhotoResponse;

import java.time.LocalDate;
import java.util.List;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StoryResponse(

        @Schema(description = "Unique identifier of the story (ULID-based)", example = "01KDN7C9F9KZPCHCXYXG22CZA6")
        String id,

        @Schema(description = "Title of the story", example = "A Day at the Beach")
        String title,

        @Schema(description = "Date when the album was saved in Offset Date Time", example = "2025-12-20")
        LocalDate eventDate,

        @Schema(description = "Display order of the story in the album", example = "1")
        Integer displayOrderInDay,

        @Schema(description = "Weather condition during the event", example = "sunny")
        WeatherType weather,

        @Schema(description = "Content or description of the story", example = "It was a beautiful sunny day at the beach...")
        String content,

        @Schema(description = "URL of the background music for the story", example = "https://example.com/music.mp3")
        String musicUrl,

        @Schema(description = "List of photos associated with the story")
        List<PhotoResponse> photos,

        @Schema(description = "List of locations associated with the story")
        List<LocationResponse> locations
) {
}
