package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.DropItemAction;
import game.enums.Status;

import java.util.List;

/**
 * Special Action for attacking other Actors.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 2
 * @see MeleeWeapon
 */

public class GreatMachete extends MeleeWeapon{
    /**
     * The player
     */
    private Actor actor;

    /**
     * An action object from Actions class
     */
    Actions actions = new Actions();

    /**
     * The Attributes of BurningGround object.
     */
    BurningGroundAction burningGroundAction;

    /**
     * Constructor.
     *
     * @param actor the Actor use the GreatMachete
     */
    public GreatMachete(Actor actor) {
        super("GreatMachete", 'M', 95, "Slash", 60);
        this.actor = actor;
    }

    /**
     * Add BurnignGroundaction object into the actions List.
     * @return a new list of actions with burningGroundaction in it.
     */
    @Override
    public List<Action> getAllowableActions() {
        if (burningGroundAction == null) {
            if (actor.hasCapability(Status.ENRAGED) || actor.getClass() == Player.class) {
                this.burningGroundAction = new BurningGroundAction(this);
                actions.add(burningGroundAction);

            }
        }return actions.getUnmodifiableActionList();
    }

    /**
     * change the hitrate of the GreatMachete.
     * @param hitRate The damage deal by the GreatMachete.
     */
    public void setHitRate(int hitRate){
        this.hitRate = hitRate;
    }

    @Override
    public DropItemAction getDropAction(Actor actor) {
        return null;
    }
}
