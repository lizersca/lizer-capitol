package lizer.capitol.infra.rest.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ErrorResponseFactory {

  public ResponseEntity<ErrorMessageDTO> create(HttpStatus httpStatus, Object[] values) {
    return ResponseEntity
        .status(httpStatus)
        .body(new ErrorMessageDTO(values));
  }
}
