package space.photostory.repository.story;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.photostory.entity.story.Story;

@Repository
public interface StoryRepository extends JpaRepository<Story, String> {
}
