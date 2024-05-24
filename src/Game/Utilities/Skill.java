package Game.Utilities;

public class Skill {
    private boolean unlocked = false;
    private int cooldownLength = 0;
    private int cooldown = 0;
    private boolean isActivated = false;

    public void update(long deltaTime) {

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

    public void deactivate() {
        isActivated = false;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getCooldownLength() {
        return cooldownLength;
    }
}
