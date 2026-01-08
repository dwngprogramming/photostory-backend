package space.photostory.exception.exts;

import org.springframework.http.HttpStatus;
import space.photostory.constant.LogLevel;
import space.photostory.exception.ApplicationException;

public class ConflictException extends ApplicationException {
    public ConflictException(String messageCode, Object... args) {
        super(HttpStatus.CONFLICT, messageCode, LogLevel.WARN, args);
    }
}
