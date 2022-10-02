package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;

/**
 * A class for pickup action.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 2
 * @see PickUpItemAction
 */

public class PickUpTOSAction extends PickUpItemAction {

    /**
     * An object from tokenOfSoul
     */
    private TokenOfSoul tokenOfSoul;

    /**
     * Constructor.
     *
     * @param tokenOfSoul the tokenOfSoul attributes when the player died.
     */
    public PickUpTOSAction(TokenOfSoul tokenOfSoul){
        super(tokenOfSoul);
        this.tokenOfSoul = tokenOfSoul;
    }

    /**
     * This method is used to execute pickupAction for tokenOfSouls.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return result, a descriptive message that shows the tokenoOfSouls have been picked up by the player.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = "";
        Location here = map.locationOf(actor);

        for(Exit exit : here.getExits()){
            Location location = exit.getDestination();
            if(location.getItems().contains(tokenOfSoul)){
                tokenOfSoul.asSoul().transferSouls(actor.asSoul());
                location.removeItem(tokenOfSoul);
                result += tokenOfSoul + " has been picked up";
            }
        }
        tokenOfSoul.asSoul().transferSouls(actor.asSoul());
        here.removeItem(tokenOfSoul);
//        if(here.getGround().getClass() == Floor.class){
//            Floor floor = (Floor)here.getGround();
//            floor.setActions(new Actions());
//        }else{
//            Dirt dirt = (Dirt)here.getGround();
//            dirt.setActions(new Actions());
//        }
        result += tokenOfSoul + " has been picked up";
        return result;
    }

    /**
     * Descriptive message to describe the player has picked up the tokenOfSoul from the ground.
     * @param actor The player.
     * @return a string of descriptive message
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " picks up the " + tokenOfSoul;
    }
}
