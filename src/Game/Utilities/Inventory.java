package Game.Utilities;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import Game.Utilities.Ammunition;

public class Inventory {
    private HashMap<Ammunition, Integer> items = new HashMap<>();

    public Inventory() {
        items.put(Ammunition.BONE, 0);
        items.put(Ammunition.FIRE_CHARGE, 0);
        items.put(Ammunition.LIGHTNING_CHARGE, 0);
    }

    public boolean hasItem(Ammunition ammunition) {
        return items.get(ammunition) > 0;
    }

    public int getCount(Ammunition ammunition) {
        return items.get(ammunition);
    }

    public void removeItem(Ammunition ammunition) {
        items.put(ammunition, items.get(ammunition) - 1);
    }

    public void addItems(Ammunition ammunition, int count) {
        if (items.get(ammunition) + count <=999){
            items.put(ammunition, items.get(ammunition) + count);
        } else {
            while(items.get(ammunition)!=999) {
                items.put(ammunition, items.get(ammunition) + 1);
            }
        }


    }
}
