package bridge.abstraction;

import bridge.implementor.TV;

public class ConcreteRemoteControl extends RemoteControl {
    public ConcreteRemoteControl(TV tv) {
        super (tv);
        currentStation = 1;
    }
    @Override
    public void on() {
        tv.on ();
    }
    @Override
    public void off() {
        tv.off ();
    }
    public void nextChannel() {
        currentStation ++;
        tv.tuneChannel (currentStation);
    }
    public void previousChannel() {
        currentStation --;
        tv.tuneChannel (currentStation);
    }
}
