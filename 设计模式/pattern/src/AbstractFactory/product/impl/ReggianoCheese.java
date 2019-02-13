package abstractFactory.product.impl;

import abstractFactory.product.Cheese;

public class ReggianoCheese implements Cheese {
    private String name = "ReggianoCheese";
    @Override
    public String toString() {
        return name;
    }
}
