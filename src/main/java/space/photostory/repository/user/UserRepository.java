package space.photostory.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.photostory.entity.profile.Profile;

@Repository
public interface UserRepository extends JpaRepository<Profile, String> {
}
