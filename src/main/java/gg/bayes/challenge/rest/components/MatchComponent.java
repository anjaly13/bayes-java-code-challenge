package gg.bayes.challenge.rest.components;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.persistence.model.MatchEntity;
import gg.bayes.challenge.rest.Utils.CombatLogConstants;
import gg.bayes.challenge.rest.Utils.MatchUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class MatchComponent {

    /**
     * The line of log consists of any specific words, pick the line and further separate by words and take the words
     * required for filling up the combat log entity
     *
     * @param combatLogEntryEntity combat log database entity for collecting the processed log data
     * @param match match database entity contains saved match data
     * @param line one line of log from the event log, separated by each line
     * @return Parsed combat log data
     * @throws ParseException Exception occurred while parsing
     */
    public CombatLogEntryEntity createCombatLog(CombatLogEntryEntity combatLogEntryEntity, MatchEntity match, String line) throws ParseException {
        if(line.contains(CombatLogConstants.KEY_BUYS) && line.contains(CombatLogConstants.HERO_PREFIX)){
            // eg: [00:08:46.693] npc_dota_hero_snapfire buys item item_clarity

            String [] wordsInLog = line.split(CombatLogConstants.SPACE);
            long timeStamp = MatchUtility.convertLogTime(wordsInLog[0]);
            CombatLogEntryEntity.Type type = CombatLogEntryEntity.Type.ITEM_PURCHASED;
            String hero = MatchUtility.getHero(wordsInLog[CombatLogConstants.BUYS_HERO]);
            String item = MatchUtility.getItem(wordsInLog[CombatLogConstants.BUYS_ITEM]);

            combatLogEntryEntity.setMatch(match);
            combatLogEntryEntity.setTimestamp(timeStamp);
            combatLogEntryEntity.setType(type);
            combatLogEntryEntity.setActor(hero);
            combatLogEntryEntity.setItem(item);

            return combatLogEntryEntity;
        } else if (line.contains(CombatLogConstants.KEY_KILLED) && line.contains(CombatLogConstants.HERO_PREFIX)) {
            // eg: [00:11:17.489] npc_dota_hero_snapfire is killed by npc_dota_hero_mars

            String [] wordsInLog = line.split(CombatLogConstants.SPACE);
            long timeStamp = MatchUtility.convertLogTime(wordsInLog[0]);
            CombatLogEntryEntity.Type type = CombatLogEntryEntity.Type.HERO_KILLED;
            String target = MatchUtility.getHero(wordsInLog[CombatLogConstants.KILLED_TARGET]);
            String hero = MatchUtility.getHero(wordsInLog[CombatLogConstants.KILLED_HERO]);

            if(StringUtils.isBlank(hero) || StringUtils.isBlank(target)){
                return null;
            }

            combatLogEntryEntity.setMatch(match);
            combatLogEntryEntity.setTimestamp(timeStamp);
            combatLogEntryEntity.setType(type);
            combatLogEntryEntity.setActor(hero);
            combatLogEntryEntity.setTarget(target);

            return combatLogEntryEntity;
        } else if (line.contains(CombatLogConstants.KEY_CASTS) && line.contains(CombatLogConstants.HERO_PREFIX)) {
            // eg: [00:11:19.622] npc_dota_hero_pangolier casts ability pangolier_swashbuckle (lvl 1) on dota_unknown

            String [] wordsInLog = line.split(CombatLogConstants.SPACE);
            long timeStamp = MatchUtility.convertLogTime(wordsInLog[0]);
            CombatLogEntryEntity.Type type = CombatLogEntryEntity.Type.SPELL_CAST;
            String hero = MatchUtility.getHero(wordsInLog[CombatLogConstants.CASTS_HERO]);
            String ability = wordsInLog[CombatLogConstants.CASTS_ABILITY];
            int abilityLevel = MatchUtility.removeBraces(wordsInLog[CombatLogConstants.CASTS_ABILITY_LEVEL]);
            String target = wordsInLog[CombatLogConstants.CASTS_TARGET];

            if(StringUtils.isBlank(hero) ){
                return null;
            }
            combatLogEntryEntity.setMatch(match);
            combatLogEntryEntity.setTimestamp(timeStamp);
            combatLogEntryEntity.setType(type);
            combatLogEntryEntity.setActor(hero);
            combatLogEntryEntity.setTarget(target);
            combatLogEntryEntity.setAbility(ability);
            combatLogEntryEntity.setAbilityLevel(abilityLevel);

            return combatLogEntryEntity;

        } else if (line.contains(CombatLogConstants.KEY_HITS) && line.contains(CombatLogConstants.HERO_PREFIX)) {
            // eg: [00:34:59.195] npc_dota_hero_abyssal_underlord hits npc_dota_hero_death_prophet with abyssal_underlord_firestorm for 94 damage (1643->1549)

            String [] wordsInLog = line.split(CombatLogConstants.SPACE);
            long timeStamp = MatchUtility.convertLogTime(wordsInLog[0]);
            CombatLogEntryEntity.Type type = CombatLogEntryEntity.Type.DAMAGE_DONE;
            String hero = MatchUtility.getHero(wordsInLog[CombatLogConstants.HITS_HERO]);
            String target = MatchUtility.getHero(wordsInLog[CombatLogConstants.HITS_TARGET]);
            String damage = wordsInLog[CombatLogConstants.HITS_DAMAGE];

            if(StringUtils.isBlank(hero) || StringUtils.isBlank(target)){
                return null;
            }
            combatLogEntryEntity.setMatch(match);
            combatLogEntryEntity.setTimestamp(timeStamp);
            combatLogEntryEntity.setType(type);
            combatLogEntryEntity.setActor(hero);
            combatLogEntryEntity.setTarget(target);
            combatLogEntryEntity.setDamage(Integer.valueOf(damage));

            return combatLogEntryEntity;
        }else {
            // not need to split the lines at all
            return null;
        }

    }
}
