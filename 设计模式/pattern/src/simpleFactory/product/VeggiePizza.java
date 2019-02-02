package simpleFactory.product;

public class VeggiePizza implements Pizza {
    @Override
    public void prepare() {
        System.out.println("prepare VeggiePizza...");
    }

    @Override
    public void bake() {
        System.out.println("bake VeggiePizza...");
    }

    @Override
    public void cut() {
        System.out.println("cut VeggiePizza...");
    }

    @Override
    public void box() {
        System.out.println("box VeggiePizza...");
    }
}
