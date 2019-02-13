package composite;

public class MenuItem extends MenuComponent {
    private String name;
    private double price;
    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public double getPrice() {
        return price;
    }
    @Override
    public void print() {
        System.out.println("Item名称：" + name + " 价格：" + price);
    }
}
