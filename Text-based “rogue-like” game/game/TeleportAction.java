package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;

/**
 * Class representing the BonFire.
 *
 * @author Ng Kai Yi
 * @author Edmund Tai Weng Chen
 * @version 3
 * @see Action
 */

public class TeleportAction extends Action{

    /**
     * Bonfire's attributes.
     */
    private final Bonfire bonfire;

    /**
     * The lastBonfire enum interact with.
     */
    private final Location position;

    /**
     * Bonfire's name
     */
    private final String teleportDestination;


    /**
     * Constructor.
     *
     * @param bonfire The bonfire's attributes.
     * @param position the bonfire's position.
     * @param teleportDestination the bonfire's name
     */
    public TeleportAction(Bonfire bonfire, Location position, String teleportDestination){
        this.bonfire = bonfire;
        this.position = position;
        this.teleportDestination = teleportDestination;

    }

    /**
     * This method is used to execute where the player teleport to another bonfires on another map.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return result, a descriptive message shows that the actor teleport to another bonfire.
     */
    @Override
    public String execute(Actor actor,GameMap map){
        map.moveActor(actor, position);
        this.bonfire.removeCapability(Status.CANT_TELEPORT);
        return actor + "teleport to " + teleportDestination + "'s Bonfire";
    }

    /**
     * Descriptive message to describe the actor teleport to the bonfire.
     * @param actor The actor performing the action.
     * @return a string of descriptive message
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " moves to " + teleportDestination  + "'s Bonfire";

    }
}
