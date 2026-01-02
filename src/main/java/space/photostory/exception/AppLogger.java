package space.photostory.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import space.photostory.constant.LogLevel;
import space.photostory.service.i18n.Translator;

import java.util.Map;

@Component
@Slf4j
public class AppLogger {

    public void log(ApplicationException ex, String path) {
        String exceptionName = ex.getClass().getSimpleName();
        String sourceClassName = getSourceClassName(ex);
        String messageDetail = Translator.forLogging(ex.getMessageCode(), ex.getArgs());

        // 4. Format log: [Exception] [SourceClass] Message...
        String logContent = String.format("[%s] [from %s] %s (%s) - Path: %s",
                exceptionName, sourceClassName, messageDetail, ex.getMessageCode(), path);

        // 5. Ghi log
        switch (ex.getLogLevel()) {
            case ERROR -> log.error("App Error: {}", logContent, ex);
            case WARN  -> log.warn("App Warning: {}", logContent);
            case INFO  -> log.info("App Info: {}", logContent);
            case DEBUG -> log.debug("App Debug: {}", logContent);
        }
    }

    // Log cho Exception thường nhưng muốn chỉ định Level (VD: 404, 400)
    public void log(Exception ex, String path, LogLevel level) {
        String message = ex.getMessage();
        switch (level) {
            case ERROR -> log.error("System Error: {} - Path: {}", message, path, ex);
            case WARN  -> log.warn("Warning: {} - Path: {}", message, path);
            case INFO  -> log.info("Info: {} - Path: {}", message, path);
            case DEBUG -> log.debug("Debug: {} - Path: {}", message, path);
        }
    }

    // Log đặc biệt cho lỗi Validation (In ra danh sách field lỗi)
    public void logValidation(Map<String, String> errors, String path) {
        log.info("Validation Failed at {}: {}", path, errors);
    }

    public void log(Exception ex, String path) {
        log.error("System Error: {} - Path: {}", ex.getMessage(), path, ex);
    }

    private static String getSourceClassName(ApplicationException ex) {
        String sourceClassName = "Unknown";
        StackTraceElement[] stackTrace = ex.getStackTrace();

        if (stackTrace != null && stackTrace.length > 0) {
            // Lấy phần tử đầu tiên trong stack trace (nơi throw)
            String fullClassName = stackTrace[0].getClassName();
            // Cắt chuỗi để lấy tên class ngắn gọn (bỏ package)
            sourceClassName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);

            // Nếu bạn muốn chi tiết hơn (Class + Method + Dòng code):
            // sourceClassName = sourceClassName + "." + stackTrace[0].getMethodName() + ":" + stackTrace[0].getLineNumber();
        }
        return sourceClassName;
    }
}
