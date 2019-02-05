package decorator.decorator;

import decorator.component.Beverage;

public class Mocha extends CondimentDecorator {
    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }
    @Override
    public String getDescription() {
        return beverage.getDescription () + ",Mocha";
    }

    @Override
    public double cost() {
        return 0.2 + beverage.cost ();
    }
}
