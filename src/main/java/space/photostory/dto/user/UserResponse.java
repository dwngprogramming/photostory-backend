package space.photostory.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import space.photostory.constant.Gender;

import java.time.LocalDate;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(

        @Schema(description = "Unique identifier of the user (ULID-based)", example = "01KDN7C9F9KZPCHCXYXG22CZA6")
        String id,

        @Schema(description = "Username of the user", example = "johndoe")
        String username,

        @Schema(description = "Email address of the user", example = "johndoe@test.mail")
        String email,

        @Schema(description = "Full name of the user", example = "John Doe")
        String fullName,

        @Schema(description = "Date of birth of the user", example = "1990-01-01")
        LocalDate dob,

        @Schema(description = "Gender of the user", example = "male")
        Gender gender,

        @Schema(description = "Profile picture URL of the user", example = "https://example.com/profile.jpg")
        String avatar
) {
}
