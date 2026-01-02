package space.photostory.mapper.location;

import org.mapstruct.Mapper;
import space.photostory.dto.location.LocationRequest;
import space.photostory.dto.location.LocationResponse;
import space.photostory.entity.location.Location;
import space.photostory.mapper.GlobalMapperConfig;

@Mapper(config = GlobalMapperConfig.class)
public interface LocationMapper {
    LocationResponse toResponse(Location location);
    Location toEntity(LocationRequest request);
}
