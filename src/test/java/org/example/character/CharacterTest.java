package org.example.character;

import org.junit.Test;

import static org.junit.Assert.*;

public class CharacterTest {
    Character character = new Character();

    //ITERATION ONE
        //I 1.1
        @Test
        public void Health_should_start_at_1000(){
            assertEquals(1000, character.getHealth());
        }
        //MISMO TEST CON TRUE
        @Test
        public void Health_should_start_at_1000True(){
            assertTrue(character.getHealth() >= 1000);
        }
        @Test
        public void Level_should_start_at_1(){
            assertEquals(1, character.getLevel());
        }
        @Test
        public void Alive_status_should_start_True(){
            assertTrue(character.isAlive());
        }
        //I 1.2
        @Test
        public void Character_can_DealDamage_to_Characters(){
            int health = character.getHealth();
            int damage = 50;
            character.dealDamage(damage);
            assertEquals(health - damage, character.getHealth());
        }
        @Test
        public void Character_can_die_when_Health_0(){
            int initHealth = character.getHealth();
            int damage = 1001;
            character.dealDamage(damage);
            assertFalse(character.isAlive());
            assertEquals(0, character.getHealth());
        }
        //I 1.3
        @Test
        public void Character_can_Heal_only_if_isAlive(){
            character.dealDamage(900);
            character.heal();

            assertTrue(character.isAlive());
            assertEquals(1000, character.getHealth());
        }
}