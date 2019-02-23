package strategy.strategy;

import java.math.BigDecimal;

public interface Strategy {
    BigDecimal price(BigDecimal price);
}
