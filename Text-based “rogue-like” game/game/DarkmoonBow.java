package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;

import java.util.List;

/**
 * Class representing the weapon used by Aldrich, Darkmoon Longbow.
 *
 * @author Jerrold Wong Youn Zhuet
 * @version 1
 * @see edu.monash.fit2099.engine.Weapon
 */
public class DarkmoonBow extends WeaponItem {
    /**
     * to signify that this is a ranged weapon
     */
//    private Enum ranged = Abilities.RANGEDWEAPON;

    /**
     * The object from RangedAttackAction class.
     */
    private RangedAttackAction rangedAttackAction;

    /**
     * The object from Actions class.
     */
    private Actions actions = new Actions();

    /**
     * Constructor
     * @param actor that wields the weapon
     */
    public DarkmoonBow(Actor actor) {
        super("DarkMoon Longbow", 'D', 70, "shoots", 100);
//        actor.addCapability(ranged);
//        this.addCapability(ranged);
    }

    /**
     * Add StormRulerAction object into the actions List.
     * @return a new list of actions with StormRulerAction in it.
     */
    @Override
    public List<Action> getAllowableActions() {
//        Location here = actor.
        if ( rangedAttackAction == null) {

        }
        return actions.getUnmodifiableActionList();
    }

    @Override
    public DropItemAction getDropAction(Actor actor) {
        return null;
    }
}
