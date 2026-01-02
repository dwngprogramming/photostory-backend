package space.photostory.dto.album;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import space.photostory.dto.story.StoryRequest;

import java.util.List;

@Builder(toBuilder = true)
public record AlbumRequest(

        @NotBlank
        @Schema(description = "Unique code of the album", example = "ABC123XYZ")
        String code,

        @NotNull
        @Size(min = 1, max = 10)
        @Schema(description = "List of recipient name", example = "[\"Dung Pham\", \"Minh Tam\"]")
        List<String> recipients,

        @NotBlank
        @Schema(description = "Title of the album", example = "My Summer Vacation")
        String title,

        @Schema(description = "Description of the album", example = "A collection of photos from my summer vacation.")
        String description,

        @Schema(description = "URL of the theme song for the album", example = "https://example.com/theme-song.mp3")
        String themeSongUrl,

        @NotBlank
        @Schema(description = "Note in french flip", example = "This is a special note.")
        String frenchFlipNote,

        @Schema(description = "URL of the album avatar image", example = "https://example.com/avatar.jpg")
        String avatarUrl,

        @Schema(description = "Preface of the album", example = "Welcome to my photo album!")
        String preface,

        @Schema(description = "URL of the highlight photo", example = "https://example.com/highlight.jpg")
        String highlightPhotoUrl,

        @NotNull
        @Schema(description = "List of photo-story in the album")
        List<StoryRequest> stories,

        @Schema(description = "Afterword of the album", example = "Thank you for viewing my album.")
        String afterword
) {
}
