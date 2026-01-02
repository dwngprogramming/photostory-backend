package space.photostory.entity.photo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import space.photostory.constant.MediaType;
import space.photostory.constant.Orientation;
import space.photostory.entity.Base;
import space.photostory.entity.story.Story;

@Entity
@Table(name = "photos")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Photo extends Base {

    @Column(name = "media_url", nullable = false)
    String mediaUrl;

    @Column(name = "media_type", nullable = false)
    @Enumerated(EnumType.STRING)
    MediaType mediaType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Orientation orientation;

    @Column(length = 50)
    String caption;

    @Column(name = "display_order", nullable = false)
    Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id", nullable = false)
    Story story;
}
