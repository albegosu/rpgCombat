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
    public void heal() {
        if (isAlive()) {
            health = 1000;
        }
    }

}
