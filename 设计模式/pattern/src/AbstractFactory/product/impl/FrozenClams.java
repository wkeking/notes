package abstractFactory.product.impl;

import abstractFactory.product.Clams;

public class FrozenClams implements Clams {
    private String name = "FrozenClams";
    @Override
    public String toString() {
        return name;
    }
}
