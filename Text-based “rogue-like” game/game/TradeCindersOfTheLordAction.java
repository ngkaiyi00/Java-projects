package game;

import edu.monash.fit2099.engine.*;

import java.util.List;

public class TradeCindersOfTheLordAction extends Action {

    /**
     * All the weapons exist in the game abstract from Item class.
     */
    private Item item;

    /**
     * Constructor.
     *
     * @param item All the weapon in the game.
     */
    public TradeCindersOfTheLordAction(Item item){
        this.item = item;
    }

    /**
     * @param actor The player performing the action.
     * @return a string of descriptive message
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Player player = (Player) actor;
        List<Item> items = actor.getInventory();
        String result = "";

        if(this.item.getClass() == CindersOfTheLord.class) {
            CindersOfTheLord cindersOfTheLord = (CindersOfTheLord) item;
            if (cindersOfTheLord.lordOfCinder.getClass() == YhormTheGiant.class) {
                for (Item item : items) {
                    if (item.asWeapon() != null) {
                        player.removeItemFromInventory(item);
                        player.addItemToInventory(new GreatMachete(player));
                        player.removeItemFromInventory(cindersOfTheLord);
                        result += "Player has traded cinders of the lord for Great Machete";
                        break;
                    }
                }
            }else{
                for (Item item : items) {
                    if (item.asWeapon() != null) {
                        player.removeItemFromInventory(item);
                        player.addItemToInventory(new DarkmoonBow(player));
                        player.removeItemFromInventory(cindersOfTheLord);
                        result += "Player has traded cinders of the lord for Dark Moon Bow";
                        break;
                    }
                }
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
        CindersOfTheLord weapon = (CindersOfTheLord) item;
        return actor + " trades " + item.getClass().getSimpleName() + " for " + weapon.lordOfCinder.getWeapon().getClass().getSimpleName();
    }
}

