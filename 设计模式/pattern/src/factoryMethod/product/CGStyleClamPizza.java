package factoryMethod.product;

public class CGStyleClamPizza extends Pizza {
    public CGStyleClamPizza() {
        name = "CG Style Clam Pizza";
        dough = "Thin Crust Dough";
        sauce = "Marinara Sauce";
        toppings.add("Grated Reggiano Clam");
    }
}
