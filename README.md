# Bayes Java Dota Challlenge

This is the [task](TASK.md).

Any additional information about your solution goes here.

## Project Information
Tools Used: Spring boot 2.7.0, Java 11, Maven, H2, intellij IDE

Urls required:
- Swagger - http://localhost:8080/swagger-ui   (Only available after application start up)
- H2 database console - http://localhost:8080/h2-console   (Only available after application start up)

## Task's Todo
- Capture all items purchased by match hero (keyword: 'buys')
- Capture all heroes killing each other  (keyword: 'killed')
- Capture all the spells and the level of spell being cast by heroes  (keyword: 'casts')
- Capture the damages being done to a hero (keyword: 'hits')
- Create the rest APIs for collecting and querying the details captured
- Write the unit test cases

points to remember:
> There are other non-hero characters in the game. All other events can be ignored.
> Hero name prefix should be 'npc_dota_hero_' and 
> Item name prefix should be 'item_'


## Task Completed Details:

> POST /api/match 
End point to receive the log events, and process it.
Business Logic: 
- Collect all the logs and split it line by line
- Iterate through the logs and serach for the key words required

Assumption:
All logs are formed in a structure way
eg:
- logs with keyword 'buys'
[00:08:46.693] npc_dota_hero_snapfire buys item item_clarity
   > [Timestamp] [hero name] buys Item [item name]
- logs with keyword 'hits'
[00:34:58.192] npc_dota_hero_abyssal_underlord hits npc_dota_hero_death_prophet with abyssal_underlord_firestorm for 78 damage (1807->1729)
   > [Timestamp] [hero name] hits [target] with [ability/ item] for [damage count] damage [damge count drop]
- logs with keyword 'casts'
[00:35:10.300] npc_dota_hero_bane casts ability bane_nightmare (lvl 4) on npc_dota_hero_dragon_knight
   > [Timestamp] [hero name] casts ability [ability name] [ability level count] on [target]
- logs with keyword 'killed'
[00:35:07.423] npc_dota_hero_snapfire is killed by npc_dota_hero_bloodseeker
   > [Timestamp] [target name] is killed by [hero name]


- If the line contains any of these key words, the string will be splitted based on the spaces in between
- since the logs are sturcture, can search for each details by index
- Purchase related details are captured on if it is of a hero. This is identified by prefix in the hero name
- Kill details are captured only if both hero and target actors having the hero name prefix in their names
- Casts spelled by the heroes are captured
- Hits between heroes are captured
- After collecting all the combat log details into the CombatLogEntryEntity including the Match details, the details will be saved in H2.
- The saved match id will be returned in the API

> GET /api/match/$match_id
- Query H2 with the match id and the keyword type, group the details based on the hero name
- Collect the results into a list and returned

> GET /api/match/$match_id/$hero_name/items
- Query H2 based on the match id, hero name and the keyword type. 
- Collect the results into a list and returned

> GET /api/match/$match_id/$hero_name/spells
- Query all the casts spelled by a hero in a match based on the, match id, hero name and the keyword type
- Collect the results into a list and returned

> GET /api/match/$match_id/$hero_name/damage
- Query the all the damages caused by heroes each other based on the match id, hero name and the keyword type
- Collect the results into a list and returned

> Test Cases
Test cases are written for the controller, services and the helper component classes




