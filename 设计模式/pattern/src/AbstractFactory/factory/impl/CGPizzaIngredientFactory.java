package AbstractFactory.factory.impl;

import AbstractFactory.factory.PizzaIngredientFactory;
import AbstractFactory.product.Cheese;
import AbstractFactory.product.Clams;
import AbstractFactory.product.Dough;
import AbstractFactory.product.Sauce;
import AbstractFactory.product.impl.FrozenClams;
import AbstractFactory.product.impl.MozzarellaCheese;
import AbstractFactory.product.impl.PlumTomatoSauce;
import AbstractFactory.product.impl.ThickCrustDough;

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
