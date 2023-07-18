package org.example.character;

import org.example.melee.Melee;
import org.example.ranged.Ranged;
import org.junit.Test;

import static org.junit.Assert.*;

public class CharacterTest {
    Character character = new Character();

    //ITERATION ONE
    //I 1.1
    @Test
    public void Health_should_start_at_1000() {
        assertEquals(1000, character.getHealth());
    }

    //MISMO TEST CON TRUE
    @Test
    public void Health_should_start_at_1000True() {
        assertTrue(character.getHealth() >= 1000);
    }

    @Test
    public void Level_should_start_at_1() {
        assertEquals(1, character.getLevel());
    }

    @Test
    public void Alive_status_should_start_True() {
        assertTrue(character.isAlive());
    }

    //I 1.2
    @Test
    public void Character_can_DealDamage_to_Characters() {
        int health = character.getHealth();
        int damage = 50;
        character.dealDamage(damage);
        assertEquals(health - damage, character.getHealth());
    }

    @Test
    public void Character_can_die_when_Health_0() {
        int damage = 1001;
        character.dealDamage(damage);
        assertFalse(character.isAlive());
        assertEquals(0, character.getHealth());
    }

    //I 1.3
    @Test
    public void Character_can_Heal_only_if_isAlive() {
        character.dealDamage(900);
        character.healDamage();

        assertTrue(character.isAlive());
        assertEquals(1000, character.getHealth());
    }

    //ITERATION TWO
    // I 2.1
    @Test
    public void Character_cannot_attack_itself() {
        int initHealth = character.getHealth();
        character.attack(character, 50, 0);

        assertEquals(1000, initHealth);
    }

    // I 2.2
    @Test
    public void Character_only_Heal_itself() {
        Character victim = new Character();
        victim.setRange(1);
        character.attack(victim, 100, 0);
        character.heal(victim);

        assertEquals(900, victim.getHealth());
    }

    // I 2.3
    @Test
    public void Character_Damage_reduce_50_percent_if_Target_is_5_Levels_above() {
        Character victim = new Character();
        // Victim 5 or more level above
        victim.setLevel(8);
        victim.setRange(1);
        character.attack(victim, 100, 0);

        assertEquals(950, victim.getHealth());
    }

    @Test
    public void Character_Damage_increased_50_percent_if_target_is_5_Levels_below() {
        Character victim = new Character();
        victim.setRange(1);
        // Atacante 5 or more level above
        character.setLevel(8);
        character.attack(victim, 100, 0);

        assertEquals(850, victim.getHealth());
    }

    //ITERATION THREE
    // I 3.1
    @Test
    public void Character_must_be_in_range_to_deal_damage_to_a_target_Ranged() {
        Character victim = new Ranged();
        victim.setLevel(8);

        character.attack(victim, 100, 19);

        assertEquals(950, victim.getHealth());
    }

    @Test
    public void Character_must_be_in_range_to_deal_damage_to_a_target_Melee() {
        Character victim = new Melee();
        victim.setLevel(8);

        character.attack(victim, 100, 5);

        assertEquals(1000, victim.getHealth());
    }
    //ITERATION FOUR
    // I 4.1
}