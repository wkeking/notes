package factoryMethod.product;

public class CGStylePepperoniPizza extends Pizza {
    public CGStylePepperoniPizza() {
        name = "CG Style Pepperoni Pizza";
        dough = "Thin Crust Dough";
        sauce = "Marinara Sauce";
        toppings.add("Grated Reggiano Pepperoni");
    }
}
