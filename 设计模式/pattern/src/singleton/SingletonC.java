package singleton;

public class SingletonC {
    private volatile static SingletonC instance;
    private SingletonC() {}
    public static SingletonC getInstance() {
        if (instance == null) {
            synchronized (SingletonC.class) {
                if (instance == null) {
                    instance = new SingletonC ();
                }
            }
        }
        return instance;
    }
}
