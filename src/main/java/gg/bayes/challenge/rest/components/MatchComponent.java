package gg.bayes.challenge.rest.components;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.persistence.model.MatchEntity;
import gg.bayes.challenge.rest.Utils.MatchUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class MatchComponent {

    public CombatLogEntryEntity createCombatLog(CombatLogEntryEntity combatLogEntryEntity, MatchEntity match, String word, String line) throws ParseException {
        if(line.contains("buys") && line.contains("npc_dota_hero")){
            String [] wordsInLog = line.split(" ");
            long timeStamp = MatchUtility.convertLogTime(wordsInLog[0]);
            CombatLogEntryEntity.Type type = CombatLogEntryEntity.Type.ITEM_PURCHASED;
            String hero = MatchUtility.getHero(wordsInLog[1]);
            String item = MatchUtility.getItem(wordsInLog[4]);

            combatLogEntryEntity.setMatch(match);
            combatLogEntryEntity.setTimestamp(timeStamp);
            combatLogEntryEntity.setType(type);
            combatLogEntryEntity.setActor(hero);
            combatLogEntryEntity.setItem(item);

            System.out.println(timeStamp + " " + type.toString() + " "+hero + " " + item);
            return combatLogEntryEntity;
        } else if (line.contains("killed") && line.contains("npc_dota_hero")) { // [00:11:17.489] npc_dota_hero_snapfire is killed by npc_dota_hero_mars
            String [] wordsInLog = line.split(" ");
            long timeStamp = MatchUtility.convertLogTime(wordsInLog[0]);
            CombatLogEntryEntity.Type type = CombatLogEntryEntity.Type.HERO_KILLED;
            String hero = MatchUtility.getHero(wordsInLog[1]);
            String target = MatchUtility.getHero(wordsInLog[5]);

            if(StringUtils.isBlank(hero) || StringUtils.isBlank(target)){
                return null;
            }

            combatLogEntryEntity.setMatch(match);
            combatLogEntryEntity.setTimestamp(timeStamp);
            combatLogEntryEntity.setType(type);
            combatLogEntryEntity.setActor(hero);
            combatLogEntryEntity.setTarget(target);

            System.out.println(timeStamp + " " + type.toString() + " "+hero + " " +target);
            return combatLogEntryEntity;
        } else if (line.contains("casts") && line.contains("npc_dota_hero")) {
            // [00:11:19.622] npc_dota_hero_pangolier casts ability pangolier_swashbuckle (lvl 1) on dota_unknown
            String [] wordsInLog = line.split(" ");
            long timeStamp = MatchUtility.convertLogTime(wordsInLog[0]);
            CombatLogEntryEntity.Type type = CombatLogEntryEntity.Type.SPELL_CAST;
            String hero = MatchUtility.getHero(wordsInLog[1]);
            String ability = wordsInLog[4];
            int abilityLevel = MatchUtility.removeBraces(wordsInLog[6]);
            String target = wordsInLog[8];

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

            System.out.println(timeStamp + " " + type.toString() + " "+hero + " " + target + " " + ability +" " + abilityLevel);
            return combatLogEntryEntity;

        } else if (line.contains("hits") && line.contains("npc_dota_hero")) {
            // [00:34:59.195] npc_dota_hero_abyssal_underlord hits npc_dota_hero_death_prophet with abyssal_underlord_firestorm for 94 damage (1643->1549)
            String [] wordsInLog = line.split(" ");
            long timeStamp = MatchUtility.convertLogTime(wordsInLog[0]);
            CombatLogEntryEntity.Type type = CombatLogEntryEntity.Type.DAMAGE_DONE;
            String hero = MatchUtility.getHero(wordsInLog[1]);
            String target = MatchUtility.getHero(wordsInLog[3]);
            String damage = wordsInLog[7];

            if(StringUtils.isBlank(hero) || StringUtils.isBlank(target)){
                return null;
            }
            combatLogEntryEntity.setMatch(match);
            combatLogEntryEntity.setTimestamp(timeStamp);
            combatLogEntryEntity.setType(type);
            combatLogEntryEntity.setActor(hero);
            combatLogEntryEntity.setTarget(target);
            combatLogEntryEntity.setDamage(Integer.valueOf(damage));

            System.out.println(timeStamp + " " + type.toString() + " "+hero + " " + target +" " + damage);
            return combatLogEntryEntity;
        }else {
            // not need to split the lines at all
            return null;
        }

    }
}
