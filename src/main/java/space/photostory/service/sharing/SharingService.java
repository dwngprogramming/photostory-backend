package space.photostory.service.sharing;

import space.photostory.constant.ResourceType;
import space.photostory.constant.ResourceVisibility;
import space.photostory.dto.sharing.SharingResponse;

public interface SharingService {
    SharingResponse verifyAccessPermission(String code);

    SharingResponse verifyAccessPin(String code, String pin);

    void verifySharingToken(String token, ResourceType type);

    SharingResponse createPublicAccess(String resourceId, ResourceType type);

    SharingResponse createPermissionAccess(String resourceId, ResourceType type, ResourceVisibility visibility);
}
