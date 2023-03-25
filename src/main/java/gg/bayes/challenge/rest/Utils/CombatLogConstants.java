package gg.bayes.challenge.rest.Utils;

public class CombatLogConstants {
    private CombatLogConstants(){}

    // Keywords used
    public static final String KEY_BUYS = "buys";
    public static final String KEY_KILLED = "killed";
    public static final String KEY_HITS = "hits";
    public static final String KEY_CASTS = "casts";


    // Helpful Constants
    public static final String HERO_PREFIX = "npc_dota_hero";
    public static final String SPACE = " ";


    // Word indices for 'buys' keyword
    public static final Integer BUYS_HERO = 1;
    public static final Integer BUYS_ITEM = 4;


    // Word indices for 'killed' keyword
    public static final Integer KILLED_HERO = 1;
    public static final Integer KILLED_TARGET = 5;


    // Word indices for 'casts' key word
    public static final Integer CASTS_HERO = 1;
    public static final Integer CASTS_ABILITY = 4;
    public static final Integer CASTS_ABILITY_LEVEL = 6;
    public static final Integer CASTS_TARGET = 8;


    // Word indices for 'hits' keyword
    public static final Integer HITS_HERO = 1;
    public static final Integer HITS_TARGET = 3;
    public static final Integer HITS_DAMAGE = 7;


}
