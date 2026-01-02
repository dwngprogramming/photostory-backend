package space.photostory.repository.photo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.photostory.entity.album.Album;

@Repository
public interface PhotoRepository extends JpaRepository<Album, String> {
}
