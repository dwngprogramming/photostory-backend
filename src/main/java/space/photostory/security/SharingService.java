package space.photostory.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import space.photostory.constant.LogLevel;
import space.photostory.constant.ResourceType;
import space.photostory.dto.sharing.SharingResponse;
import space.photostory.exception.exts.ForbiddenException;
import space.photostory.exception.exts.InternalServerException;
import space.photostory.exception.exts.UnauthorizedException;

import java.text.ParseException;
import java.util.Date;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class SharingService {

    @Value("${sharing-link.key}")
    String sharingLinkKey;

    @Value("${sharing-link.exp-ms}")
    Long sharingLinkExpMs;

    public SharingResponse createSharingAccess(String resourceId) {
        Date expirationTime = new Date(new Date().getTime() + sharingLinkExpMs);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(resourceId)
                .issueTime(new Date())
                .expirationTime(expirationTime)
                .claim("type", "sharing_link")
                .claim("resource", ResourceType.album.name())
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256).build();

        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(sharingLinkKey.getBytes()));
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
        return SharingResponse.of(
                resourceId,
                ResourceType.album,
                jwsObject.serialize(),
                expirationTime.getTime()
        );
    }

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
