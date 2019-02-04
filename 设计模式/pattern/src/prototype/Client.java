package prototype;

public class Client {
    public static void main(String args[]) throws CloneNotSupportedException {
        Dog dog = new Dog ();
        dog.name = "tom";
        dog.age = 1;
        dog.call.add ("wangwang");
        dog.call.add ("wuwu");
        Dog dog1 = (Dog) dog.clone ();
        dog.show ();
        dog1.show ();
        System.out.println(dog == dog1);
    }
}
