package factoryMethod.creator;

import factoryMethod.product.*;

public class CGPizzaStore extends PizzaStore {
    @Override
    protected Pizza createPizza(String type) {
        Pizza pizza = null;
        switch (type) {
            case "cheese":
                pizza = new CGStyleCheesePizza ();
                break;
            case "veggie":
                pizza = new CGStyleCheesePizza ();
                break;
            case "clam":
                pizza = new CGStyleClamPizza ();
                break;
            case "pepperoni":
                pizza = new CGStylePepperoniPizza ();
                break;
            default:
                break;
        }
        return pizza;
    }
}
