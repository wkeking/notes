package flyweight;

import java.util.HashMap;
import java.util.Map;

public class TreeFlyweightFactory {
    private static Map<String, TreeFlyweight> map = new HashMap<> ();
    public static TreeFlyweight getTree(String name, TreeState state) {
        TreeFlyweight treeFlyweight = map.get (name);
        if (treeFlyweight == null) {
            treeFlyweight = new ConcreateTree (name, "树共享属性");
            map.put (name, treeFlyweight);
            treeFlyweight.setState (state);
        }
        return treeFlyweight;
    }
}
