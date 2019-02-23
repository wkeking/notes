package strategy.strategy;

import java.math.BigDecimal;

public class NewUserStrategy implements Strategy {
    @Override
    public BigDecimal price(BigDecimal price) {
        return price.multiply (new BigDecimal (0.9).setScale (2, BigDecimal.ROUND_HALF_UP));
    }
}
