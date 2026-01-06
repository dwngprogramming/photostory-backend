package space.photostory.exception.exts;

import org.springframework.http.HttpStatus;
import space.photostory.constant.LogLevel;
import space.photostory.exception.ApplicationException;

public class UnauthorizedException extends ApplicationException {
    public UnauthorizedException(String messageCode, Object... args) {
        super(HttpStatus.UNAUTHORIZED, messageCode, LogLevel.INFO, args);
    }

    public UnauthorizedException(String messageCode, LogLevel logLevel, Object... args) {
        super(HttpStatus.UNAUTHORIZED, messageCode, logLevel, args);
    }
}
