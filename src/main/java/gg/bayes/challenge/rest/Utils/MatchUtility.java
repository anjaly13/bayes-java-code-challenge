package gg.bayes.challenge.rest.Utils;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MatchUtility {

    private MatchUtility(){}

    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT);
    private static final SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Convert the time in string to timestamp based milliseconds
     *
     * @param timeInString event log time
     * @return milliseconds version of the event log time
     * @throws ParseException exception occur while parsing
     */
    public static long convertLogTime(String timeInString) throws ParseException {
        if(StringUtils.isBlank(timeInString)){ // a regex may also be added for the timestamp check
            return 0l;
        }
        String removedTheBraces = timeInString.substring(1,timeInString.length()-1);
        String timeStamp = simpleDateFormatDate.format(new java.util.Date());
        return Timestamp.valueOf(timeStamp +" " + removedTheBraces).getTime();
    }

    /**
     * Remove the prefix from the hero name, collected from the logs
     *
     * @param heroName Hero name from the log with prefix
     * @return hero name without prefix
     */
    public static String getHero(String heroName) {
        if(StringUtils.isBlank(heroName) || !heroName.contains("npc_dota_hero_")){ // a regex may also be added for the timestamp check
            return "";
        }
        return heroName.split("npc_dota_hero_")[1];
    }

    /**
     * Remove the item name prefix, collected from the event log
     *
     * @param itemName item name present in teh logs with prefix
     * @return item name without prefix
     */
    public static String getItem(String itemName) {
        if(StringUtils.isBlank(itemName) || !itemName.contains("item_")){ // a regex may also be added for the timestamp check
            return "";
        }
        return itemName.split("item_")[1];
    }

    /**
     * Remove the extra braces form the cast level, collected from the event log
     *
     * @param string cast level string contains extra braces to be removed
     * @return integer value of the cast level string after braces removed
     * @throws ParseException exception occur while parsing
     */
    public static Integer removeBraces(String string) throws ParseException {
        if(StringUtils.isBlank(string)){ // a regex may also be added for the timestamp check
            return 0;
        }
        String val = string.substring(0,string.length()-1);
        return Integer.valueOf(val);
    }
}
