package space.photostory.dto.story;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import space.photostory.constant.WeatherType;
import space.photostory.dto.location.LocationRequest;
import space.photostory.dto.photo.PhotoRequest;

import java.time.LocalDate;
import java.util.List;

@Builder(toBuilder = true)
public record StoryRequest(

        @Schema(description = "Title of the story", example = "A Day at the Beach")
        String title,

        @Schema(description = "Date of the event in the story", example = "2025-12-14")
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
        List<PhotoRequest> photos,

        @Schema(description = "List of locations associated with the story")
        List<LocationRequest> locations
) {
}
