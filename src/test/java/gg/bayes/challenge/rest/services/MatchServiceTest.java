package gg.bayes.challenge.rest.services;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.persistence.model.MatchEntity;
import gg.bayes.challenge.persistence.repository.CombatLogEntryRepository;
import gg.bayes.challenge.persistence.repository.MatchRepository;
import gg.bayes.challenge.rest.components.MatchComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MatchServiceTest {

    @InjectMocks
    private MatchService matchService;

    @Mock
    private CombatLogEntryRepository combatLogEntryRepository;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private MatchComponent matchComponent;


    @BeforeEach
    void setUp() {
    }

    @Test
    public void test(){}

    @Test
    public void getHeroAndKillsTest(){
        Mockito.when(combatLogEntryRepository.findAllHeroAndKillCount(Mockito.anyLong())).thenReturn(new ArrayList<>());
        matchService.getHeroAndKills(1l);
    }

    @Test
    public void getHeroAndItemsTest(){
        Mockito.when(combatLogEntryRepository.findItemAndTimestampByMatchIdAndActorAndType(1l,"maro", CombatLogEntryEntity.Type.ITEM_PURCHASED)).thenReturn(new ArrayList<>());
        assertEquals(matchService.getHeroAndItems(1l,"maro"),new ArrayList<>());
    }

    @Test
    public void getHeroAndItemsBlankInputTest(){
        Mockito.when(combatLogEntryRepository.findItemAndTimestampByMatchIdAndActorAndType(1l,"maro", CombatLogEntryEntity.Type.ITEM_PURCHASED)).thenReturn(new ArrayList<>());
        assertEquals(matchService.getHeroAndItems(1l,""),new ArrayList<>());
    }

    @Test
    public void getSpellsAndCountTest(){
        Mockito.when(combatLogEntryRepository.fetchCastsSpellAndCount(1l,"maro")).thenReturn(new ArrayList<>());
        assertEquals(matchService.getSpellsAndCount(1l,"maro"),new ArrayList<>());
    }

    @Test
    public void getSpellsAndCountBlankInputTest(){
        Mockito.when(combatLogEntryRepository.fetchCastsSpellAndCount(1l,"maro")).thenReturn(new ArrayList<>());
        assertEquals(matchService.getSpellsAndCount(1l,""),new ArrayList<>());
    }

    @Test
    public void getHeroAndDamagesTest(){
        Mockito.when(combatLogEntryRepository.findHeroAndDamages(1l,"maro")).thenReturn(new ArrayList<>());
        matchService.getHeroAndDamages(1l,"maro");
    }

    @Test
    public void getHeroAndDamagesBlankTest(){
        Mockito.when(combatLogEntryRepository.findHeroAndDamages(1l,"maro")).thenReturn(new ArrayList<>());
        matchService.getHeroAndDamages(1l,"");
    }

    @Test
    public void combatLogTest() throws ParseException {
        MatchEntity match = new MatchEntity();
        match.setId(1l);
        CombatLogEntryEntity combatLogEntryEntity = new CombatLogEntryEntity();
        Mockito.when(matchRepository.save(Mockito.any())).thenReturn(match);
        Mockito.when(matchComponent
                .createCombatLog(combatLogEntryEntity,match,"[00:08:46.693] npc_dota_hero_snapfire buys item item_clarity"))
                .thenReturn(combatLogEntryEntity);
        Mockito.when(combatLogEntryRepository.saveAll(new ArrayList<>())).thenReturn(new ArrayList<>());

        assertEquals(matchService.saveCombatLogs("[00:08:46.693] npc_dota_hero_snapfire buys item item_clarity"),1l);
    }
}