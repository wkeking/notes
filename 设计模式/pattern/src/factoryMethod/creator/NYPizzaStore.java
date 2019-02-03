package factoryMethod.creator;

import factoryMethod.product.*;

public class NYPizzaStore extends PizzaStore {
    @Override
    protected Pizza createPizza(String type) {
        Pizza pizza = null;
        switch (type) {
            case "cheese":
                pizza = new NYStyleCheesePizza ();
                break;
            case "veggie":
                pizza = new NYStyleVeggiePizza ();
                break;
            case "clam":
                pizza = new NYStyleClamPizza ();
                break;
            case "pepperoni":
                pizza = new NYStylePepperoniPizza ();
                break;
            default:
                break;
        }
        return pizza;
    }
}
