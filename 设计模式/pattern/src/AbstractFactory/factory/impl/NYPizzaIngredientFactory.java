package abstractFactory.factory.impl;

import abstractFactory.factory.PizzaIngredientFactory;
import abstractFactory.product.Cheese;
import abstractFactory.product.Clams;
import abstractFactory.product.Dough;
import abstractFactory.product.Sauce;
import abstractFactory.product.impl.FreshClams;
import abstractFactory.product.impl.MarinaraSauce;
import abstractFactory.product.impl.ReggianoCheese;
import abstractFactory.product.impl.ThinCrustDough;

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
