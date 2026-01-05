package space.photostory.entity.location;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import space.photostory.constant.LocationTheme;
import space.photostory.entity.Base;

@Entity
@Table(name = "locations")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Location extends Base {

    @Column(nullable = false, length = 100)
    String name;

    @Column(name = "location_theme", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    LocationTheme locationTheme = LocationTheme.standard;

    @Column(name = "map_url", length = 512)
    String mapUrl;

    @Column(name = "display_order", nullable = false)
    Integer displayOrder;
}
