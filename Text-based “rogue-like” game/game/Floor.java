package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;

/**
 * A class that represents the floor inside a building.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 2
 * @see Ground
 */

public class Floor extends Ground {
	/**
	 * An object from Actions class.
	 */
	private Actions actions = new Actions();

	/**
	 * An object from PickUpTOSAction
	 */
	private PickUpTOSAction pickUpTOSAction;

	/**
	 * Constructor.
	 *
	 */
	public Floor() {
		super('_');
	}

	/**
	 * A method where tokenofsouls is on the dirt and the player can pick it up.
	 * @param  actor the player
	 * @param destination the location of the item.
	 * @return a new list of actions with pickUpTOSAction.
	 */
	@Override
	public Actions allowableActions(Actor actor, Location destination, String direction) {
//		if(!destination.getItems().isEmpty()){
//			for (Item item : destination.getItems()) {
//				pickUpTOSAction = new PickUpTOSAction((TokenOfSoul) item);
//				if (item.getClass() == TokenOfSoul.class && actor.hasCapability(Abilities.PICKUPTOS)) {
//					actions.add(pickUpTOSAction);
//					return actions;
//				}
//			}
//		}
		return actions;
	}

	/**
	 * A setter that changes the object actions in Actions class.
	 * @param actions The actions object in Actions class.
	 */
	public void setActions(Actions actions) {
		this.actions = actions;
	}

	/**
	 * Check whether the actor can enter the floor or not.
	 * @param actor The actor performing the action.
	 * @return actor has the capability to enter the floor.
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		return actor.hasCapability(Abilities.TOENTERFLOOR);
	}
}
