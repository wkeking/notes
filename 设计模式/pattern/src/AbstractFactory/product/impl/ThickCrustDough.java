package AbstractFactory.product.impl;

import AbstractFactory.product.Dough;

public class ThickCrustDough implements Dough {
    private String name = "ThickCrustDough";
    @Override
    public String toString() {
        return name;
    }
}
