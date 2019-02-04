package prototype;

public class Dog extends DogPrototype {

    @Override
    public void show() {
        System.out.println(this.toString ());
        System.out.println("name is " + this.name + "\n" +
                            "age is " + this.age + "\n" +
                            "call is " + this.call);
    }
}
