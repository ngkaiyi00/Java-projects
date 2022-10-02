package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;
import game.interfaces.Soul;

/**
 * A class for all the enemy in the game.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 2
 * @see Actor
 */

public class Enemy extends Actor implements Soul {
    /**
     * The variable which keeps track of the soul of an enemy
     */
    private int soulCount;

    /**
     * Constructor
     * @param name the name of the enemy
     * @param displayChar the display character of enemy on map
     * @param hitPoints the starting/maximum HP of an enemy
     */
    public Enemy(String name, char displayChar, int hitPoints){
        super(name, displayChar, hitPoints);
        this.addCapability(Status.HOSTILE_TO_PLAYER_ONLY);
    }

    /**
     * Gets the soul count of enemy
     * @return soul count of the enemy
     */
    public int getSoulCount() {
        return soulCount;
    }

    /**
     * Sets the soul count of an enemy
     * @param soulCount updates the value of soul count
     */
    public void setSoulCount(int soulCount) {
        this.soulCount = soulCount;
    }

    /**
     * Figure out what to do next.
     * Loops through the list of behaviours and action list.
     * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return return the action to be played
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        return null;
    }

    /**
     * Transfers the soul from a soul object to another
     * @param soulObject a target souls.
     */
    @Override
    public void transferSouls(Soul soulObject) {
        soulObject.addSouls(soulCount);
    }

}
