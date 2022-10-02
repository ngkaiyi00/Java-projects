package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.*;

/**
 * Special Action for attacking other Actors.
 *
 * @author Ng Kai Yi
 * @author Edmund Tai Weng Chen
 * @version 2
 * @see Action
 */

public class DrinkItemAction extends Action {

    /**
	 * The estusFlask object in EstusFlask class.
	 */
    protected EstusFlask estusFlask;

    /**
	 * The estusFlask object in EstusFlask class.
	 * @param estusFlask the estusFlask attributes from the EstusFlask class.
   	 */
    public DrinkItemAction(EstusFlask estusFlask){
        this.estusFlask = estusFlask;
    }
    
    /**
	 * This method is used to execute the actions of DrinkItemAction that shows whether
	 * the player has drank the estusflask or not. 
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a descriptive message shows whether the player has used the estusFlask or not.
	 */
    @Override
    public String execute(Actor actor, GameMap map) {
        boolean status = this.estusFlask.useCharges();
        if(status){
            return actor + " consumed " + this.estusFlask.toString();
        }else {
            return actor + " unable to consume " + this.estusFlask.toString() + ",please refill charges.";
        }
    }
    
    /**
	 * Action of consumes EstusFlask is print at the console when the game runs.
	 * @param actor The actor performing the action.
	 * @return a descriptive message that shows player can drink estusflask in the game.
	 */
    @Override
    public String menuDescription(Actor actor){return actor + " consumes EstusFlask";}
}

