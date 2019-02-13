package proxy.jdk;

import proxy.product.Product;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MemberInvocationHandler implements InvocationHandler {
    private Product product;
    private String grade;
    public MemberInvocationHandler(Product product, String grade) {
        this.product = product;
        this.grade = grade;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("member".equals (grade))
            return ((double) method.invoke (product, args)) * 0.8;
        else
            return method.invoke (product, args);
    }
}
