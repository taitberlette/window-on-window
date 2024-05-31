package Game.Utilities;

public class Skill {
    private boolean unlocked = true;
    private int cooldownLength;
    private int cooldown;
    private boolean isActivated = false;
    public Skill(int cooldown){
        this.cooldown = cooldown;
        this.cooldownLength = cooldown;
    }

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

    public void changeCooldown(){
        if(isActivated && cooldownLength > 0){
            cooldownLength --;
        } else if(!isActivated && cooldownLength < cooldown) {
            cooldownLength++;
        }
    }
    public boolean getActiveStatus(){return isActivated;}
    public int getCooldown() {
        return cooldown;
    }

    public int getCooldownLength() {
        return cooldownLength;
    }





}
