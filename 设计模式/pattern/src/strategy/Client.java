package strategy;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Client {
    public static void main(String[] args) {
        StrategyContext user = new StrategyContext ("老用户");
        BigDecimal price = user.getPrice (new BigDecimal (100.0));
        System.out.println(price);

        Calendar instance = Calendar.getInstance ();
        instance.add (Calendar.HOUR_OF_DAY, -48);
        Date time = instance.getTime ();
        SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        String s = format.format (time);
        System.out.println(s);
    }
}
