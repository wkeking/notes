package AbstractFactory.product.impl;

import AbstractFactory.product.Cheese;

public class ReggianoCheese implements Cheese {
    private String name = "ReggianoCheese";
    @Override
    public String toString() {
        return name;
    }
}
