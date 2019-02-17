package flyweight;

public class Client {
    public static void main(String[] args) {
        TreeState state = new TreeState (10, 10, 2);
        TreeFlyweight tree1 = TreeFlyweightFactory.getTree ("松树", state);
        System.out.println(tree1);

        state = new TreeState (20, 10, 3);
        TreeFlyweight tree2 = TreeFlyweightFactory.getTree ("胡杨", state);
        System.out.println(tree2);

        TreeFlyweight tree3 = TreeFlyweightFactory.getTree ("松树", state);
        System.out.println(tree3);
    }
}
