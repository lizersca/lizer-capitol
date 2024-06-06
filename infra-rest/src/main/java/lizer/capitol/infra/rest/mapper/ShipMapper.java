package lizer.capitol.infra.rest.mapper;

import lizer.capitol.domain.entities.Ship;
import lizer.capitol.infra.rest.model.ShipDTO;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(
    collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
@Component
public interface ShipMapper {

  Ship toShip(ShipDTO shipDTO);

  ShipDTO toShipDTO(Ship ship);
}
