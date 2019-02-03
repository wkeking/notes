package AbstractFactory.product.impl;

import AbstractFactory.product.Cheese;

public class MozzarellaCheese implements Cheese {
    private String name = "MozzarellaCheese";
    @Override
    public String toString() {
        return name;
    }
}
