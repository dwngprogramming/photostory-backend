package space.photostory.dto.sharing;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record VerifyPinRequest(
        @NotBlank
        @Size(min = 5, max = 30)
        @Schema(description = "Unique code of the album", example = "ABC123XYZ")
        String code,

        @NotBlank
        @Size(min = 6, max = 6)
        @Pattern(regexp = "^[0-9]*$")
        @Schema(description = "6-digit PIN to unlock the album", example = "123456")
        String pin
) {
}
