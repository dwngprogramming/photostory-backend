package space.photostory.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import space.photostory.service.i18n.Translator;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        int status,
        String message,
        T data
) {
    public static <T> ApiResponse<T> getSuccess(T data) {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                Translator.toLocale("get.success"),
                data
        );
    }

    public static ApiResponse<Void> createSuccess() {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                Translator.toLocale("create.success"),
                null
        );
    }

    public static <T> ApiResponse<T> createSuccess(T data) {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                Translator.toLocale("create.success"),
                data
        );
    }

    public static ApiResponse<Void> updateSuccess() {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                Translator.toLocale("update.success"),
                null
        );
    }

    public static <T> ApiResponse<T> updateSuccess(T data) {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                Translator.toLocale("update.success"),
                data
        );
    }

    public static ApiResponse<Void> deleteSuccess() {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                Translator.toLocale("delete.success"),
                null
        );
    }

    public static <T> ApiResponse<T> error(int status, String messageCode, Object... args) {
        return new ApiResponse<>(
                status,
                Translator.toLocale(messageCode, args),
                null
        );
    }

    public static <T> ApiResponse<T> error(int status, String messageCode, T data) {
        return new ApiResponse<>(
                status,
                Translator.toLocale(messageCode),
                data
        );
    }
}
