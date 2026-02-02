package space.photostory.dto.album;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import space.photostory.constant.Gender;
import space.photostory.constant.RelationshipType;
import space.photostory.dto.story.StoryResponse;
import space.photostory.dto.toc.TOCResponse;

import java.time.LocalDate;
import java.util.List;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AlbumResponse(

        @Schema(description = "Unique identifier of the album (ULID-based)", example = "01KDN7C9F9KZPCHCXYXG22CZA6")
        String id,

        @Schema(description = "Identifier of the album owner (ULID-based)", example = "01KDN7CVN90E2MN74WHQ0Z37S2")
        String ownerId,

        @Schema(description = "Name of the album owner", example = "John Doe")
        String ownerName,

        @Schema(description = "Relationship type between the album owner and the album receiver", example = "lover")
        RelationshipType relationshipType,

        @Schema(description = "Custom relationship by locale (Example for \"wife\" in Vietnamese", example = "Vợ iu")
        String customRelationship,

        @Schema(description = "Locale of the album to display relationship", example = "vi")
        String customRelationshipLocale,

        @Schema(description = "Date when the album was saved", example = "2025-12-20")
        LocalDate savedDate,

        @Schema(description = "List of recipient name", example = "[\"Dung Pham\", \"Minh Tam\"]")
        List<String> recipients,

        @Schema(description = "Title of the album", example = "My Summer Vacation")
        String title,

        @Schema(description = "Description of the album", example = "A collection of photos from my summer vacation.")
        String description,

        @Schema(description = "URL of the theme song for the album", example = "https://example.com/theme-song.mp3")
        String themeSongUrl,

        @Schema(description = "Note in french flip", example = "This is a special note.")
        String frenchFlipNote,

        @Schema(description = "Place of french flip", example = "Paris")
        String frenchFlipPlace,

        @Schema(description = "URL of the album avatar image", example = "https://example.com/avatar.jpg")
        String avatarUrl,

        @Schema(description = "Gender of the album owner", example = "male")
        Gender avatarGender,

        @Schema(description = "Preface of the album", example = "Welcome to my photo album!")
        String preface,

        @Schema(description = "URL of the highlight photo", example = "https://example.com/highlight.jpg")
        String highlightPhotoUrl,

        @Schema(description = "Table of contents of the album")
        List<TOCResponse> tableOfContents,

        @Schema(description = "List of photo-story in the album")
        List<StoryResponse> stories,

        @Schema(description = "Afterword of the album", example = "Thank you for viewing my album.")
        String afterword
) {
}
