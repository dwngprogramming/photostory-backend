package space.photostory.repository.album;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import space.photostory.entity.album.Album;

import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, String> {

    @Query("SELECT a.id FROM Album a WHERE a.code = :code")
    Optional<String> findIdByCode(String code);

    Optional<Album> findByCode(String code);

    Optional<Album> findByPublicAccessKey(String key);

    boolean existsByCode(String code);
}
