package space.photostory.repository.photo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.photostory.entity.photo.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, String> {
}
