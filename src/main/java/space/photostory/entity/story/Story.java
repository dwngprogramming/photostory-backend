package space.photostory.entity.story;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import space.photostory.constant.WeatherType;
import space.photostory.entity.Base;
import space.photostory.entity.album.Album;
import space.photostory.entity.location.Location;
import space.photostory.entity.photo.Photo;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stories")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Story extends Base {

    @Column(nullable = false, length = 40)
    String title;

    @Column(name = "event_date", nullable = false, length = 10)
    LocalDate eventDate;

    @Column(name = "display_order_in_day", nullable = false)
    Integer displayOrderInDay;

    @Column
    @Enumerated(EnumType.STRING)
    WeatherType weather;

    @Column(nullable = false, columnDefinition = "TEXT")
    String content;

    @Column(name = "music_url")
    String musicUrl;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    List<Photo> photos = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "story_id", nullable = false)
    @Builder.Default
    List<Location> locations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    Album album;
}
