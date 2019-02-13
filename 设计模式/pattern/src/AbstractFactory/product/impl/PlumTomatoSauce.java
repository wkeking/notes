package abstractFactory.product.impl;

import abstractFactory.product.Sauce;

public class PlumTomatoSauce implements Sauce {
    private String name = "PlumTomatoSauce";
    @Override
    public String toString() {
        return name;
    }
}
