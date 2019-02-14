package bridge;

import bridge.abstraction.ConcreteRemoteControl;
import bridge.implementor.Sony;
import bridge.implementor.TV;

public class Client {
    public static void main(String args[]) {
        TV tv = new Sony ();
        ConcreteRemoteControl control = new ConcreteRemoteControl (tv);
        control.on ();
        control.setChannel (5);
        control.nextChannel ();
        control.previousChannel ();
        control.previousChannel ();
        control.off ();
    }
}
