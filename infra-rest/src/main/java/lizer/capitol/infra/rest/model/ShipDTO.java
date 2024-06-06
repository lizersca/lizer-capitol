package lizer.capitol.infra.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShipDTO implements Serializable {

  public Long id;

  public String name;

  public Float speed;

}
