package abstractFactory.product.impl;

import abstractFactory.product.Cheese;

public class MozzarellaCheese implements Cheese {
    private String name = "MozzarellaCheese";
    @Override
    public String toString() {
        return name;
    }
}
