package space.photostory.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.photostory.entity.location.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {
}
