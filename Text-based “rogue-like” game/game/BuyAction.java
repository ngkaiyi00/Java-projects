package game;

import edu.monash.fit2099.engine.*;
import java.util.List;

/**
 * A class for the player to buy weapons from the FireKeeper.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 2
 * @see Action
 */

public class BuyAction extends Action {

    /**
     * All the weapons exist in the game abstract from Item class.
     */
    private Item item;

    /**
     * Constructor.
     *
     * @param item All the weapon in the game.
     */
    public BuyAction(Item item){
        this.item = item;
    }

    /**
     * @param actor The player performing the action.
     * @return a string of descriptive message
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Player player = (Player) actor;
        Weapon currentWeapon = actor.getWeapon();
        List<Item> items = actor.getInventory();
        String result = "";

        if(this.item.getClass() == Broadsword.class){
            Broadsword broadsword = (Broadsword) item;
            if(player.getSoulCount() >= broadsword.getSoulPrice()){
                for( Item item : items){
                    if(item.asWeapon() != null){
                        player.removeItemFromInventory(item);
                        player.addItemToInventory(broadsword);
                        player.subtractSouls(broadsword.getSoulPrice());
                        result += player + " has bought " + broadsword;
                        return result;
                    }
                }
            }else{
                result += "Insufficient souls, requires extra " + (500- player.getSoulCount()) + " souls";
                return result;
            }
        }else if(this.item.getClass() == GiantAxe.class) {
            GiantAxe giantAxe = (GiantAxe) item;
            if (player.getSoulCount() >= giantAxe.getSoulPrice()) {
                for (Item item : items) {
                    if (item.asWeapon() != null) {
                        player.removeItemFromInventory(item);
                        player.addItemToInventory(giantAxe);
                        player.subtractSouls(giantAxe.getSoulPrice());
                        result += player + " has bought " + giantAxe;
                        return result;
                    }
                }
            }else {
                result += "Insufficient souls, requires extra " + (1000 - player.getSoulCount()) + " souls";
                return result;
            }
        }

        return result;
    }

    /**
     * @param actor The actor performing the action.
     * @return a string showing what weapon has the player bought.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " buys " + item.getClass().getSimpleName();
    }
}
