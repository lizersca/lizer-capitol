package lizer.capitol.domain.exceptions;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class InvalidParameterException extends RuntimeException {

  /**
   El enfoque de esto seria evitar crear demasiadas excepciones
   a la vez de tener la posibilidad de hilar muy fino en los aspect
   para el tratamiento de excepciones
   */
  public enum ParameterEnum {PAGE, SIZE, ID, NAME, SPEED}

  private ParameterEnum parameter;

  public InvalidParameterException(ParameterEnum param, String message) {
    super(message);
    parameter = param;
  }
}
