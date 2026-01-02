package space.photostory.entity.location;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
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

    @Column(name = "map_url")
    String mapUrl;

    @Column(name = "display_order", nullable = false)
    Integer displayOrder;
}
