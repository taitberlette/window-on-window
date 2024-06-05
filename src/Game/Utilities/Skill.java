package Game.Utilities;

import java.util.ArrayList;

public class Skill {
    private boolean unlocked = false;
    private double cooldownLength;
    private double cooldown;
    private boolean isActivated = false;
    public Skill(int cooldown){
        this.cooldown = cooldown;
        this.cooldownLength = cooldown;
    }

    public Skill(ArrayList<String> lines) {
        for(String line : lines) {
            if(line.startsWith("UNLOCKED=")) {
                unlocked = Boolean.parseBoolean(line.replace("UNLOCKED=", "").trim());
            } else if(line.startsWith("COOLDOWN=")) {
                cooldown = Double.parseDouble(line.replace("COOLDOWN=", "").trim());
            } else if(line.startsWith("MAX COOLDOWN=")) {
                cooldownLength = Double.parseDouble(line.replace("MAX COOLDOWN=", "").trim());
            }
        }
    }

    public void update(long deltaTime) {
        if(isActivated && cooldown > 0){
            cooldown = Math.max(cooldown - ((double) deltaTime / 1000000000), 0);
        } else if(!isActivated && cooldown < cooldownLength) {
            cooldown = Math.min(cooldown + ((double) deltaTime / 1000000000), cooldownLength);
        }

        if(cooldown == 0) {
            isActivated = false;
        }
    }

    public boolean wasUnlocked() {
        return unlocked;
    }

    public void unlock() {
        unlocked = true;
    }

    public void activate() {
        isActivated = true;


    }

    public boolean isFull() {
        return cooldown == cooldownLength;
    }

    public void useFull() {
        cooldown = 0;
    }

    public void deactivate() {
        isActivated = false;
    }

    public boolean getActiveStatus(){return isActivated;}
    public double getCooldown() {
        return cooldown;
    }

    public double getCooldownLength() {
        return cooldownLength;
    }

    public String encode() {
        String result = "";

        result += "UNLOCKED=" + unlocked + "\n";
        result += "COOLDOWN=" + cooldown + "\n";
        result += "MAX COOLDOWN=" + cooldownLength + "\n";

        return result;
    }
}
