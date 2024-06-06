package lizer.capitol.boot;

import lizer.capitol.domain.exceptions.InvalidParameterException;
import lizer.capitol.infra.rest.ShipsController;
import lizer.capitol.infra.rest.model.ShipDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class})
@SpringBootTest
public class ShipsControllerIT {

  /**
   * Por simplicidad, los puntos requeridos en la prueba tecnica son testeados
   * de manera centralizada en esta clase
   */

  private final Long ID = 1L;

  private final Long ID_6 = 6L;

  private final Long ID_2 = 2L;

  private final Long ID_CREATE = 7L;

  private final Long ID_UPDATE = ID;

  private final Long ID_DELETE = ID;

  private final Long ID_NEGATIVE = -1L;

  private final Long NON_EXISTING_ID = 99L;

  private final String NAME = "UNSC Infinity";

  private final String NAME_2 = "UNSC Infinity 2";

  private final String NAME_3 = "Battlestar Galactica";

  private final String NAME_SIMILAR = "Battlestar";

  private final String NAME_NON_EXISTING = "PEPE";

  private final String NAME_CREATE = "ship_created";

  private final String NAME_UPDATE = "ship_updated";

  private final Float SPEED = 1000.9F;

  private final Float SPEED_2 = 1000.8F;

  private final Float SPEED_3 = 1234.8F;

  private final Float SPEED_CREATE = 666.666F;

  private final Float SPEED_UPDATE = 666.666F;

  private final ShipDTO SHIP = ShipDTO.builder()
      .id(ID)
      .name(NAME)
      .speed(SPEED)
      .build();

  private final ShipDTO SHIP_2 = ShipDTO.builder()
      .id(ID_6)
      .name(NAME_2)
      .speed(SPEED_2)
      .build();

  private final ShipDTO SHIP_3 = ShipDTO.builder()
      .id(ID_2)
      .name(NAME_3)
      .speed(SPEED_3)
      .build();

  private final ShipDTO SHIP_CREATE = ShipDTO.builder()
      .id(ID_CREATE)
      .name(NAME_CREATE)
      .speed(SPEED_CREATE)
      .build();

  private final ShipDTO SHIP_UPDATE = ShipDTO.builder()
      .id(ID_UPDATE)
      .name(NAME_UPDATE)
      .speed(SPEED_UPDATE)
      .build();

  private final URI URI_1 = URI.create("http://localhost:8090/capitol/ships/" + ID_CREATE);

  @Autowired
  private ShipsController controller;

  @Test
  void it_should_return_ships() {
    var size = 2;
    var page = 0;
    var expected = ResponseEntity.ok(List.of(SHIP, SHIP_3));

    var result = controller.getAllShips(page, size);

    assertThat(result).isEqualTo(expected);
  }

  @Test
  void it_should_return_a_ship_by_id() {
    var expected = ResponseEntity.ok(SHIP);

    var result = controller.getShipBy(ID);

    assertThat(result).isEqualTo(expected);
  }

  @Test
  void it_should_not_return_a_ship_by_id() {
    var expected = ResponseEntity.notFound().build();

    var result = controller.getShipBy(NON_EXISTING_ID);

    assertThat(result).isEqualTo(expected);
  }

  @Test
  void it_should_return_a_ship_by_name() {
    var expected = ResponseEntity.ok(List.of(SHIP, SHIP_2));

    var result = controller.getShipsBy(NAME);

    assertThat(result).isEqualTo(expected);
  }

  @Test
  void it_should_return_a_ship_by_similar_name() {
    var expected = ResponseEntity.ok(List.of(SHIP_3));

    var result = controller.getShipsBy(NAME_SIMILAR);

    assertThat(result).isEqualTo(expected);
  }

  @Test
  void it_should_not_return_a_ship_by_name() {
    var expected = ResponseEntity.notFound().build();

    var result = controller.getShipsBy(NAME_NON_EXISTING);

    assertThat(result).isEqualTo(expected);
  }

  @Test
  @DirtiesContext
  void it_should_create_a_ship() {
    var expected = ResponseEntity.created(URI_1).build();

    var result = controller.createShip(SHIP_CREATE);

    assertThat(result).isEqualTo(expected);
  }

  @Test
  @DirtiesContext
  void it_should_update_a_ship() {
    var expected = ResponseEntity.noContent().build();

    var result = controller.updateShip(SHIP_UPDATE);

    assertThat(result).isEqualTo(expected);
  }

  @Test
  @DirtiesContext
  void it_should_delete_a_ship() {
    var expected = ResponseEntity.noContent().build();

    var result = controller.deleteShip(ID_DELETE);

    assertThat(result).isEqualTo(expected);
  }

  @Test
  void it_should_return_an_error() {
    assertThatThrownBy(() -> controller.getShipBy(ID_NEGATIVE))
        .isInstanceOf(InvalidParameterException.class)
        .extracting("parameter").isEqualTo(InvalidParameterException.ParameterEnum.ID);
  }
}
