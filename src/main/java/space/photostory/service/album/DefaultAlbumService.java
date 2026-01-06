package space.photostory.service.album;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import space.photostory.dto.album.AlbumRequest;
import space.photostory.dto.album.AlbumResponse;
import space.photostory.dto.sharing.SharingResponse;
import space.photostory.entity.album.Album;
import space.photostory.entity.user.User;
import space.photostory.exception.exts.NotFoundException;
import space.photostory.mapper.album.AlbumMapper;
import space.photostory.repository.album.AlbumRepository;
import space.photostory.security.SharingService;
import space.photostory.service.user.UserService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DefaultAlbumService implements AlbumService {
    AlbumRepository albumRepository;
    UserService userService;
    SharingService sharingService;
    AlbumMapper albumMapper;

    @Override
    public AlbumResponse getAlbumById(String id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("album.not_found_by_code", id));
        return albumMapper.toResponse(album);
    }

    @Override
    public void createAlbum(String userId, AlbumRequest request) {
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
        albumRepository.save(album);
    }

    @Override
    public SharingResponse generateAccessPermission(String code) {
        String albumId = albumRepository.findIdByCode(code)
                .orElseThrow(() -> new NotFoundException("unwrap.code.not_found", code));

        return sharingService.createSharingAccess(albumId);
    }

    @Override
    public boolean existsByCode(String code) {
        return albumRepository.existsByCode(code);
    }
}
