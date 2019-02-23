package strategy;

import java.math.BigDecimal;

public class Client {
    public static void main(String[] args) {
        StrategyContext user = new StrategyContext ("老用户");
        BigDecimal price = user.getPrice (new BigDecimal (100.0));
        System.out.println(price);
    }
}
