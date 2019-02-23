package strategy;

import strategy.strategy.NewUserStrategy;
import strategy.strategy.OldUserStrategy;
import strategy.strategy.Strategy;

import java.math.BigDecimal;

public class StrategyContext {
    private String OLD_USER = "老用户";
    private String NEW_USER = "新用户";
    private Strategy strategy;
    public StrategyContext(String user) {
        if (OLD_USER.equals (user))
            strategy = new OldUserStrategy ();
        else
            strategy = new NewUserStrategy ();
    }
    public BigDecimal getPrice(BigDecimal price) {
        return strategy.price (price);
    }
}
