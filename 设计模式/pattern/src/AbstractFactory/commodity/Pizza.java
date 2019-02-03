package AbstractFactory.commodity;

import AbstractFactory.product.Cheese;
import AbstractFactory.product.Clams;
import AbstractFactory.product.Dough;
import AbstractFactory.product.Sauce;

public abstract class Pizza {
    protected String name;
    protected Dough dough;
    protected Sauce sauce;
    protected Cheese cheese;
    protected Clams clams;

    public abstract void prepare();

    public void bake() {
        System.out.println("Bake for 25 minutes at 350");
    }

    public void cut() {
        System.out.println("Cutting the pizza into diagonal slices");
    }

    public void box() {
        System.out.println("Place pizza in official PizzaStore box");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "This pizza is " + getName () + "\n" +
                "Dough is " + dough + "\n" +
                "Sauce is " + sauce + "\n" +
                "Cheese is " + cheese + "\n" +
                "Clams is " + clams + "\n" +
                "Thank you!";
    }
}
