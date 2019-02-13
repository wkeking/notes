package abstractFactory.factory.impl;

import abstractFactory.factory.PizzaIngredientFactory;
import abstractFactory.product.Cheese;
import abstractFactory.product.Clams;
import abstractFactory.product.Dough;
import abstractFactory.product.Sauce;
import abstractFactory.product.impl.FrozenClams;
import abstractFactory.product.impl.MozzarellaCheese;
import abstractFactory.product.impl.PlumTomatoSauce;
import abstractFactory.product.impl.ThickCrustDough;

public class CGPizzaIngredientFactory implements PizzaIngredientFactory {
    @Override
    public Dough createDough() {
        return new ThickCrustDough ();
    }

    @Override
    public Sauce createSauce() {
        return new PlumTomatoSauce ();
    }

    @Override
    public Cheese createCheese() {
        return new MozzarellaCheese ();
    }

    @Override
    public Clams createClams() {
        return new FrozenClams ();
    }
}
