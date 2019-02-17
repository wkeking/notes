package flyweight;

public class ConcreateTree implements TreeFlyweight {
    private String name;
    private TreeState state;
    private String shared;
    public ConcreateTree(String name, String shared) {
        this.name = name;
        this.shared = shared;
        System.out.println("生成一棵" + name);
    }
    @Override
    public void setState(TreeState state) {
        this.state = state;
    }
    @Override
    public String toString() {
        return "ConcreateTree{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", shared='" + shared + '\'' +
                '}';
    }
}
