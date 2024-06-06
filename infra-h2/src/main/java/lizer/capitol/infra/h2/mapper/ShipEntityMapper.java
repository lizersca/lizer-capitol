package lizer.capitol.infra.h2.mapper;

import lizer.capitol.domain.entities.Ship;
import lizer.capitol.infra.h2.entity.ShipEntity;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

@Mapper(
    collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
@Component
public interface ShipEntityMapper {

  Ship toShip(ShipEntity entity);

  ShipEntity toShipEntity(Ship ship);
}
