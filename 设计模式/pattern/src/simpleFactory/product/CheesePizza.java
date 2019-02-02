package simpleFactory.product;

public class CheesePizza implements Pizza {
    @Override
    public void prepare() {
        System.out.println("prepare CheesePizza...");
    }

    @Override
    public void bake() {
        System.out.println("bake CheesePizza...");
    }

    @Override
    public void cut() {
        System.out.println("cut CheesePizza...");
    }

    @Override
    public void box() {
        System.out.println("box CheesePizza...");
    }
}
