package adapter.target;

public class RedDuck implements Duck {
    @Override
    public void quack() {
        System.out.println("quack quack");
    }

    @Override
    public void fly() {
        System.out.println("flying");
    }
}
