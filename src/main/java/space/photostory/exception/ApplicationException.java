package space.photostory.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import space.photostory.constant.LogLevel;

@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
@Getter
public abstract class ApplicationException extends RuntimeException {
    HttpStatus httpStatus;
    String messageCode;
    LogLevel logLevel;
    Object[] args;

    protected ApplicationException(HttpStatus httpStatus, String messageCode, LogLevel logLevel, Object... args) {
        super(messageCode);
        this.httpStatus = httpStatus;
        this.messageCode = messageCode;
        this.logLevel = logLevel;
        this.args = args;
    }
}
