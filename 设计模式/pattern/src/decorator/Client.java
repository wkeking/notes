package decorator;

import decorator.component.Beverage;
import decorator.component.Espresso;
import decorator.component.HouseBlend;
import decorator.decorator.Milk;
import decorator.decorator.Mocha;

public class Client {
    public static void main(String args[]) {
        Beverage beverage = new HouseBlend ();
        System.out.println(beverage.getDescription () + " $" + beverage.cost ());

        Beverage beverage1 = new Espresso ();
        beverage1 = new Milk (beverage1);
        beverage1 = new Mocha (beverage1);
        System.out.println(beverage1.getDescription () + " $" + beverage1.cost ());
    }
}
