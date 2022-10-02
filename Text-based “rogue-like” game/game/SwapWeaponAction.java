package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;

import java.util.List;

/**
 * An action to swap weapon (new weapon replaces old weapon).
 * It loops through all items in the Actor inventory.
 * The old weapon will be gone.
 * TODO: you may update this code if required.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 2
 * @see PickUpItemAction
 *
 */

public class SwapWeaponAction extends PickUpItemAction {

    /**
     * The weapon created abstract from Item class.
     */
    private Item weapon;

    /**
     * Constructor
     *
     * @param weapon the new item that will replace the weapon in the Actor's inventory.
     */
    public SwapWeaponAction(Item weapon){
        super(weapon);
        this.weapon = weapon;
    }

    /**
     * This method is used to execute the swapWeapon action implemented by the player.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return result, a descriptive message showing what weapon has the player swapped.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Weapon currentWeapon = actor.getWeapon();
        List<Item> items = actor.getInventory();

        // loop through all inventory
        for(Item item : items){
            if(item.asWeapon() != null){
                actor.removeItemFromInventory(item);
                break; // after it removes that weapon, break the loop.
            }
        }
        String result = "";
        Location here = map.locationOf(actor);

        for(Exit exit : here.getExits()){
            Location location = exit.getDestination();
            if(location.getItems().contains(item)){
                location.removeItem(item);
                Dirt dirt = (Dirt)location.getGround();
                dirt.setActions(new Actions());
                result += item + " has been picked up";
            }
        }


        // if the ground has item, remove that item.
        // additionally, add new weapon to the inventory (equip).
        super.execute(actor, map);
        return actor + " swaps " + currentWeapon + " with " + item;
    }

}
