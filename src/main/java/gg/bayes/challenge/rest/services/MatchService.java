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
     * [00:08:46.693] npc_dota_hero_snapfire buys item item_clarity
     * [timestamp] [hero/character] buys item [item name]
     * @param combatLogs
     * @return
     */

    @Transactional(rollbackOn = Exception.class)
    public long saveCombatLogs(String combatLogs) throws ParseException {
        String [] logsByLine = combatLogs.split("\n");
        String [] wordsInLog = null;
        MatchEntity match = new MatchEntity();
        match = matchRepository.save(match);

        Set<CombatLogEntryEntity> combatLogEntries = new LinkedHashSet<>();
        for(String line : logsByLine){
            CombatLogEntryEntity combatLogEntry = matchComponent
                    .createCombatLog(new CombatLogEntryEntity(),match,"buys",line);
            if(combatLogEntry == null){
                continue;
            }
            combatLogEntries.add(combatLogEntry);
        }

        combatLogEntryRepository.saveAll(combatLogEntries);
        return match.getId();
    }

    public List<HeroKills> getHerosAndKills(Long matchId) {
        return combatLogEntryRepository.findAllHeroAndKillCount(matchId);
    }

    public List<HeroItem> getHeroAndItems(Long matchId, String heroName) {
        if (StringUtils.isBlank(heroName)) return new ArrayList<>();
        return combatLogEntryRepository.findItemAndTimestampByMatchIdAndActorAndType(matchId,heroName,CombatLogEntryEntity.Type.ITEM_PURCHASED);
    }

    public List<HeroSpells> getSpellsAndCount(Long matchId, String heroName) {
        if (StringUtils.isBlank(heroName)) return new ArrayList<>();
        return combatLogEntryRepository.fetchCastsSpellAndCount(matchId,heroName);
    }

    public List<HeroDamage> getHeroAndDamages(Long matchId, String heroName) {
        if (StringUtils.isBlank(heroName)) return new ArrayList<>();
        return combatLogEntryRepository.findHeroAndDamages(matchId,heroName);

    }
}
