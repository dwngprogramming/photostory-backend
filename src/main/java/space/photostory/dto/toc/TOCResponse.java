package space.photostory.dto.toc;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;

@Builder(toBuilder = true)
public record TOCResponse(

        @Schema(description = "Title of story", example = "Story Title")
        String storyTitle,

        @Schema(description = "Event date of story", example = "2023-10-15")
        LocalDate eventDate,

        @Schema(description = "Display order of story in the day", example = "1")
        Integer displayOrderInDay,

        @Schema(description = "Page number of story in the album", example = "1")
        Integer page
) {
}
