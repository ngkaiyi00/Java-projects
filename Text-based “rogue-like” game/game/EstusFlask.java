package game;

import edu.monash.fit2099.engine.*;

import java.util.List;

/**
 * Class for EstusFlask.
 *
 * @author Ng Kai Yi
 * @author Edmund Tai Weng Chen
 * @version 2
 * @see Item
 */

public class EstusFlask extends Item {
    /**
     * The maximum usage of EstusFlask.
     */
    private final int MAXCHARGE = 3;

    /**
     * The number of usage of EstusFlask.
     */
    private int charge;

    /**
     * The player in the game.
     */
    protected Player player;

    /**
     * The object of actions from Actions class.
     */
    protected Actions actions = new Actions();

    /**
     * The object from DrinkItemAction class.
     */
    private DrinkItemAction drinkItemAction;


    /**
     * Constructor.
     *
     * @param player the player in the game.
     */
    public EstusFlask(Player player) {
        super("EstusFlask", 'E', true);
        this.player = player;
        this.charge = MAXCHARGE;

    }

    /**
     * Add drinkItemAction object into the actions List.
     * @return a new list of actions with rest action in it.
     */
    @Override
    public List<Action> getAllowableActions() {
        if(drinkItemAction == null){
            this.drinkItemAction = new DrinkItemAction(this);
            actions.add(drinkItemAction);
        }return actions.getUnmodifiableActionList();
    }

    /**
     * The player cannot drop EstusFlask on the floor.
     * @param actor the player.
     * @return nothing
     */
    @Override
    public DropItemAction getDropAction(Actor actor) {
        return null;
    }

    /**
     * A getter for the charge of EstusFlask.
     * @return the charge of estusFlask.
     */
    public int getCharge() {
        return charge;
    }

    /**
     * A setter for the charge of EstusFlask.
     * @param charge the number of usage for estusFlask.
     */
    public void setCharge(int charge) {
        this.charge = charge;
    }

    /**
     * A method that uses the estusFlask and it will heal the player's hitpoint.
     * @return true or false based on the number of usage of estusFlask.
     */
    public boolean useCharges(){
        boolean result = false;
        if (this.charge > 0){
            int heal_hp = 40;
            player.heal(heal_hp);
            this.charge -=1;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Descriptive message that tells the number of usage left for estusFlask.
     * @return the charge of estusFlask.
     */
    @Override
    public String toString() {return "EstusFlask's charges: " + this.charge + "/" + this.MAXCHARGE; }

}