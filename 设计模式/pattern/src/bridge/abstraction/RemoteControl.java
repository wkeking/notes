package bridge.abstraction;

import bridge.implementor.TV;

public abstract class RemoteControl {
    protected TV tv;
    protected int currentStation;
    public RemoteControl(TV tv) {
        this.tv = tv;
    }
    public abstract void on();
    public abstract void off();
    public void setChannel(int channel) {
        tv.tuneChannel (channel);
        currentStation = channel;
    }
}
