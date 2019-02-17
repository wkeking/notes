package flyweight;

public class TreeState {
    private int x;
    private int y;
    private int age;

    public TreeState(int x, int y, int age) {
        this.x = x;
        this.y = y;
        this.age = age;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "TreeState{" +
                "x=" + x +
                ", y=" + y +
                ", age=" + age +
                '}';
    }
}
