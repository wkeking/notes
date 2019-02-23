package strategy.strategy;

import java.math.BigDecimal;

public class OldUserStrategy implements Strategy {
    @Override
    public BigDecimal price(BigDecimal price) {
        return price.multiply (new BigDecimal (0.8).setScale (2, BigDecimal.ROUND_HALF_UP));
    }
}
