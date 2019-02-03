package AbstractFactory.store;

import AbstractFactory.commodity.Pizza;
import AbstractFactory.commodity.impl.CheesePizza;
import AbstractFactory.commodity.impl.ClamPizza;
import AbstractFactory.commodity.impl.PepperoniPizza;
import AbstractFactory.commodity.impl.VeggiePizza;
import AbstractFactory.factory.PizzaIngredientFactory;
import AbstractFactory.factory.impl.CGPizzaIngredientFactory;
import AbstractFactory.factory.impl.NYPizzaIngredientFactory;

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
