package game;

import edu.monash.fit2099.engine.*;
import edu.monash.fit2099.engine.addons.DesignOSoulsAddOn;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.Soul;

/**
 * A class for TokenOfSoul where it is dropped by the player when the player died.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 2
 * @see Item
 */

public class TokenOfSoul extends Item implements Soul, DesignOSoulsAddOn {
    /**
     * the soulCount for the tokenOfSoul
     */
    private int soulCount = 0;

    /**
     * The player
     */
    private Actor player;

    /**
     * The object of pickUpTOSAction.
     */
    private PickUpTOSAction pickUpTOSAction = new PickUpTOSAction(this);

    /**
     * Constructor.
     *
     * @param name
     * @param target the player.
     */
    public TokenOfSoul(String name, Actor target,int soulCount){
        super(name, '$', true);
        player = target;
        this.soulCount = soulCount;
    }

    /**
     * Gets the soul count of the player.
     * @return soulCount the number of souls within the player.
     */
    public int getSoulCount() {
        return soulCount;
    }

    /**
     * change the soul count of the player
     * @param souls the number of souls in player.
     * @return boolean
     */
    public boolean setSoulCount(int souls){
        boolean flag = false;
        if(souls >= 0){
            flag = true;
            soulCount = souls;
        }
        return flag;
    }


    /**
     * Transfers the soul to the token of souls.
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
     * Ensure the pickUPSAction method is implemented not getPickUpAction.
     * @param actor The actor performing the action.
     * @return null.
     */
    @Override
    public PickUpItemAction getPickUpAction(Actor actor) {
        if(actor.hasCapability(Abilities.PICKUPTOS)){
//            return pickUpTOSAction;
            return null;
        }return null;
    }

    /**
     * Descriptive message that will be printed out in the console.
     * @return a descriptive message.
     */
    public String toString(){
        return "Token Of Soul";
    }

}
