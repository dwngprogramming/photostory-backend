package space.photostory.exception.exts;

import org.springframework.http.HttpStatus;
import space.photostory.constant.LogLevel;
import space.photostory.exception.ApplicationException;

public class InternalServerException extends ApplicationException {
    public InternalServerException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "server.error", LogLevel.ERROR);
    }
}
