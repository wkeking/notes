package AbstractFactory.product.impl;

import AbstractFactory.product.Clams;

public class FreshClams implements Clams {
    private String name = "FreshClams";
    @Override
    public String toString() {
        return name;
    }
}
