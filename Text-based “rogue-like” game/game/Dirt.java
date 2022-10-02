package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;

/**
 * A class that represents bare dirt.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 2
 * @see Action
 */

public class Dirt extends Ground {
    /**
     * The object of the Actions class.
     */
	private Actions actions = new Actions();

	/**
     * The object of the PickUpToSAction class.
     */
	public PickUpTOSAction pickUpTOSAction;

	/**
     * The object of the SwapWeaponAction class.
     */
	private SwapWeaponAction swapWeaponAction;

    /**
	 * Constructor.
	 *
	 */
	public Dirt() {
		super('.');
	}

    /**
	 * Check whether the item on the floor is a token of souls or weapons if it is a tokenofsoul
	 *the then pickUpTOSAction is added into actions list else it will be swapWeaponAction. If there are no item
	 * drop on the floor no action will be added into the actions list.
	 * @param actor The actor performing the action.
	 * @param destination The location where the item at.
	 * @return a new action is added to the actions list or no action is added.
	 */
	@Override
	public Actions allowableActions(Actor actor, Location destination, String direction) {
		Actions actions = new Actions();
		if(!destination.getItems().isEmpty()){
			for (Item item : destination.getItems()) {
				if(item.getClass() == TokenOfSoul.class && actor.hasCapability(Abilities.PICKUPTOS)) {
					pickUpTOSAction = new PickUpTOSAction((TokenOfSoul) item);
					actions.add(pickUpTOSAction);
					return actions;
				} else if(item.getClass() == StormRuler.class && actor.hasCapability(Abilities.PICKUPSTORMRULER)){
					//if its not token of soul its storm ruler
					if(swapWeaponAction == null) {
						swapWeaponAction = new SwapWeaponAction(item);
						actions.add(swapWeaponAction);
					}
					return actions;
				}
			}
		}
		return actions;
	}

    /**
     * A setter that changes the object actions in Actions class.
     * @param actions The actions object in Actions class.
     */
	public void setActions(Actions actions) {
		this.actions = actions;
	}
}
