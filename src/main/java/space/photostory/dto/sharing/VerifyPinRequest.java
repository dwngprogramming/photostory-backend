package space.photostory.dto.sharing;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record VerifyPinRequest(
        @NotBlank
        @Size(min = 5, max = 20)
        String code,

        @NotBlank
        @Size(min = 6, max = 6)
        @Pattern(regexp = "^[0-9]*$")
        String pin
) {
}
