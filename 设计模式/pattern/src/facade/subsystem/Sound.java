package facade.subsystem;

public class Sound {
    public void openSound() {
        System.out.println("打开音响...");
    }

    public void closeSound() {
        System.out.println("关闭音响...");
    }

    public void volume(int size) {
        System.out.println("设置音响音量到" + size + "%");
    }
}
