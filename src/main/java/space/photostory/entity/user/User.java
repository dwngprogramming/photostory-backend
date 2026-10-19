package space.photostory.entity.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import space.photostory.constant.Gender;
import space.photostory.entity.Base;
import space.photostory.entity.album.Album;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends Base {

    // lowercase only: normalized in normalize(), enforced by ck_users_username_lowercase (Flyway)
    @Column(unique = true, length = 30)
    String username;

    // lowercase only: normalized in normalize(), enforced by ck_users_email_lowercase (Flyway)
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

    @ManyToMany(mappedBy = "sharedWith", fetch = FetchType.LAZY)
    @Builder.Default
    Set<Album> sharedAlbums = new HashSet<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    List<Album> ownedAlbums = new ArrayList<>();

    @PrePersist
    @PreUpdate
    void normalize() {
        if (username != null) {
            username = username.trim().toLowerCase(Locale.ROOT);
        }
        if (email != null) {
            email = email.trim().toLowerCase(Locale.ROOT);
        }
    }
}
