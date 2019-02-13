package composite;

import java.util.ArrayList;
import java.util.List;

public class Menu extends MenuComponent {
    private List<MenuComponent> list;
    private String name;
    public Menu(String name) {
        this.name = name;
        list = new ArrayList<> ();
    }
    @Override
    public void add(MenuComponent menuComponent) {
        list.add (menuComponent);
    }
    @Override
    public void remove(MenuComponent menuComponent) {
        list.remove (menuComponent);
    }
    @Override
    public MenuComponent getChild(int i) {
        return list.get (i);
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public void print() {
        System.out.println("Menu名称：" + name);
    }
}
