package space.photostory.service.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import space.photostory.entity.user.User;
import space.photostory.exception.exts.NotFoundException;
import space.photostory.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DefaultUserService implements UserService {
    UserRepository userRepository;

    @Override
    public User getEntityById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user.not_found"));
    }
}
