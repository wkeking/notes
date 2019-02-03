package AbstractFactory.factory.impl;

import AbstractFactory.factory.PizzaIngredientFactory;
import AbstractFactory.product.Cheese;
import AbstractFactory.product.Clams;
import AbstractFactory.product.Dough;
import AbstractFactory.product.Sauce;
import AbstractFactory.product.impl.FreshClams;
import AbstractFactory.product.impl.MarinaraSauce;
import AbstractFactory.product.impl.ReggianoCheese;
import AbstractFactory.product.impl.ThinCrustDough;

public class NYPizzaIngredientFactory implements PizzaIngredientFactory {
    @Override
    public Dough createDough() {
        return new ThinCrustDough ();
    }

    @Override
    public Sauce createSauce() {
        return new MarinaraSauce ();
    }

    @Override
    public Cheese createCheese() {
        return new ReggianoCheese ();
    }

    @Override
    public Clams createClams() {
        return new FreshClams ();
    }
}
