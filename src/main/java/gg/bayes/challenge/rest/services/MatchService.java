package gg.bayes.challenge.rest.services;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.persistence.model.MatchEntity;
import gg.bayes.challenge.persistence.repository.CombatLogEntryRepository;
import gg.bayes.challenge.persistence.repository.MatchRepository;
import gg.bayes.challenge.rest.Utils.MatchUtility;
import gg.bayes.challenge.rest.components.MatchComponent;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItem;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.*;

@Slf4j
@Service
public class MatchService {

    @Autowired
    private MatchComponent matchComponent;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private CombatLogEntryRepository combatLogEntryRepository;

    /**
     * Splitting the event logs by line and processing it. If the required details are available in the result save both
     * match and combat log details database.
     *
     * @param combatLogs the logs of the event
     * @return Match id associated with the event
     * @throws ParseException
     */
    @Transactional(rollbackOn = Exception.class)
    public long saveCombatLogs(String combatLogs) throws ParseException {
        String [] logsByLine = combatLogs.split("\n");
        MatchEntity match = new MatchEntity();
        match = matchRepository.save(match);

        Set<CombatLogEntryEntity> combatLogEntries = new LinkedHashSet<>();
        for(String line : logsByLine){
            CombatLogEntryEntity combatLogEntry = matchComponent
                    .createCombatLog(new CombatLogEntryEntity(),match,line);
            if(combatLogEntry == null){
                continue;
            }
            combatLogEntries.add(combatLogEntry);
        }

        combatLogEntryRepository.saveAll(combatLogEntries);
        return match.getId();
    }

    /**
     * Fetch the details of hero and the kills they made by querying in the database based on the match id and other details
     *
     * @param matchId Match id of the event
     * @return Collection of hero and the number of kills they made
     */
    public List<HeroKills> getHeroAndKills(Long matchId) {
        return combatLogEntryRepository.findAllHeroAndKillCount(matchId);
    }

    /**
     * Fetch the hero and teh items they purchased during the event by querying the database
     *
     * @param matchId match id of the event
     * @param heroName name of one of the hero in the match
     * @return Collection of heo and items they purchased in during the event
     */
    public List<HeroItem> getHeroAndItems(Long matchId, String heroName) {
        if (StringUtils.isBlank(heroName)) {
            log.info("Null/ Empty hero name input passed while fetching hero and items");
            return new ArrayList<>();
        };
        return combatLogEntryRepository.findItemAndTimestampByMatchIdAndActorAndType(matchId,heroName,CombatLogEntryEntity.Type.ITEM_PURCHASED);
    }

    /**
     * Fetch the hero and the spell cast, by querying the database
     *
     * @param matchId match id of the event
     * @param heroName name of one of the hero in the match
     * @return Collection of Hero and the spell cast
     */
    public List<HeroSpells> getSpellsAndCount(Long matchId, String heroName) {
        if (StringUtils.isBlank(heroName)) {
            log.info("Null/ Empty hero name input passed while fetching spells and count");
            return new ArrayList<>();
        };
        return combatLogEntryRepository.fetchCastsSpellAndCount(matchId,heroName);
    }

    /**
     * Fetch hero got attacked and the damages happened by querying teh database
     *
     * @param matchId match id of the event
     * @param heroName name of one of the hero in the match
     * @return Collection of hero got attached and the damages caused
     */
    public List<HeroDamage> getHeroAndDamages(Long matchId, String heroName) {
        if (StringUtils.isBlank(heroName)) {
            log.info("Null/ Empty hero name input passed while fetching hero and damage");
            return new ArrayList<>();
        };
        return combatLogEntryRepository.findHeroAndDamages(matchId,heroName);

    }
}
