package decorator.decorator;

import decorator.component.Beverage;

public class Soy extends CondimentDecorator {
    public Soy(Beverage beverage) {
        this.beverage = beverage;
    }
    @Override
    public String getDescription() {
        return beverage.getDescription () + ",Soy";
    }

    @Override
    public double cost() {
        return 0.11 + beverage.cost ();
    }
}
