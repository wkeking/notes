import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Demo2 {
    public Object instance = null;
    private static final int _1M = 1024 * 1024;
    private byte[] big = new byte[2 * _1M];
    public static void main(String[] args) throws Error {
        String str = new String("abc");
        char[] arrays = {'a', 'b', 'c'};
        String str2 = new String(arrays);
        System.out.println(str == str2);
        str2 = str2.intern();
        System.out.println(str == str2);

        String str3 = "bcd";
        String str4 = "bcd";
        System.out.println(str3 == str4);

    }
}