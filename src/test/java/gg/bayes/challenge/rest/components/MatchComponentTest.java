package gg.bayes.challenge.rest.components;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.persistence.model.MatchEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MatchComponentTest {
    /**
     * Test cases for match component services class.
     *
     */
    @InjectMocks
    private MatchComponent matchComponent;

    String buysLog;
    String killedLog;
    String hitsLog;
    String castsLog;
    @BeforeEach
    void setUp() {
        buysLog = "[00:08:46.693] npc_dota_hero_snapfire buys item item_clarity";
        killedLog="[00:35:07.423] npc_dota_hero_snapfire is killed by npc_dota_hero_bloodseeker";
        hitsLog="[00:34:58.192] npc_dota_hero_abyssal_underlord hits npc_dota_hero_death_prophet with abyssal_underlord_firestorm for 78 damage (1807->1729)";
        castsLog="[00:35:10.300] npc_dota_hero_bane casts ability bane_nightmare (lvl 4) on npc_dota_hero_dragon_knight";
    }

    @Test
    public void test(){}
    @Test
    public void createCombatLogBuysKeyTest() throws ParseException {
        CombatLogEntryEntity combatLogEntryEntity = new CombatLogEntryEntity();
        assertEquals(matchComponent.createCombatLog(combatLogEntryEntity,new MatchEntity(),buysLog), combatLogEntryEntity);
    }

    @Test
    public void createCombatLogHitsKeyTest() throws ParseException {
        CombatLogEntryEntity combatLogEntryEntity = new CombatLogEntryEntity();
        assertEquals(matchComponent.createCombatLog(combatLogEntryEntity,new MatchEntity(),hitsLog), combatLogEntryEntity);
    }

    @Test
    public void createCombatLogKilledKeyTest() throws ParseException {
        CombatLogEntryEntity combatLogEntryEntity = new CombatLogEntryEntity();
        assertEquals(matchComponent.createCombatLog(combatLogEntryEntity,new MatchEntity(),killedLog), combatLogEntryEntity);
    }

    @Test
    public void createCombatLogCastsTest() throws ParseException {
        CombatLogEntryEntity combatLogEntryEntity = new CombatLogEntryEntity();
        assertEquals(matchComponent.createCombatLog(combatLogEntryEntity,new MatchEntity(),castsLog), combatLogEntryEntity);
    }
}