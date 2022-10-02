package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.LastBonFire;

/**
 * A rest Action where player can rest at the BonFire when he is near to it.
 *
 * @author Ng Kai Yi
 * @author Edmund Tai Weng Chen
 * @version 2
 * @see Action
 */

public class RestAction extends Action{

    /**
     * bonfire name
     */
    private Bonfire bonfire;

    /**
     * Player's Attributes
     */
    protected  Player player;

    /**
     * The last bonfire interacted with.
     */
    private final Enum<LastBonFire> lastBonFireEnum;

    /**
     * the player respawn location.
     */
    private Location respawnLocation;

    /**
     * Constructor.
     *
     * @param bonfire bonfire's Attributes
     * @param lastBonFireEnum the type of bonfire
     */
    public RestAction(Bonfire bonfire,Enum<LastBonFire> lastBonFireEnum){
        this.bonfire = bonfire;
        this.lastBonFireEnum = lastBonFireEnum;
    }

    /**
     * This method is used to execute the RestAction class where the player will undergo soft reset.
     * actors.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return result, a descriptive message of telling the player he is resting at the BonFire.
     */
    @Override
    public String execute(Actor actor, GameMap map){

        String result = "The " + actor;
        //update latest bonfire
        for (LastBonFire lastBonFire : LastBonFire.values()){
            if(actor.hasCapability(lastBonFire)){
                actor.removeCapability(lastBonFire);
            }
            actor.addCapability(this.lastBonFireEnum);
        }

        //get the bonfire's previous location.
        for(LastBonFire lastBonFire: LastBonFire.values()){
            if(actor.hasCapability(lastBonFire)){
                this.respawnLocation = Bonfire.previousLocationOfBonFire((lastBonFire));
            }
        }

        map.moveActor(actor,this.respawnLocation);
        Player player = (Player) actor;
        player.heal(1000);
        player.getEstusFlask().setCharge(3);
        result += " took a good rest at " + this.bonfire + "'s Bonfire." ;
        return result;

    }

    /**
     * Descriptive message to describe the actor is resting at the Bonfire.
     * @param actor The actor performing the action.
     * @return a string of descriptive message
     */
    @Override
    public String menuDescription(Actor actor){
        if (actor.hasCapability(Abilities.REST)){
            return actor + " rest at Bonfire.";
        }
        return null;
    }
}
