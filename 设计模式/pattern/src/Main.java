import builder.builder.HotBuilder;
import builder.director.YXRSDirector;
import builder.product.YXRS;

public class Main {
    public static void main(String args[]) {
        YXRSDirector director = new YXRSDirector (new HotBuilder ());
        YXRS yxrs = director.construct ();
        System.out.println(yxrs);
    }
}