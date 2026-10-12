package space.photostory.exception.exts;

import org.springframework.http.HttpStatus;
import space.photostory.constant.LogLevel;
import space.photostory.exception.ApplicationException;

public class BadRequestException extends ApplicationException {
    public BadRequestException(String messageCode, Object... args) {
        super(HttpStatus.BAD_REQUEST, messageCode, LogLevel.WARN, args);
    }
}
