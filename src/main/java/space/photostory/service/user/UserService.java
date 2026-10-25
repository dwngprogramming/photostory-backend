package space.photostory.service.user;

import space.photostory.entity.profile.Profile;

public interface UserService {
    Profile getEntityById(String id);
}
