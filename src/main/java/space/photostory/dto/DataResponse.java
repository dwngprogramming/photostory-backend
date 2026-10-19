package space.photostory.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import space.photostory.service.i18n.Translator;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DataResponse<T>(
        @Schema(description = "HTTP status code of the response.")
        int status,

        @Schema(description = "Message providing additional information about the response.")
        String message,

        @Schema(description = "Real data returned from the API. Can be null if no data is available.")
        T data
) {
    public static <T> DataResponse<T> getSuccess(T data) {
        return new DataResponse<>(
                HttpStatus.OK.value(),
                Translator.toLocale("get.success"),
                data
        );
    }

    public static DataResponse<Void> createSuccess() {
        return new DataResponse<>(
                HttpStatus.OK.value(),
                Translator.toLocale("create.success"),
                null
        );
    }

    public static <T> DataResponse<T> createSuccess(T data) {
        return new DataResponse<>(
                HttpStatus.OK.value(),
                Translator.toLocale("create.success"),
                data
        );
    }

    public static DataResponse<Void> updateSuccess() {
        return new DataResponse<>(
                HttpStatus.OK.value(),
                Translator.toLocale("update.success"),
                null
        );
    }

    public static <T> DataResponse<T> updateSuccess(T data) {
        return new DataResponse<>(
                HttpStatus.OK.value(),
                Translator.toLocale("update.success"),
                data
        );
    }

    public static DataResponse<Void> deleteSuccess() {
        return new DataResponse<>(
                HttpStatus.OK.value(),
                Translator.toLocale("delete.success"),
                null
        );
    }

    public static <T> DataResponse<T> error(int status, String messageCode, Object... args) {
        return new DataResponse<>(
                status,
                Translator.toLocale(messageCode, args),
                null
        );
    }

    public static <T> DataResponse<T> error(int status, String messageCode, T data) {
        return new DataResponse<>(
                status,
                Translator.toLocale(messageCode),
                data
        );
    }
}
