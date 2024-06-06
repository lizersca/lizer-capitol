package lizer.capitol.infra.h2;

import lizer.capitol.infra.h2.entity.ShipEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipsPagingRepository extends PagingAndSortingRepository<ShipEntity, Long> {
}
