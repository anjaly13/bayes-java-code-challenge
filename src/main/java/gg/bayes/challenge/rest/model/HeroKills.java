package gg.bayes.challenge.rest.model;

import lombok.Value;

@Value
public class HeroKills {
    String hero;
    Long kills;


    public HeroKills(String hero,Long kills){
        this.hero = hero;
        this.kills = kills;
    }
}
