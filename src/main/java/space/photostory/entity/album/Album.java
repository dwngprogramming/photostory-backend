package space.photostory.entity.album;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import space.photostory.entity.Base;
import space.photostory.entity.story.Story;
import space.photostory.entity.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "albums")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Album extends Base {

    @Column(unique = true, length = 50)
    String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    User owner;

    @ElementCollection
    @CollectionTable(
            name = "album_recipients",
            joinColumns = @JoinColumn(name = "album_id")
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

    @Column(name = "avatar_url")
    String avatarUrl;

    @Column(columnDefinition = "TEXT")
    String preface;

    @Column(name = "highlight_photo_url")
    String highlightPhotoUrl;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    List<Story> stories = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    String afterword;
}
