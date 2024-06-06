package lizer.capitol.aspect;

import lizer.capitol.domain.exceptions.InvalidParameterException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ShipAspect {

  @AfterThrowing(pointcut = "execution(* lizer.capitol.infra.rest..*.*(..))", throwing = "ex")
  public void handleInvalidParameter(InvalidParameterException ex) {
    if (InvalidParameterException.ParameterEnum.ID.equals(ex.getParameter())) {
      log.info("\\ (•◡•) / \\ (•◡•) / \\ (•◡•) /");
      log.info(ex.getMessage());
    }
  }
}