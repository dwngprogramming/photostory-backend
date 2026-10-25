package space.photostory.entity.album;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import space.photostory.constant.RelationshipType;
import space.photostory.constant.ResourceVisibility;
import space.photostory.constant.Gender;
import space.photostory.constant.SupportedLocale;
import space.photostory.entity.Base;
import space.photostory.entity.profile.Profile;
import space.photostory.entity.story.Story;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "albums", indexes = @Index(name = "idx_albums_owner_id", columnList = "owner_id"))
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Album extends Base {

    @Column(unique = true, length = 30)
    String code;

    @Column(unique = true, name = "public_access_key", length = 16)
    String publicAccessKey;

    @Column(name = "pin")
    String pin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false, foreignKey = @ForeignKey(name = "fk_albums_owner"))
    Profile owner;

    @Column(name = "visibility", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    ResourceVisibility visibility = ResourceVisibility.PIN_PROTECTED;

    @Column(name = "relationship_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    RelationshipType relationshipType;

    @Column(name = "custom_relationship", length = 30)
    String customRelationship;

    @Column(name = "custom_relationship_locale", length = 20)
    @Enumerated(EnumType.STRING)
    SupportedLocale customRelationshipLocale;

    @Column(name = "saved_date", nullable = false)
    LocalDate savedDate;

    @ElementCollection
    @CollectionTable(
            name = "album_recipients",
            joinColumns = @JoinColumn(name = "album_id", foreignKey = @ForeignKey(name = "fk_album_recipients_album")),
            indexes = @Index(name = "idx_album_recipients_album_id", columnList = "album_id")
    )
    List<String> recipients;

    @Column(nullable = false)
    String title;

    @Column
    String description;

    @Column(name = "theme_song_url")
    String themeSongUrl;

    @Column(name = "french_flip_note", nullable = false, length = 350)
    String frenchFlipNote;

    @Column(name = "french_flip_place", length = 20)
    String frenchFlipPlace;

    @Column(name = "avatar_url")
    String avatarUrl;

    @Column(name = "avatar_gender", length = 20)
    @Enumerated(EnumType.STRING)
    Gender avatarGender;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "table_of_contents", columnDefinition = "jsonb")
    String tableOfContents;

    @Column(columnDefinition = "TEXT")
    String preface;

    @Column(name = "highlight_photo_url")
    String highlightPhotoUrl;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    List<Story> stories = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    String afterword;

    // (album_id, profile_id) included index for album_id (left-most prefix). Just create an index for profile_id
    @ManyToMany
    @JoinTable(
            name = "album_shared_profiles",
            joinColumns = @JoinColumn(name = "album_id", foreignKey = @ForeignKey(name = "fk_album_shared_profiles_album")),
            inverseJoinColumns = @JoinColumn(name = "profile_id", foreignKey = @ForeignKey(name = "fk_album_shared_profiles_profile")),
            indexes = @Index(name = "idx_album_shared_profiles_profile_id", columnList = "profile_id")
    )
    @Builder.Default
    Set<Profile> sharedWith = new HashSet<>();
}
