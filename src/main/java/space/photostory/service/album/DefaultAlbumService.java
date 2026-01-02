package space.photostory.service.album;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import space.photostory.dto.album.AlbumRequest;
import space.photostory.dto.album.AlbumResponse;
import space.photostory.entity.album.Album;
import space.photostory.entity.user.User;
import space.photostory.exception.exts.NotFoundException;
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

    @Override
    public AlbumResponse getAlbumByCode(String code) {
        AlbumResponse response = albumMapper.toResponse(albumRepository.findByCode(code));
        if (response == null) {
            throw new NotFoundException("album.not_found_by_code", code);
        }
        return response;
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
}
