package space.photostory.exception;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import space.photostory.constant.LogLevel;
import space.photostory.dto.ApiResponse;

import java.util.HashMap;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    MessageSource messageSource;
    AppLogger appLogger;

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiResponse<Void>> handleApplicationException(ApplicationException ex, WebRequest request) {
        appLogger.log(ex, request.getDescription(false));
        return ResponseEntity.status(ex.getHttpStatus())
                .body(ApiResponse.error(ex.getHttpStatus().value(), ex.getMessageCode(), ex.getArgs()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnwantedException(Exception ex, WebRequest request) {
        appLogger.log(ex, request.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "server.error"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage;
            try {
                errorMessage = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            } catch (NoSuchMessageException e) {
                errorMessage = error.getDefaultMessage();
            }
            errors.put(fieldName, errorMessage);
        });

        appLogger.logValidation(errors, request.getDescription(false));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "request.validation_error", errors));
    }

    // Bad Request (TypeMismatch, BodyInvalid...) -> Log INFO
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ApiResponse<Void>> handleBadRequestException(Exception ex, WebRequest request) {
        // Log WARN thông qua AppLogger
        appLogger.log(ex, request.getDescription(false), LogLevel.INFO);

        String messageCode = (ex instanceof MethodArgumentTypeMismatchException)
                ? "request.type_mismatch"
                : "request.body_invalid";

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), messageCode));
    }

//    // Username Not Found -> Log INFO
//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<ApiResponse<Void>> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
//        appLogger.log(ex, request.getDescription(false), LogLevel.INFO);
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "user.not_found"));
//    }

    // Resource Not Found (404) -> Log INFO
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoResourceFoundException(NoResourceFoundException ex, WebRequest request) {
        appLogger.log(ex, request.getDescription(false), LogLevel.INFO);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "resource.not_found"));
    }

//    // Auth Exception -> Log WARN
//    @ExceptionHandler({JwtValidationException.class, MissingRequestCookieException.class})
//    public ResponseEntity<ApiResponse<Void>> handleAuthException(Exception ex, WebRequest request) {
//        appLogger.log(ex, request.getDescription(false), LogLevel.WARN);
//
//        String messageCode = (ex instanceof JwtValidationException)
//                ? "auth.token_invalid"
//                : "auth.cookie_missing";
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                .body(ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), messageCode));
//    }
}
