package lizer.capitol.infra.h2;

import lizer.capitol.domain.entities.Ship;
import lizer.capitol.domain.repositories.ShipsRepository;
import lizer.capitol.infra.h2.mapper.ShipEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class H2ShipsRepository implements ShipsRepository {

  @Autowired
  private ShipsPagingRepository shipsPagingRepository;

  @Autowired
  private ShipsCrudRepository shipsCrudRepository;

  @Autowired
  private ShipEntityMapper mapper;

  @Override
  public List<Ship> findAll(Integer page, Integer size) {
    var pageable = Pageable.unpaged();
    if (page != null && size != null) {
      pageable = Pageable.ofSize(size).withPage(page);
    }

    return StreamSupport.stream(
            shipsPagingRepository.findAll(pageable).spliterator(),
            false)
        .map(mapper::toShip)
        .toList();
  }

  @Cacheable(value = "ships")
  @Override
  public Optional<Ship> findBy(Long id) {
    return shipsCrudRepository.findById(id).map(mapper::toShip);
  }

  @Override
  public Optional<List<Ship>> findBy(String name) {
    var ships = shipsCrudRepository.findByNameContaining(name);
    return ships.isEmpty()
        ? Optional.empty()
        : Optional.of(
        ships.stream().map(mapper::toShip).toList());
  }

  @Override
  public Long create(Ship ship) {
    return shipsCrudRepository.save(mapper.toShipEntity(ship)).getId();
  }

  @Override
  public void update(Ship ship) {
    shipsCrudRepository.save(mapper.toShipEntity(ship));
  }

  @Override
  public void delete(Long id) {
    shipsCrudRepository.deleteById(id);
  }
}
