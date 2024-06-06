package lizer.capitol.domain.repositories;

import lizer.capitol.domain.entities.Ship;

import java.util.List;
import java.util.Optional;

public interface ShipsRepository {

  List<Ship> findAll(Integer page, Integer size);

  Optional<Ship> findBy(Long id);

  Optional<List<Ship>> findBy(String name);

  Long create(Ship ship);

  void update(Ship ship);

  void delete(Long id);
}
