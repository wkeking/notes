package abstractFactory.product.impl;

import abstractFactory.product.Sauce;

public class MarinaraSauce implements Sauce {
    private String name = "MarinaraSauce";
    @Override
    public String toString() {
        return name;
    }
}
