package space.photostory.entity.profile;

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
@Table(name = "profiles")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Profile extends Base {

    @Column(name = "account_id", unique = true, nullable = false, updatable = false, length = 36)
    String accountId;       // Keycloak account id: UUID v4 stored as varchar(36)

    // lowercase only: normalized in normalize(), enforced by ck_profiles_username_normalized (Flyway)
    @Column(length = 30, unique = true, nullable = false, updatable = false)
    String username;

    // lowercase only: normalized in normalize(), enforced by ck_profiles_email_normalized (Flyway)
    @Column(length = 50, unique = true, nullable = false, updatable = false)
    String email;

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
