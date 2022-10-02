package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.DropItemAction;
import edu.monash.fit2099.engine.WeaponItem;
import game.enums.Abilities;

/**
 * A class for all the melee weapons.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 2
 * @see WeaponItem
 */

public class MeleeWeapon extends WeaponItem {
    /**
     * Constructor.
     *
     * @param name        name of the item
     * @param displayChar character to use for display when item is on the ground
     * @param damage      amount of damage this weapon does
     * @param verb        verb to use for this weapon, e.g. "hits", "zaps"
     * @param hitRate     the probability/chance to hit the target.
     */
    public MeleeWeapon(String name, char displayChar, int damage, String verb, int hitRate) {
        super(name, displayChar, damage, verb, hitRate);
        this.addCapability(Abilities.MELEEWEAPON);
    }

    /**
     * All weapons cannot be dropped on the floor.
     * @Param actor the player
     * @return null
     */
    @Override
    public DropItemAction getDropAction(Actor actor) {
        return null;
    }


    //TODO: please figure out how to disable dropping item action.
}
