package Game.Utilities;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import Game.GameObjects.Projectiles.Bone;
import Game.Levels.ActiveLevel;
import Game.Utilities.Ammunition;

public class Inventory {
    private HashMap<Ammunition, Integer> items = new HashMap<>();

    public Inventory() {
        items.put(Ammunition.BONE, 0);
        items.put(Ammunition.FIRE_CHARGE, 0);
        items.put(Ammunition.LIGHTNING_CHARGE, 0);
    }

    public Inventory(ArrayList<String> lines) {
        for(String line : lines) {
            if(line.startsWith("BONE=")) {
                items.put(Ammunition.BONE, Integer.parseInt(line.replace("BONE=", "").trim()));
            } else if(line.startsWith("FIRE CHARGE=")) {
                items.put(Ammunition.FIRE_CHARGE, Integer.parseInt(line.replace("FIRE CHARGE=", "").trim()));
            } else if(line.startsWith("LIGHTNING CHARGE=")) {
                items.put(Ammunition.LIGHTNING_CHARGE, Integer.parseInt(line.replace("LIGHTNING CHARGE=", "").trim()));
            }
        }
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

    public void clear() {
        items.put(Ammunition.BONE, 0);
        items.put(Ammunition.FIRE_CHARGE, 0);
        items.put(Ammunition.LIGHTNING_CHARGE, 0);
    }

    public String encode() {
        String result = "";

        result += "BONE=" + items.get(Ammunition.BONE) + "\n";
        result += "FIRE CHARGE=" + items.get(Ammunition.FIRE_CHARGE) + "\n";
        result += "LIGHTNING CHARGE=" + items.get(Ammunition.LIGHTNING_CHARGE) + "\n";

        return result;
    }
}
