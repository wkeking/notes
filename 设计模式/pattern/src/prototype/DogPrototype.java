package prototype;

import java.util.ArrayList;

public abstract class DogPrototype implements Cloneable {
    public String name;
    public int age;
    public ArrayList<String> call = new ArrayList<> ();

    @Override
    public Object clone() throws CloneNotSupportedException {
        DogPrototype dog = (DogPrototype) super.clone ();
        dog.call = (ArrayList<String>) this.call.clone();
        return dog;
    }

    public abstract void show();
}
