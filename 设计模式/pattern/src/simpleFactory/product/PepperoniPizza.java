package simpleFactory.product;

public class PepperoniPizza implements Pizza {
    @Override
    public void prepare() {
        System.out.println("prepare PepperoniPizza...");
    }

    @Override
    public void bake() {
        System.out.println("bake PepperoniPizza...");
    }

    @Override
    public void cut() {
        System.out.println("cut PepperoniPizza...");
    }

    @Override
    public void box() {
        System.out.println("box PepperoniPizza...");
    }
}
