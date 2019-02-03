package AbstractFactory.product.impl;

import AbstractFactory.product.Sauce;

public class PlumTomatoSauce implements Sauce {
    private String name = "PlumTomatoSauce";
    @Override
    public String toString() {
        return name;
    }
}
