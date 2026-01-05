package space.photostory.dto.toc;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TOCRequest(

        @NotBlank
        @Schema(description = "Title of story", example = "Story Title")
        String storyTitle,

        @NotNull
        @Schema(description = "Event date of story", example = "2023-10-15")
        LocalDate eventDate,

        @NotNull
        @Min(1)
        @Schema(description = "Display order of story in the day", example = "1")
        Integer displayOrderInDay,

        @NotNull
        @Min(1)
        @Schema(description = "Page number of story in the album", example = "1")
        Integer page
) {
}
