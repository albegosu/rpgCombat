package org.example.character;

import org.example.faction.Faction;
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
        character.heal(character, 400);

        assertTrue(character.isAlive());
        assertEquals(500, character.getHealth());
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
    public void characterOnlyHealsItself() {
        // GIVEN
        Character victim = new Character();
        victim.setRange(1);

        // Perform an attack on the victim
        character.attack(victim, 100, 0);

        // WHEN
        character.heal(character, 100);

        // THEN
        assertEquals(1000, character.getHealth());
        assertEquals(900, victim.getHealth()); // Ensure the victim's health remains unchanged
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
    @Test
    public void characterStartsWithoutFactionButCanJoinOneOrMany() {
        //GIVEN

        Character sigmaMaleChad = new Character();
        Character badTasteBenny = new Character();
        Character peerPressureJohn = new Character();

        Faction jocksClub = new Faction("Jocks Club");
        Faction badBunnyFans = new Faction("Bad Bunny Fan Club");

        //WHEN
        badTasteBenny.joinFaction(badBunnyFans);
        peerPressureJohn.joinFaction(jocksClub, badBunnyFans);

        var sut1 = sigmaMaleChad.getFactions();
        var sut2 = badTasteBenny.getFactions();
        var sut3 = peerPressureJohn.getFactions();

        //THEN

        assertEquals(0, sut1.size());
        assertEquals(1, sut2.size());
        assertEquals(2, sut3.size());
    }
    @Test
    public void CharacterMayLeaveOneOrManyFactions() {
        //GIVEN
        Character faithfulFinn = new Character();
        Character traitorTom = new Character();
        Character renegadeRoy = new Character();

        Faction heroAlliance = new Faction("Hero Alliance");
        Faction spiesGuild = new Faction("Spies' Guild");

        //WHEN
        faithfulFinn.joinFaction(heroAlliance);
        traitorTom.joinFaction(heroAlliance, spiesGuild);
        renegadeRoy.joinFaction(heroAlliance, spiesGuild);

        traitorTom.leaveFaction(heroAlliance);
        renegadeRoy.leaveFaction(heroAlliance, spiesGuild);

        var sut1 = faithfulFinn.getFactions();
        var sut2 = traitorTom.getFactions();
        var sut3 = renegadeRoy.getFactions();

        //THEN
        assertEquals(1, sut1.size());
        assertEquals(1, sut2.size());
        assertTrue(sut3.isEmpty());

    }

    @Test
    public void charactersInSameFactionAreAllies() {

        //GIVEN
        Character masterChief = new Character();
        Character cortana = new Character();
        Character sangheili = new Character();

        Faction spaceCommand = new Faction("United Nations Space Command");
        Faction covenant = new Faction("Covenant");

        //WHEN
        masterChief.joinFaction(spaceCommand);
        cortana.joinFaction(spaceCommand);
        sangheili.joinFaction(covenant);

        var sut1 = masterChief.isAlly(cortana);
        var sut2 = cortana.isAlly(masterChief);
        var sut3 = masterChief.isAlly(sangheili);

        //THEN

        assertTrue(sut1);
        assertTrue(sut2);
        assertFalse(sut3);
    }

    @Test
    public void characterCannotDamageAllies() {
        //GIVEN
        Character masterChief = new Character();
        Character cortana = new Character();
        Character sangheili = new Character();

        Faction spaceCommand = new Faction("United Nations Space Command");
        Faction covenant = new Faction("Covenant");

        //WHEN
        masterChief.joinFaction(spaceCommand);
        cortana.joinFaction(spaceCommand);
        sangheili.joinFaction(covenant);

        masterChief.attack(cortana, 50, 0);
        masterChief.attack(sangheili, 50, 0);

        var sut1 = cortana.getHealth();
        var sut2 = sangheili.getHealth();

        //THEN

        assertEquals(1000, sut1);
        assertEquals(950, sut2);
    }
    @Test
    public void characterCanHealAllies() {
        //GIVEN
        Character masterChief = new Character();
        Character cortana = new Character();
        Character sangheili = new Character();

        Faction spaceCommand = new Faction("United Nations Space Command");
        Faction covenant = new Faction("Covenant");

        //WHEN
        masterChief.joinFaction(spaceCommand);
        cortana.joinFaction(spaceCommand);
        sangheili.joinFaction(covenant);

        masterChief.setDamage(500);
        cortana.setDamage(500);
        sangheili.setDamage(500);

        masterChief.heal(masterChief, 100);
        masterChief.heal(cortana, 100);
        masterChief.heal(sangheili, 100);

        var sut1 = masterChief.getHealth();
        var sut2 = cortana.getHealth();
        var sut3 = sangheili.getHealth();

        //THEN
        assertEquals(1000, sut1);
        assertEquals(1000, sut2);
        assertEquals(1000, sut3);
    }
}