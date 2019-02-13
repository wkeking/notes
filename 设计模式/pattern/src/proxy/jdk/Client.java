package proxy.jdk;

import proxy.product.Product;
import proxy.product.RealProduct;

import java.lang.reflect.Proxy;

public class Client {
    public static void main(String args[]) {
        Product product = new RealProduct (100.0d);
        Product proxy = (Product) Proxy.newProxyInstance (Client.class.getClassLoader (),
                new Class[]{Product.class},
                new MemberInvocationHandler (product, "member"));
        double price = proxy.show ();
        System.out.println("商品价格为：" + price);
    }
}
