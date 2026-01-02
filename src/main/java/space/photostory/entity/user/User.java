package space.photostory.entity.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import space.photostory.entity.Base;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends Base {

    @Schema(description = "Unique identifier of the user (ULID-based)", example = "01KDN7C9F9KZPCHCXYXG22CZA6")
    String id;

    @Schema(description = "Username of the user", example = "johndoe")
    String username;

    @Schema(description = "Email address of the user", example = "johndoe@test.mail")
    String email;

    @Schema(description = "Full name of the user", example = "John Doe")
    String fullName;

    @Schema(description = "Date of birth of the user", example = "1990-01-01")
    LocalDate dob;
}
