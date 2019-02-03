package singleton;

public class SingletonA {
    private static SingletonA instance = new SingletonA ();
    private SingletonA(){}
    public static SingletonA getInstance() {
        return instance;
    }
}
