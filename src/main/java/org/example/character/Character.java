package org.example.character;

import org.example.faction.Faction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Character {
    int health = 1000;
    int level = 1;
    boolean alive = true;
    int damage;
    int range;
    List<Faction> factions = new ArrayList<>();

    public int getHealth() {
        return health;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public boolean isAlive() {
        return alive;
    }
    public int getRange() { return range; }
    public void setRange(int range) { this.range = range; }
    public List<Faction> getFactions() { return factions; }
    public void joinFaction(Faction... faction) {
        this.factions.addAll(Arrays.asList(faction));
    }
    public void leaveFaction(Faction... faction) {
        this.factions.removeAll(Arrays.asList(faction));
    }
    public void dealDamage(int damage){
        this.health -= damage;
        if(this.health <= 0) {
            this.alive = false;
            this.health = 0;
        }
    }
    public void attack(Character victim, int damage, int distance){

        if (victim != this && !this.isAlly(victim)){
            if (distance < victim.getRange()) {
                int modifyDamage = damage;
                if (victim.getLevel() >= (this.level + 5)) {
                    modifyDamage = (int) (damage * 0.5);
                } else if (victim.getLevel() <= (this.level - 5)) {
                    modifyDamage = (int) (damage * 1.5);
                }
                victim.dealDamage(modifyDamage);
            }
        }
    }
    public void heal(Character character, int healthAmount) {
        if (character == this || this.isAlly(character) ) {
            character.restoreHealth(healthAmount);
        }
    }
    private void restoreHealth(int healthAmount) {
        if(this.alive && this.health <= 1000) {
            this.health += healthAmount;
            if (this.health > 1000) {
                this.health = 1000;
            }
        }
    }
    public Boolean isAlly(Character character) {
        List<Faction> commonFactions = new ArrayList<Faction>(this.getFactions());
        commonFactions.retainAll(character.getFactions());
        return commonFactions.size() > 0;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
