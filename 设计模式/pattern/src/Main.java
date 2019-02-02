import simpleFactory.PizzaStore;
import simpleFactory.factory.SimplePizzaFactory;

public class Main {
    public static void main(String args[]) {
        SimplePizzaFactory factory = new SimplePizzaFactory();
        PizzaStore store = new PizzaStore(factory);
        store.orderPizza("cheese");
    }
}