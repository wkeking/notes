package proxy.product;

public class Client {
    public static void main(String args[]) {
        Product realProduct = new RealProduct (100.0d);
        Product proxyProduct = new ProxyProduct (realProduct, "member");
        double price = proxyProduct.show ();
        System.out.println("商品价格为：" + price);
    }
}
