package space.photostory.dto.sharing;

import com.fasterxml.jackson.annotation.JsonInclude;
import space.photostory.constant.ResourceType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SharingResponse(
        String resourceId,
        ResourceType type,
        String token,
        Long exp
) {
    public static SharingResponse of(String resourceId, ResourceType type, String token, Long exp) {
        return new SharingResponse(resourceId, type, token, exp);
    }
}
