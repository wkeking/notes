package abstractFactory.factory;

import abstractFactory.product.Cheese;
import abstractFactory.product.Clams;
import abstractFactory.product.Dough;
import abstractFactory.product.Sauce;

public interface PizzaIngredientFactory {
    Dough createDough();
    Sauce createSauce();
    Cheese createCheese();
    Clams createClams();
}
