package abstractFactory.store;

import abstractFactory.commodity.Pizza;
import abstractFactory.commodity.impl.CheesePizza;
import abstractFactory.commodity.impl.ClamPizza;
import abstractFactory.commodity.impl.PepperoniPizza;
import abstractFactory.commodity.impl.VeggiePizza;
import abstractFactory.factory.PizzaIngredientFactory;
import abstractFactory.factory.impl.CGPizzaIngredientFactory;

public class CGPizzaStore extends PizzaStore {
    @Override
    protected Pizza createPizza(String type) {
        Pizza pizza = null;
        PizzaIngredientFactory factory = new CGPizzaIngredientFactory ();
        switch (type) {
            case "cheese":
                pizza = new CheesePizza (factory);
                pizza.setName ("Chicago Style Cheese Pizza");
                break;
            case "veggie":
                pizza = new VeggiePizza (factory);
                pizza.setName ("Chicago Style Veggie Pizza");
                break;
            case "clam":
                pizza = new ClamPizza (factory);
                pizza.setName ("Chicago Style Clam Pizza");
                break;
            case "pepperoni":
                pizza = new PepperoniPizza (factory);
                pizza.setName ("Chicago Style Pepperoni Pizza");
                break;
        }
        return pizza;
    }
}
