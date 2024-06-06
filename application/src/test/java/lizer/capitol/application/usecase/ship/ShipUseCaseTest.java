package lizer.capitol.application.usecase.ship;

import lizer.capitol.domain.entities.Ship;
import lizer.capitol.domain.repositories.ShipsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShipUseCaseTest {

  private final Long ID = 1L;

  private final String NAME = "ship";

  private final Float SPEED = 1234.5F;

  private final Ship SHIP = Ship.builder()
      .name(NAME)
      .speed(SPEED)
      .build();

  @Mock
  private ShipsRepository shipRepository;

  @InjectMocks
  private ShipUseCase useCase;

  @Test
  void it_should_return_all_ships() {
    var expected = List.of(SHIP);
    var pageNumber = 0;
    var size = 1;
    when(shipRepository.findAll(pageNumber, size))
        .thenReturn(expected);

    var ship = useCase.getAllShips(pageNumber, size);

    assertThat(ship).isEqualTo(expected);
  }

  @Test
  void it_should_return_a_ship_id() {
    var expected = Optional.of(SHIP);
    when(shipRepository.findBy(ID))
        .thenReturn(expected);

    var ship = useCase.getShipBy(ID);

    assertThat(ship).isEqualTo(expected);
  }

  @Test
  void it_should_return_a_ship_by_name() {
    var expected = Optional.of(List.of(SHIP));
    when(shipRepository.findBy(NAME))
        .thenReturn(expected);

    var ships = useCase.getShipsBy(NAME);

    assertThat(ships).isEqualTo(expected);
  }

  @Test
  void it_should_create_a_ship() {
    when(shipRepository.create(SHIP)).thenReturn(ID);

    var id = useCase.createShip(SHIP);

    assertThat(id).isEqualTo(ID);
  }

  @Test
  void it_should_update_a_ship() {
    useCase.updateShip(SHIP);

    verify(shipRepository).update(SHIP);
  }

  @Test
  void it_should_delete_a_ship() {
    useCase.deleteShip(ID);

    verify(shipRepository).delete(ID);
  }
}