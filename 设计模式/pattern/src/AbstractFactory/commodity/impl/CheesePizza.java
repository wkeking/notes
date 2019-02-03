package AbstractFactory.commodity.impl;

import AbstractFactory.commodity.Pizza;
import AbstractFactory.factory.PizzaIngredientFactory;

public class CheesePizza extends Pizza {
    PizzaIngredientFactory factory;

    public CheesePizza (PizzaIngredientFactory factory) {
        this.factory = factory;
    }
    @Override
    public void prepare() {
        System.out.println("Preparing " + name);
        dough = factory.createDough ();
        sauce = factory.createSauce ();
        cheese = factory.createCheese ();
    }
}
