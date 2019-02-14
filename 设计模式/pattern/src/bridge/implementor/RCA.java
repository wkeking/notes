package bridge.implementor;

public class RCA implements TV {
    @Override
    public void on() {
        System.out.println("RCA on");
    }
    @Override
    public void off() {
        System.out.println("RCA off");
    }
    @Override
    public void tuneChannel(int channel) {
        System.out.println("Channel is " + channel);
    }
}
