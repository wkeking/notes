package AbstractFactory.product.impl;

import AbstractFactory.product.Clams;

public class FrozenClams implements Clams {
    private String name = "FrozenClams";
    @Override
    public String toString() {
        return name;
    }
}
