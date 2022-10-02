package game;
import edu.monash.fit2099.engine.*;
import game.enums.LastBonFire;
import game.enums.Status;

/**
 * An activate Action where player light up the BonFire when he is near to the bonfire.
 *
 * @author Edmund Tai Weng Chen
 * @version 2
 * @see Action
 */
public class ActivateBonFireAction extends Action{

    /**
     * Bonfire's attributes.
     */
    private final Bonfire bonfire;

    /**
     * The lastBonfire enum interact with.
     */
    private final Enum<LastBonFire> lastBonFireEnum;

    /**
     * Constructor.
     *
     * @param bonfire The bonfire's attributes.
     * @param lastBonFireEnum the bonfire's enum
     */
    public ActivateBonFireAction(Bonfire bonfire, Enum<LastBonFire> lastBonFireEnum){
        this.bonfire = bonfire;
        this.lastBonFireEnum = lastBonFireEnum;
    }

    /**
     * This method is used to execute the action of light up Bonfire.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return result, a descriptive message shows that the actor has light up the bonfire.
     */
    @Override
    public String execute(Actor actor, GameMap map){
        for (LastBonFire lastBonFire : LastBonFire.values()){
            if(actor.hasCapability(lastBonFire)){
                actor.removeCapability(lastBonFire);
            }
            actor.addCapability(this.lastBonFireEnum);
        }
        this.bonfire.removeCapability(Status.NOT_ACTIVATED);
        return actor + "has Lighted up the " + this.bonfire + "'s Bonfire";
    }

    /**
     * Descriptive message to describe the actor has light up the bonfire.
     * @param actor The actor performing the action.
     * @return a string of descriptive message
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " lights the " + this.bonfire + "'s Bonfire";
    }
}
