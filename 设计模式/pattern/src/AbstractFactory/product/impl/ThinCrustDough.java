package AbstractFactory.product.impl;

import AbstractFactory.product.Dough;

public class ThinCrustDough implements Dough {
    private String name = "ThinCrustDough";
    @Override
    public String toString() {
        return name;
    }
}
