package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;

/**
 * An action where the player get burn and hitpoints reduced.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 2
 * @see Action
 */

public class BurningGroundAction extends Action {

    /**
     * GreatMachete weapon attributes.
     */
    protected GreatMachete greatMachete;

    /**
     * Constructor.
     *
     * @param greatMachete The greatMachete's Attributes.
     */
    public BurningGroundAction(GreatMachete greatMachete){
        this.greatMachete = greatMachete;
    }

    /**
     * Create an ArrayList for burningLocation.
     */
    private ArrayList<Location> burningLocation = new ArrayList<>();

    /**
     * This method is used to execute the dirt ground changes to ember.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a descriptive message that tells the player the ground is getting burned.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Location here = map.locationOf(actor);
        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (destination.getGround().getClass() == Dirt.class) {
                destination.setGround(new BurningGround(actor));
                burningLocation.add(destination);

            }
        }return actor.getClass().getSimpleName() + " burnt its surrounding dirt.";
    }

    /**
     * This action is not done by the player, so return null.
     * @param actor The actor performing the action.
     * @return null
     */
    @Override
    public String menuDescription(Actor actor) {
        return "BURN SURROUNDING [GREAT MACHETE]";
    }
}
