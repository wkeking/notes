package factoryMethod.product;

public class CGStyleCheesePizza extends Pizza {
    public CGStyleCheesePizza() {
        name = "CG Style Cheese Pizza";
        dough = "Thin Crust Dough";
        sauce = "Marinara Sauce";
        toppings.add("Grated Reggiano Cheese");
    }
}
