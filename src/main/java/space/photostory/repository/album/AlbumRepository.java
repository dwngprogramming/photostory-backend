package space.photostory.repository.album;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.photostory.entity.album.Album;

import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, String> {
    Optional<Album> findByCode(String code);

    Optional<Album> findByPublicAccessKey(String key);

    boolean existsByCode(String code);
}
