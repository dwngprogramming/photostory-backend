package space.photostory.exception.exts;

import org.springframework.http.HttpStatus;
import space.photostory.constant.LogLevel;
import space.photostory.exception.ApplicationException;

public class ForbiddenException extends ApplicationException {
    public ForbiddenException(String messageCode, Object... args) {
        super(HttpStatus.FORBIDDEN, messageCode, LogLevel.WARN, args);
    }
}
