package adapter;

import adapter.adaptee.Turkey;
import adapter.adaptee.WildTurkey;
import adapter.adapter.TurkeyAdapter;
import adapter.target.Duck;
import adapter.target.RedDuck;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void show(Duck duck) {
        duck.quack ();
        duck.fly ();
    }

    public static void main(String args[]) {
        Duck dad = new RedDuck ();
        Duck mom = new RedDuck ();
        Turkey turkey = new WildTurkey ();
        Duck wild = new TurkeyAdapter (turkey);
        List<Duck> team = new ArrayList<> ();
        team.add (dad);
        team.add (mom);
        team.add (wild);
        team.forEach (duck -> show (duck));
    }
}
