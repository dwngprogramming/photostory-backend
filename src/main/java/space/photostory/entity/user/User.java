package space.photostory.entity.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import space.photostory.constant.Gender;
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

    @Column(unique = true, length = 30)
    String username;

    @Column(unique = true, length = 50)
    String email;

    @Column(nullable = false)
    String password;

    @Column(name = "full_name", length = 100)
    String fullName;

    @Column
    LocalDate dob;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    Gender gender;

    @Column
    String avatar;
}
