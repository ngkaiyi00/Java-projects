package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;

import java.util.List;

/**
 * A class for FireKeeper.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 2
 * @see Actor
 */

public class FireKeeper extends Actor {
    /**
     * The player
     */
    protected Actor player;

    /**
     * The broadsword weapon's attributes.
     */
    private Broadsword broadSword = new Broadsword();

    /**
     * The GiantAxe weapon's attributes.
     */
    private GiantAxe giantAxe = new GiantAxe();


    /**
     * The actions object from the Actions class.
     */
    private Actions actions = new Actions();

    /**
     * Constructor.
     *
     * @param player the player
     */
    public FireKeeper(Actor player) {
        super("FireKeeper", 'F', Integer.MAX_VALUE);
        this.player = player;
    }

    /**
     * Add buyAction into actions list with the chosen weapon.
     * @param otherActor the player.
     * @return a new list of actions with buy action that contains one of the weapon in it.
     */
    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();
        if(otherActor.hasCapability(Abilities.BUY)){
            actions.add(new BuyAction(broadSword));
            actions.add(new BuyAction(giantAxe));
        }
        List<Item> items = otherActor.getInventory();
        for(Item item : items){
            if(item.getClass() == CindersOfTheLord.class && ((CindersOfTheLord) item).lordOfCinder.getClass() == YhormTheGiant.class){
                actions.add(new TradeCindersOfTheLordAction(item));
            }else if(item.getClass() == CindersOfTheLord.class && ((CindersOfTheLord) item).lordOfCinder.getClass() == AldrichTheDevourer.class){
                actions.add(new TradeCindersOfTheLordAction(item));
            }
        }
        return actions;
    }

    /**
     * When the player is buying weapons from the firekeeper it will not do anything.
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }
}
