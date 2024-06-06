package lizer.capitol.infra.rest.errorhandling;

import lizer.capitol.domain.exceptions.InvalidParameterException;
import lombok.AllArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.util.Objects.nonNull;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@AllArgsConstructor
public class InvalidParameterErrorHandler {

  private final ErrorResponseFactory errorResponseFactory;

  @ExceptionHandler(value = {InvalidParameterException.class})
  public ResponseEntity<ErrorMessageDTO> handle(InvalidParameterException exception) {
    var values = nonNull(exception.getMessage()) ? new Object[]{exception.getMessage()} : new Object[]{};
    return errorResponseFactory.create(HttpStatus.BAD_REQUEST, values);
  }
}
