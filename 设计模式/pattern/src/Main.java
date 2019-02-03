import AbstractFactory.commodity.Pizza;
import AbstractFactory.store.NYPizzaStore;
import AbstractFactory.store.PizzaStore;

public class Main {
    public static void main(String args[]) {
        PizzaStore store = new NYPizzaStore ();
        Pizza pizza = store.orderPizza ("cheese");
        System.out.println(pizza);
    }
}