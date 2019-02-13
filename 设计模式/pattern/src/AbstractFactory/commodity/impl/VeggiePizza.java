package abstractFactory.commodity.impl;

import abstractFactory.commodity.Pizza;
import abstractFactory.factory.PizzaIngredientFactory;

public class VeggiePizza extends Pizza {
    PizzaIngredientFactory factory;

    public VeggiePizza(PizzaIngredientFactory factory) {
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
