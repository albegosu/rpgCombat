package org.example.character;

public class Character {
    int health = 1000;
    int level = 1;
    boolean alive = true;
    int damage;

    public int getHealth() {
        return health;
    }

    public int getLevel() {
        return level;
    }
    public boolean isAlive() {
        return alive;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public int getDamage() {
        return damage;
    }
    public void dealDamage(int damage){
        health -= damage;
        if(health <= 0) {
            alive = false;
            health = 0;
        }
    }
    public void healDamage() {
        if (isAlive()) {
            health = 1000;
        }
    }
    public void attack(Character victim, int damage){
        if (victim != this){
            int modifyDamage = damage;
            if(victim.getLevel() >= (this.level + 5)){
                modifyDamage = (int) (damage * 0.5);
            }else if (victim.getLevel() <= (this.level -5)){
                modifyDamage = (int) (damage * 1.5);
            }
            victim.dealDamage(modifyDamage);
        }
    }
    public void heal(Character healer){
        if (healer != this){
            healer.healDamage();
        }
    }
}
