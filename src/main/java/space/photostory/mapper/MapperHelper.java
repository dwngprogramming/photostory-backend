package space.photostory.mapper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class MapperHelper {

    @Named("deleteIfEmpty")
    public String deleteIfEmpty(String value) {
        if (value != null && value.isEmpty()) {
            return null;
        }
        return value;
    }
}
