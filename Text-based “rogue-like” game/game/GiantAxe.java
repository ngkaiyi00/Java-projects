package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import game.enums.Abilities;

import java.util.List;

import game.enums.Abilities;

/**
 * The weapon class for GiantAxe
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 2
 * @see MeleeWeapon
 */

public class GiantAxe extends MeleeWeapon{
    /**
     * The soulprice of the GiantAxe.
     */
    private int soulPrice = 1000;

    /**
     * The object from Actions class.
     */
    protected Actions actions = new Actions();

    /**
     * The ability of GiantAxe.
     */
    private SpinAttackAction spinAttackAction;

    /**
     * The Actor that is to be attacked
     */
    private Actor target;

    /**
     * Constructor.
     *
     */
    public GiantAxe() {
        super("GiantAxe", 'A', 50, "hits", 80);
        this.addCapability(Abilities.CASTSPIN);
    }

    /**
     * Add spinAttackAction object into the actions List.
     * @return a new list of actions with spinAttackAction in it.
     */
    @Override
    public List<Action> getAllowableActions() {
        if(spinAttackAction == null){
            this.spinAttackAction = new SpinAttackAction(this);
            actions.add(spinAttackAction);
        }return actions.getUnmodifiableActionList();
    }

    /**
     * Gets the soulprice of the GiantAxe.
     * @return soulprice of GiantAxe.
     */
    public int getSoulPrice() {
        return soulPrice;
    }
}
