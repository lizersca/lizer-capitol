package lizer.capitol.infra.h2;

import lizer.capitol.infra.h2.entity.ShipEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipsCrudRepository extends CrudRepository<ShipEntity, Long> {

  List<ShipEntity> findByNameContaining(String name);
}
