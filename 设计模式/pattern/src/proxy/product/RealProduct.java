package proxy.product;

public class RealProduct implements Product {
    private double price;
    public RealProduct(double price) {
        this.price = price;
    }
    @Override
    public double show() {
        return price;
    }
}
