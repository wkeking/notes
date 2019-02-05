package decorator.decorator;

import decorator.component.Beverage;

public class Whip extends CondimentDecorator {
    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }
    @Override
    public String getDescription() {
        return beverage.getDescription () + ",Whip";
    }

    @Override
    public double cost() {
        return 0.15 + beverage.cost ();
    }
}
