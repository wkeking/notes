package AbstractFactory.factory;

import AbstractFactory.product.Cheese;
import AbstractFactory.product.Clams;
import AbstractFactory.product.Dough;
import AbstractFactory.product.Sauce;

public interface PizzaIngredientFactory {
    Dough createDough();
    Sauce createSauce();
    Cheese createCheese();
    Clams createClams();
}
