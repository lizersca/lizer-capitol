package lizer.capitol.infra.rest;


import jakarta.websocket.server.PathParam;
import lizer.capitol.application.usecase.ship.ShipUseCase;
import lizer.capitol.domain.exceptions.InvalidParameterException;
import lizer.capitol.domain.entities.Ship;
import lizer.capitol.infra.rest.mapper.ShipMapper;
import lizer.capitol.infra.rest.model.ShipDTO;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Parte de documentacion de la API:
 * Llevo haciendola mediante openapi durante mucho tiempo y no tenia claro la manera exacta requerida
 */

@RestController
@RequestMapping("ships")
@AllArgsConstructor
public class ShipsController {

  private final String SHIPS_URI = "http://localhost:8090/capitol/ships/";

  private final String INVALID_VALUE_MESSAGE = "parameter contains invalid value";

  private ShipUseCase shipUseCase;

  private ShipMapper mapper;

  /**
   * GET /ships : Get all ships
   *
   * @param page (non-required)
   * @param size (non-required)
   * @return Ok (status code 200)
   * or Request contains invalid parameter (status code 400)
   */
  @GetMapping(
      produces = {"application/json"}
  )
  public ResponseEntity<List<ShipDTO>> getAllShips(
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "size", required = false) Integer size
  ) {
    paramsCheck(page, size);
    return ResponseEntity.ok(
        shipUseCase.getAllShips(page, size).stream()
            .map(mapper::toShipDTO)
            .collect(Collectors.toList()));
  }

  /**
   * GET /ships : Get a ship by its Identifier
   *
   * @param id (required)
   * @return Ok (status code 200)
   * or Request contains invalid parameter (status code 400)
   */
  @GetMapping(
      path = {"/{id}"},
      produces = {"application/json"}
  )
  public ResponseEntity<ShipDTO> getShipBy(
      @NotNull @PathVariable(value = "id") Long id) {
    paramsCheck(id);
    Optional<Ship> ship = shipUseCase.getShipBy(id);
    return ship.map(value -> ResponseEntity.ok(mapper.toShipDTO(value)))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * GET /ships : Get a list of ships with matching or similar name
   *
   * @param name (required)
   * @return Ok (status code 200)
   * or Request contains invalid parameter (status code 400)
   */
  @GetMapping(
      params = "name",
      produces = {"application/json"}
  )
  public ResponseEntity<List<ShipDTO>> getShipsBy(
      @NotNull @RequestParam(value = "name") String name) {
    Optional<List<Ship>> optList = shipUseCase.getShipsBy(name);
    return optList.map(ships ->
            ResponseEntity.ok(ships.stream().map(mapper::toShipDTO).toList()))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * POST /ships : Create a ship
   *
   * @param shipDTO (required)
   * @return Ok (status code 201)
   */
  @PostMapping
  public ResponseEntity<ShipDTO> createShip(@NotNull @RequestBody ShipDTO shipDTO) {
    var shipId = shipUseCase.createShip(mapper.toShip(shipDTO));
    return ResponseEntity.created(URI.create(SHIPS_URI + shipId)).build();
  }

  /**
   * PUT /ships : Update a ship
   *
   * @param shipDTO (required)
   * @return No Content (status code 204)
   */
  @PutMapping
  public ResponseEntity<ShipDTO> updateShip(@NotNull @RequestBody ShipDTO shipDTO) {
    shipUseCase.updateShip(mapper.toShip(shipDTO));
    return ResponseEntity.noContent().build();
  }

  /**
   * DELETE /ships : Delete a ship
   *
   * @param id (required)
   * @return No Content (status code 204)
   */
  @DeleteMapping(
      path = "/{id}"
  )
  public ResponseEntity<ShipDTO> deleteShip(@NotNull @PathParam(value = "id") Long id) {
    paramsCheck(id);
    shipUseCase.deleteShip(id);
    return ResponseEntity.noContent().build();
  }

  private void paramsCheck(Integer page, Integer size) {
    if (page != null && page < 0) {
      throw new InvalidParameterException(
          InvalidParameterException.ParameterEnum.PAGE, "PAGE: " + INVALID_VALUE_MESSAGE);
    }
    if (size != null && size < 1) {
      throw new InvalidParameterException(
          InvalidParameterException.ParameterEnum.SIZE, "SIZE: " + INVALID_VALUE_MESSAGE);
    }
  }

  private void paramsCheck(Long id) {
    if (id < 0) {
      throw new InvalidParameterException(
          InvalidParameterException.ParameterEnum.ID, "ID: " + INVALID_VALUE_MESSAGE);
    }
  }
}
