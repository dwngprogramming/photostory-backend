package space.photostory.repository.album;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.photostory.entity.album.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, String> {

}
