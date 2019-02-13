package proxy.product;

public class ProxyProduct implements Product {
    private Product product;
    private String grade;
    public ProxyProduct(Product product, String grade) {
        this.product = product;
        this.grade = grade;
    }
    @Override
    public double show() {
        double price = product.show ();
        switch (grade) {
            case "member":
                price = price*0.8;
                break;
            default:
                break;
        }
        return price;
    }
}
