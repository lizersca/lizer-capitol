package lizer.capitol.infra.rest;

import lizer.capitol.application.usecase.ship.ShipUseCase;
import lizer.capitol.domain.entities.Ship;
import lizer.capitol.infra.rest.mapper.ShipMapper;
import lizer.capitol.infra.rest.mapper.ShipMapperImpl;
import lizer.capitol.infra.rest.model.ShipDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class ShipsControllerTest {

  private final String URI_CREATED = "http://localhost:8090/capitol/ships/1";

  private final Long ID = 1L;

  private final String NAME = "ship";

  private final String INVALID_NAME = "PEPE";

  private final Float SPEED = 999F;

  private final Ship SHIP = Ship.builder()
      .id(ID)
      .name(NAME)
      .speed(SPEED)
      .build();

  private final ShipDTO SHIP_DTO = ShipDTO.builder()
      .id(ID)
      .name(NAME)
      .speed(SPEED)
      .build();

  private static final ResponseEntity<ShipDTO> NOT_FOUND_RESPONSE =
      ResponseEntity.status(NOT_FOUND).build();

  @Mock
  private ShipUseCase useCase;

  @Spy
  private final ShipMapper mapper = new ShipMapperImpl();

  @InjectMocks
  private ShipsController controller;

  @Test
  void it_should_return_all_ships() {
    var page = 0;
    var size = 1;
    when(useCase.getAllShips(page, size)).thenReturn(List.of(SHIP));

    var ships = controller.getAllShips(0, 1);

    var expected = ResponseEntity.ok(List.of(SHIP_DTO));
    assertThat(expected).isEqualTo(ships);
  }

  @Test
  void it_should_return_a_ship_by_id() {
    when(useCase.getShipBy(ID)).thenReturn(Optional.of(SHIP));

    var ships = controller.getShipBy(ID);

    var expected = ResponseEntity.ok(SHIP_DTO);
    assertThat(expected).isEqualTo(ships);
  }

  @Test
  void it_should_return_a_ship_by_name() {
    when(useCase.getShipsBy(NAME)).thenReturn(Optional.of(List.of(SHIP)));

    var ships = controller.getShipsBy(NAME);

    var expected = ResponseEntity.ok(List.of(SHIP_DTO));
    assertThat(expected).isEqualTo(ships);
  }

  @Test
  void it_should_return_create_a_ship() {
    when(useCase.createShip(SHIP)).thenReturn(ID);

    var ships = controller.createShip(SHIP_DTO);

    var expected = ResponseEntity.created(URI.create(URI_CREATED)).build();
    assertThat(expected).isEqualTo(ships);
  }

  @Test
  void it_should_return_update_a_ship() {
    var response = controller.updateShip(SHIP_DTO);

    assertThat(response).isEqualTo(ResponseEntity.noContent().build());
    verify(useCase).updateShip(SHIP);
  }

  @Test
  void it_should_return_delete_a_ship() {
    var response = controller.deleteShip(ID);

    assertThat(response).isEqualTo(ResponseEntity.noContent().build());
    verify(useCase).deleteShip(ID);
  }
}