package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;

/**
 * Special Action for attacking other Actors.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 2
 * @see Action
 */

public class StormRulerAction extends Action {
    /**
     * The ability StormRuler weapon
     */
    private Enum ability;

    /**
     * The attributes of storm ruler weapon.
     */
    private StormRuler stormRuler;

    /**
     * Constructor.
     *
     * @param stormRuler the attributes of stormRuler.
     * @param ability the ability of StormRuler weapon.
     */
    public StormRulerAction(StormRuler stormRuler,Enum ability){
        this.ability = ability;
        this.stormRuler = stormRuler;
    }

    /**
     * This method is used to execute the attack instruction when there is an actor within the adjacent spaces of other
     * actors.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return result, a descriptive message.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Location here = map.locationOf(actor);

        String result = "";

        if(this.ability == Abilities.CHARGE ){
            actor.addCapability(Status.DISARMED);
            stormRuler.setActions(new Actions());
            result = "Player is charging";
        }else if(this.ability == Abilities.WINDSLASH){
            int damage = stormRuler.damage() * 2;
            stormRuler.setHitRate(100);
            for(Exit exit : here.getExits()){
                Location destination = exit.getDestination();
                if(map.isAnActorAt(destination)) {
                    if (map.getActorAt(destination).getClass() == YhormTheGiant.class) {
                        YhormTheGiant yhormTheGiant = (YhormTheGiant) map.getActorAt(destination);
                        yhormTheGiant.hurt(damage);
                        result = "Player slashes " + yhormTheGiant + " for " + damage;
                        if (!yhormTheGiant.isConscious()) {
                            yhormTheGiant.asSoul().transferSouls(actor.asSoul());
                            for(Item item: yhormTheGiant.getInventory()){
                                if(item.asWeapon() == null){
                                    map.locationOf(yhormTheGiant).addItem(item);
                                }
                            }
                            map.removeActor(yhormTheGiant);
                            StormRuler.setSkill(Abilities.CHARGE);
                            stormRuler.setActions(new Actions());
                            return "LORD OF CINDER FALLEN";
                        }
                        map.getActorAt(destination).addCapability(Status.STUNNED);
                    }
                }
            }
            StormRuler.setSkill(Abilities.CHARGE);
            stormRuler.setActions(new Actions());
        }
        return result;
    }

    /**
     * Descriptive message to describe the actor used the Storm Ruler's ability.
     * @param actor The actor performing the action.
     * @return a string of descriptive message.
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Unkindled (Player) use StormRuler (" + ability + ")";
    }
}
