package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.LastBonFire;
import game.enums.Status;
import game.interfaces.Soul;


/**
 * Class representing the Player.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 2
 * @see Actor
 */

public class Player extends Actor implements Soul {

	/**
	 * The menu for the game
	 */
	private final Menu menu = new Menu();

	/**
	 * The soulCount within the player.
	 */
	private int soulCount = 0;

	/**
	 * The player's location
	 */
	private Location prevLocation;

	/**
	 * The Attributes of estusFlask.
	 */
	private EstusFlask estusFlask = new EstusFlask(this);

	/**
	 * The respawnLocation
	 */
	private Location respawnLocation;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		this.addCapability(Status.HOSTILE_TO_ENEMY);
		this.addCapability(Abilities.REST);
		this.addCapability(Abilities.PICKUPTOS);
		this.addItemToInventory(estusFlask);
		this.addItemToInventory(new Broadsword());
		this.addCapability(Abilities.TOENTERFLOOR);
		this.addCapability(Abilities.BUY);
		this.addCapability(Abilities.PICKUPSTORMRULER);
		this.addCapability(Abilities.TOENTERVALLEY);
		this.addCapability(Abilities.TOENTERFOGDOOR);
		this.addCapability(Abilities.OPENCHEST);
	}

	/**
	 * The setter for soulCount where changes the value of soulCount.
	 * @param soulCount to change soulcount
	 */
	public void setSoulCount(int soulCount) {
		this.soulCount = soulCount;
	}

	/**
	 * Gets the soul count of the player
	 * @return soulcount
	 */
	public int getSoulCount() {
		return soulCount;
	}

	/**
	 * The getter for player max hp
	 * @return player's max hp
	 */
	public int getMaxHitPoints(){
		return maxHitPoints;
	}

	/**
	 * The getter for estusFlask
	 * @return estusFlask's Attributes
	 */
	public EstusFlask getEstusFlask() {
		return estusFlask;
	}

	/**
	 * Gets the allowable actions that the other actor can perform unto player
	 * @param otherActor the Actor that might be performing attack
	 * @param direction  String representing the direction of the other Actor
	 * @param map        current GameMap
	 * @return returns an attack action if there is hostile enemy nearby
	 */
	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		Actions actions = new Actions();
		// it can be attacked only by the HOSTILE opponent, and this action will not attack the HOSTILE enemy back.
		if(otherActor.hasCapability(Status.HOSTILE_TO_PLAYER_ONLY) && !otherActor.hasCapability(Status.STUNNED)){
			actions.add(new AttackAction(this, direction));
		}else{
			actions.add(new DoNothingAction());
		}
		return actions;
	}

	/**
	 * Plays the actor's current turn
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return the console menu
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		Location here = map.locationOf(this);

		if(this.getWeapon().getClass() == DarkmoonBow.class) {
			NumberRange xs, ys;
			xs = new NumberRange(here.x() - 3, 7);
			ys = new NumberRange(here.y() - 3, 7);

			for (int x : xs) {
				if (x < 0 || x > 79) {
					break;
				}
				for (int y : ys) {
					if (y < 0 || y > 25) {
						break;
					}
					if (map.isAnActorAt(map.at(x, y)) && map.getActorAt(map.at(x, y)).hasCapability(Status.HOSTILE_TO_PLAYER_ONLY)) {
						Actor target = map.getActorAt(map.at(x, y));
						RangedAttackAction rangedAttackAction = new RangedAttackAction(target);
						Action action = rangedAttackAction.getAction(this, map);
						if (action != null) {
							actions.add(action);
						}
					}

				}
			}
		}

		if(StormRuler.getAbility() == Abilities.WINDSLASH){
			display.println("Unkindled " + "(" + this.hitPoints + "/" + this.maxHitPoints + "), " + "holding " + this.getWeapon() + "(FULLY CHARGED), " + this.getSoulCount() + " souls.");
		}
		if(this.hasCapability(Status.DEAD)){
			this.removeCapability(Status.DEAD);
		}else if(this.hasCapability(Status.DISARMED)){
			if(StormRuler.getCharge() < 3) {
				StormRuler.setCharge(StormRuler.getCharge() + 1);
			}
			display.println("Storm ruler is charging for round" + (StormRuler.getCharge()));
			if(StormRuler.getCharge() == 3){
				display.println("Unkindled " + "(" + this.hitPoints + "/" + this.maxHitPoints + "), " + "holding " + this.getWeapon() + "(charging last round), " + this.getSoulCount() + " souls.");
			}
			for(Exit exit : here.getExits()){
				Location destination = exit.getDestination();
				if(map.isAnActorAt(destination) && map.getActorAt(destination).getClass() == YhormTheGiant.class){
					if(StormRuler.getCharge() == 3) {
						StormRuler.setSkill(Abilities.WINDSLASH);
						StormRuler.setCharge(0);
						this.removeCapability(Status.DISARMED);
					}
				}
			}
		}

		if(map.locationOf(this).getGround().getClass() == Valley.class ){
			hurt(10000);
			this.addCapability(Status.DEAD);
			TokenOfSoul tokenOfSoul = new TokenOfSoul("tokenOfSoul", this, 0);
			this.asSoul().transferSouls(tokenOfSoul.asSoul());
			prevLocation.addItem(tokenOfSoul);
			if(!this.isConscious()){
				map.moveActor(this,map.at(38,11));
				display.println("YOU DROPPED INTO THE VALLEY");
				display.println("YOU ARE DEAD AND YOU WILL BE RESPAWNED AT THE BONFIRE");
				display.println("Unkindled " + "(" + 0 + "/" + this.maxHitPoints + "), " + "holding " + this.getWeapon() + ", " + this.getSoulCount() + " souls.");
				this.heal(100000);
				this.getEstusFlask().setCharge(3);
				return new DoNothingAction();
			}
		}


		if (lastAction.getNextAction() != null){
			return lastAction.getNextAction();}

		prevLocation = map.locationOf(this);

		// return/print the console menu
		if(this.getWeapon().getClass() == StormRuler.class){
			if(StormRuler.getCharge() > 0 && StormRuler.getCharge() != 3){
				display.println("Unkindled " + "(" + this.hitPoints + "/" + this.maxHitPoints + "), " + "holding " + this.getWeapon() + "(charging) , " + this.getSoulCount() + " souls.");
			}
			else{
				display.println("Unkindled " + "(" + this.hitPoints + "/" + this.maxHitPoints + "), " + "holding " + this.getWeapon() + ", " + this.getSoulCount() + " souls.");
			}
		}else {
			display.println("Unkindled " + "(" + this.hitPoints + "/" + this.maxHitPoints + "), " + "holding " + this.getWeapon() + ", " + this.getSoulCount() + " souls.");
		}
		display.println("Estus Flask Charges: " + estusFlask.getCharge() + "/3");


		return menu.showMenu(this, actions, display);
	}



	/**
	 * Transfers the soul from a soul object to another
	 * @param soulObject a target souls.
	 */
	@Override
	public void transferSouls(Soul soulObject) {
		soulObject.addSouls(soulCount);
		this.setSoulCount(0);
	}

	/**
	 * Adds the souls of the player
	 * @param souls number of souls to be incremented.
	 * @return a boolean value to indicate if addition is successful
	 */
	@Override
	public boolean addSouls(int souls) {
		boolean success = false;
		if(souls != 0){
			soulCount += souls;
			success = true;
		}
		return success;
	}

	/**
	 * substract the souls of the player
	 * @param souls number of souls to be reduced.
	 * @return a boolean value to indicate if reduction is successful.
	 */
	@Override
	public boolean subtractSouls(int souls) {
		boolean success = false;
		if(souls != 0){
			soulCount -= souls;
			success = true;
		}
		return success;
	}
}
