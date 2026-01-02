package space.photostory.exception.exts;

import org.springframework.http.HttpStatus;
import space.photostory.constant.LogLevel;
import space.photostory.exception.ApplicationException;

public class NotFoundException extends ApplicationException {
    public NotFoundException(String messageCode, Object... args) {
        super(HttpStatus.NOT_FOUND, messageCode, LogLevel.INFO, args);
    }

    public NotFoundException(String messageCode, LogLevel logLevel, Object... args) {
        super(HttpStatus.NOT_FOUND, messageCode, logLevel, args);
    }
}
