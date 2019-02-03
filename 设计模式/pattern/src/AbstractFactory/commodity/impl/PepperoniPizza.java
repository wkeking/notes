package AbstractFactory.commodity.impl;

import AbstractFactory.commodity.Pizza;
import AbstractFactory.factory.PizzaIngredientFactory;

public class PepperoniPizza extends Pizza {
    PizzaIngredientFactory factory;

    public PepperoniPizza(PizzaIngredientFactory factory) {
        this.factory = factory;
    }
    @Override
    public void prepare() {
        System.out.println("Preparing " + name);
        dough = factory.createDough ();
        sauce = factory.createSauce ();
        cheese = factory.createCheese ();
        clams = factory.createClams ();
    }
}
