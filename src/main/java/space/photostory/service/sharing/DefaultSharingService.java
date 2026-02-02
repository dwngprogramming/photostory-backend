package space.photostory.service.sharing;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import space.photostory.constant.ResourceVisibility;
import space.photostory.constant.LogLevel;
import space.photostory.constant.ResourceType;
import space.photostory.dto.sharing.SharingResponse;
import space.photostory.entity.album.Album;
import space.photostory.exception.exts.ForbiddenException;
import space.photostory.exception.exts.InternalServerException;
import space.photostory.exception.exts.NotFoundException;
import space.photostory.exception.exts.UnauthorizedException;
import space.photostory.repository.album.AlbumRepository;

import java.text.ParseException;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class DefaultSharingService implements SharingService {
    final AlbumRepository albumRepository;
    final PasswordEncoder encoder;

    @Value("${sharing-link.key}")
    String sharingLinkKey;

    @Value("${sharing-link.exp-ms}")
    Long sharingLinkExpMs;

    @Override
    public SharingResponse verifyAccessPermission(String code) {
        Album album = albumRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("unwrap.code.not_found", code));

        ResourceVisibility visibility = album.getVisibility();
        String publicAccessKey = album.getPublicAccessKey();

        return switch (visibility) {
            case PUBLIC -> createPublicAccess(publicAccessKey, ResourceType.album);
            case PRIVATE -> SharingResponse.needVerifyPin();
            case RESTRICTED -> throw new ForbiddenException("sharing.access_reject", code);
        };
    }

    @Override
    public SharingResponse verifyAccessPin(String code, String pin) {
        String albumId = albumRepository.findIdByCode(code)
                .orElseThrow(() -> new NotFoundException("unwrap.code.not_found", code));

        String encodedPin = encoder.encode(pin);

        boolean isValidPin = albumRepository.existsByCode(code) && encoder.matches(pin, encodedPin);

        if (!isValidPin) {
            throw new UnauthorizedException("unwrap.pin.invalid");
        }
        return createPermissionAccess(albumId, ResourceType.album, ResourceVisibility.PRIVATE);
    }

    @Override
    public SharingResponse createPublicAccess(String publicAccessKey, ResourceType type) {
        return SharingResponse.publicSharing(publicAccessKey, type);
    }

    @Override
    public SharingResponse createPermissionAccess(String resourceId, ResourceType type, ResourceVisibility visibility) {
        Date expirationTime = new Date(new Date().getTime() + sharingLinkExpMs);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(resourceId)
                .issueTime(new Date())
                .expirationTime(expirationTime)
                .claim("type", "sharing_link")
                .claim("resource", type.name())
                .claim("visibility", visibility.name())
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256).build();

        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(sharingLinkKey.getBytes()));
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
        return SharingResponse.permissionSharing(
                resourceId,
                type,
                jwsObject.serialize(),
                expirationTime.getTime()
        );
    }

    @Override
    public void verifySharingToken(String token, ResourceType expectedType) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            JWSVerifier verifier = new MACVerifier(sharingLinkKey.getBytes());

            if (!jwsObject.verify(verifier)) {
                throw new UnauthorizedException("signature.invalid", LogLevel.WARN);
            }

            JWTClaimsSet claims = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
            ResourceType type = ResourceType.valueOf(claims.getStringClaim("resource"));
            if (type != expectedType) {
                throw new ForbiddenException("sharing.invalid");
            }

            Date referenceTime = new Date();
            Date expirationTime = claims.getExpirationTime();

            if (expirationTime != null && referenceTime.after(expirationTime)) {
                throw new UnauthorizedException("sharing.expired");
            }
        } catch (JOSEException e) {
            throw new UnauthorizedException("sharing.invalid");
        } catch (ParseException e) {
            throw new InternalServerException(e);
        }
    }
}
