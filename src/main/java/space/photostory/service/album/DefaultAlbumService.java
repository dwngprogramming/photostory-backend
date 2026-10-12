package space.photostory.service.album;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import space.photostory.constant.ResourceVisibility;
import space.photostory.dto.album.AlbumRequest;
import space.photostory.dto.album.AlbumResponse;
import space.photostory.entity.album.Album;
import space.photostory.entity.user.User;
import space.photostory.exception.exts.NotFoundException;
import space.photostory.exception.exts.BadRequestException;
import space.photostory.exception.exts.ForbiddenException;
import space.photostory.mapper.album.AlbumMapper;
import space.photostory.repository.album.AlbumRepository;
import space.photostory.service.user.UserService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DefaultAlbumService implements AlbumService {
    AlbumRepository albumRepository;
    UserService userService;
    AlbumMapper albumMapper;
    PasswordEncoder passwordEncoder;

    static final int PUBLIC_ACCESS_KEY_LENGTH = 16;

    @Override
    public AlbumResponse getAlbumById(String id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("album.not_found_by_id", id));
        return albumMapper.toResponse(album);
    }

    @Override
    public AlbumResponse getPublicAlbumByKey(String key) {
        Album album = albumRepository.findByPublicAccessKey(key)
                .orElseThrow(() -> new NotFoundException("album.not_found_by_key", key));
        if (album.getVisibility() != ResourceVisibility.PUBLIC) {
            throw new ForbiddenException("sharing.access_reject", key);
        }
        return albumMapper.toResponse(album);
    }

    @Override
    public void createAlbum(String userId, AlbumRequest request) {
        boolean isPrivate = request.visibility() == ResourceVisibility.PRIVATE;
        if (isPrivate && request.pin() == null) {
            throw new BadRequestException("album.pin.required");
        }

        User user = userService.getEntityById(userId);
        Album album = albumMapper.toEntity(request);

        if (album.getStories() != null) {
            album.getStories().forEach(story -> {
                story.setAlbum(album);

                if (story.getPhotos() != null) {
                    story.getPhotos().forEach(photo -> photo.setStory(story));
                }
            });
        }

        album.setOwner(user);
        album.setVisibility(request.visibility());
        album.setPublicAccessKey(generatePublicAccessKey());
        if (isPrivate) {
            album.setPin(passwordEncoder.encode(request.pin()));
        }
        albumRepository.save(album);
    }

    private String generatePublicAccessKey() {
        String key;
        do {
            key = NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, NanoIdUtils.DEFAULT_ALPHABET, PUBLIC_ACCESS_KEY_LENGTH);
        } while (albumRepository.existsByPublicAccessKey(key));
        return key;
    }

    @Override
    public boolean existsByCode(String code) {
        return albumRepository.existsByCode(code);
    }
}
