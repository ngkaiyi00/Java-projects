package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import java.util.*;
import game.enums.LastBonFire;
import game.enums.Status;

/**
 * Class representing the BonFire.
 *
 * @author Ng Kai Yi
 * @author Edmund Tai Weng Chen
 * @version 3
 * @see Ground
 */

public class Bonfire extends Ground{

    /**
     * Player's action that rest at the BonFire
     */
    private RestAction restAction;

    /**
     *The object of the Actions class
     */
    protected Actions actions = new Actions();

    /**
     *List of available BonFire.
     */
    public static List<Bonfire> bonfire;

    /**
     *Name of the Bonfire
     */
    private final String name;

    /**
     *The location where the player teleport from one bonfire to another.
     */
    private final Location position;

    /**
     *Status of the bonfire.
     */
    private final Enum<LastBonFire> status;

    /**
     * Constructor.
     *
     * @param name the name of the bonfire.
     * @param lastBonFireStatus the previous bonfire the player interacted with.
     * @param position the position of the bonfire.
     */
    public Bonfire(String name, Enum<LastBonFire> lastBonFireStatus, Location position){
        super('B');
        this.name = name;
        this.position = position;
        this.status = lastBonFireStatus;

        if (bonfire == null){
            bonfire = new ArrayList<>();
        }
        bonfire.add(this);
    }

    /**
     * Add RestAction object into the actions List.
     * @return a new list of actions with rest action in it.
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        Actions actions = new Actions();
        if (this.hasCapability(Status.NOT_ACTIVATED)){
            actions.add(new ActivateBonFireAction(this, this.status));
        }
        else {
            if(this.hasCapability(Status.CANT_TELEPORT)){
                actions.add(new RestAction(this, this.status));
            }
            else{
                actions.add(new RestAction(this, this.status));
                for (Bonfire bonfire: bonfire){
                    if (this != bonfire ) {
                        actions.add(new TeleportAction(bonfire, bonfire.position, bonfire.toString()));
                    }
                }
            }
        }
        return actions;
    }

    /**
     * Check whether the actor can enter the BonFire or not.
     * @param actor The actor performing the action.
     * @return actor has an ability to rest at the Bonfire.
     */
    @Override
    public boolean canActorEnter(Actor actor){
        return actor.hasCapability(Abilities.REST);
    }

    /**
     * print the name of the bonfire.
     * @return the name of the bonfire.
     */
    @Override
    public String toString(){ return this.name;}

    /**
     * Get the bonfire's position that last interacted with.
     * @param lastBonFireEnum the bonfire interacted with.
     * @return the position of bonfire.
     */
    public static Location previousLocationOfBonFire(Enum<LastBonFire> lastBonFireEnum){
        for(Bonfire bonfire :bonfire){
            if(bonfire.status == lastBonFireEnum){
                return bonfire.position;
            }
        }
        return null;
    }
}

