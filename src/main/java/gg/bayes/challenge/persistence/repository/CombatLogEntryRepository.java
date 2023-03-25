package gg.bayes.challenge.persistence.repository;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItem;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CombatLogEntryRepository extends JpaRepository<CombatLogEntryEntity, Long> {

    // TODO: add the necessary methods for your solution
    @Query(value = "SELECT new gg.bayes.challenge.rest.model.HeroKills(actor, COUNT(id)) FROM CombatLogEntryEntity WHERE match.id=:matchId AND type='HERO_KILLED' Group By actor")
    List<HeroKills> findAllHeroAndKillCount(@Param("matchId") Long matchId);

    List<HeroItem> findItemAndTimestampByMatchIdAndActorAndType(Long matchId, String heroName, CombatLogEntryEntity.Type type);

    @Query(value = "SELECT new gg.bayes.challenge.rest.model.HeroSpells(ability, COUNT(id)) FROM CombatLogEntryEntity WHERE match.id=:matchId AND actor=:heroName AND type='SPELL_CAST' Group By ability")
    List<HeroSpells> fetchCastsSpellAndCount(@Param("matchId")Long matchId, @Param("heroName")String heroName);

    @Query(value = "SELECT new gg.bayes.challenge.rest.model.HeroDamage(target, COUNT(id), SUM(damage)) FROM CombatLogEntryEntity WHERE match.id=:matchId AND actor=:heroName AND type='DAMAGE_DONE' Group By target")
    List<HeroDamage> findHeroAndDamages(@Param("matchId")Long matchId, @Param("heroName")String heroName);
}
