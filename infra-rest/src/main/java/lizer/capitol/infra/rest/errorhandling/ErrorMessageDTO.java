package lizer.capitol.infra.rest.errorhandling;

import lombok.Getter;

import java.util.Arrays;

import static java.util.Objects.nonNull;

@Getter
public class ErrorMessageDTO {

  String description;

  public ErrorMessageDTO(Object[] values) {
    description = "";

    if (nonNull(values)) {
      Arrays.stream(values).forEach(value ->
          description+= String.valueOf(value));
    }
  }
}
