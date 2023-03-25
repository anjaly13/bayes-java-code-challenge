package gg.bayes.challenge.rest.Utils;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MatchUtility {

    private MatchUtility(){}

    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT);

    public static long convertLogTime(String timeInString) throws ParseException {
        if(StringUtils.isBlank(timeInString)){ // a regex may also be added for the timestamp check
            return 0l;
        }
        String removedTheBraces = timeInString.substring(1,timeInString.length()-1);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        return Timestamp.valueOf(timeStamp +" " + removedTheBraces).getTime();
    }

    public static String getHero(String heroName) {
        if(StringUtils.isBlank(heroName) || !heroName.contains("npc_dota_hero_")){ // a regex may also be added for the timestamp check
            return "";
        }
        return heroName.split("npc_dota_hero_")[1];
    }

    public static String getItem(String itemName) {
        if(StringUtils.isBlank(itemName) || !itemName.contains("item_")){ // a regex may also be added for the timestamp check
            return "";
        }
        return itemName.split("item_")[1];
    }

    public static Integer removeBraces(String string) throws ParseException {
        if(StringUtils.isBlank(string)){ // a regex may also be added for the timestamp check
            return 0;
        }
        String val = string.substring(0,string.length()-1);
        System.out.println(val);
        return Integer.valueOf(val);
    }
}
