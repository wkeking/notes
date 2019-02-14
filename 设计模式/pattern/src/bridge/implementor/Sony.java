package bridge.implementor;

public class Sony implements TV {
    @Override
    public void on() {
        System.out.println("Sony on");
    }
    @Override
    public void off() {
        System.out.println("Sony off");
    }
    @Override
    public void tuneChannel(int channel) {
        System.out.println("Channel is " + channel);
    }
}
