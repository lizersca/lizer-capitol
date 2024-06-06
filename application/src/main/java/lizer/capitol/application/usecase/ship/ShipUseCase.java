package lizer.capitol.application.usecase.ship;

import lizer.capitol.domain.entities.Ship;
import lizer.capitol.domain.repositories.ShipsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShipUseCase {

  private ShipsRepository shipsRepository;

  public List<Ship> getAllShips(Integer page, Integer size) {
    return shipsRepository.findAll(page, size);
  }

  public Optional<Ship> getShipBy(Long id) {
    return shipsRepository.findBy(id);
  }

  public Optional<List<Ship>> getShipsBy(String name) {
    return shipsRepository.findBy(name);
  }

  public Long createShip(Ship ship) {
    return shipsRepository.create(ship);
  }

  public void updateShip(Ship ship) {
    shipsRepository.update(ship);
  }

  public void deleteShip(Long id) {
    shipsRepository.delete(id);
  }
}
