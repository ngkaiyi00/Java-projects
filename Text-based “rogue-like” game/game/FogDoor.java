package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;

/**
 * Class representing the fogdoor that teleports player to new map
 *
 * @author Jerrold Wong Youn Zhuet
 * @version 1
 * @see Ground
 */
public class FogDoor extends Ground {

    /**
     * The object of the Actions class.
     */
    private Actions actions = new Actions();

    /**
     * An object from NewMapAction
     */
    private MoveActorAction moveActorAction;

    /**
     * Constructor.
     *
     */
    public FogDoor() {
        super('=');
    }

    /**
     * A method where that returns MoveActorAction if actor has capability to enter fog door.
     * @param  actor the player
     * @param destination the location of the item.
     * @return a new list of actions with MoveActorAction.
     */
    @Override
    public Actions allowableActions(Actor actor, Location destination, String direction) {
        return actions;
    }

    /**
     * Method is used in application to add moveActorAction into actions list
     * @param action the action of moveActor to a specific location in the map.
     */
    public void addAction(Action action){
        this.actions.add(action);
    }


    /**
     * Check whether the actor can enter the fog door or not.
     * @param actor The actor performing the action.
     * @return actor has the capability to enter the fog door.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return actor.hasCapability(Abilities.TOENTERFOGDOOR);
    }
}
