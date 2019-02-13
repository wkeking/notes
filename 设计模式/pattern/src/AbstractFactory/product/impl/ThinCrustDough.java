package abstractFactory.product.impl;

import abstractFactory.product.Dough;

public class ThinCrustDough implements Dough {
    private String name = "ThinCrustDough";
    @Override
    public String toString() {
        return name;
    }
}
