package space.photostory.dto.sharing;

import com.fasterxml.jackson.annotation.JsonInclude;
import space.photostory.constant.ResourceType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SharingResponse(
        Boolean requiredPin,
        String publicSharingKey,
        String resourceId,
        ResourceType type,
        String token,
        Long exp
) {
    public static SharingResponse publicSharing(String publicSharingKey, ResourceType type) {
        return new SharingResponse(null, publicSharingKey, null, type, null, null);
    }

    public static SharingResponse permissionSharing(String resourceId, ResourceType type, String token, Long exp) {
        return new SharingResponse(null, null, resourceId, type, token, exp);
    }

    public static SharingResponse needVerifyPin() {
        return new SharingResponse(true, null, null, null, null, null);
    }
}
