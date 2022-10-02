package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.WeaponItem;
import game.enums.Abilities;

import java.util.List;

/**
 * A class for StormRuler weapon.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 2
 * @see MeleeWeapon
 */


public class StormRuler extends MeleeWeapon {

    /**
     * The ability object in Enum type.
     */
    private static Enum ability;

    /**
     * The object from stormRulerAction class.
     */
    private static StormRulerAction stormRulerAction;

    /**
     * The Actor that is to be attacked.
     */
    private Actor target;

    /**
     * The object from Actions class.
     */
    private Actions actions = new Actions();

    /**
     * The charge for Storm Ruler.
     */
    private static int charge = 0;

    /**
     * Constructor.
     *
     * @param ability the ability of StormRuler.
     */
    public StormRuler(Enum ability){
        super("StormRuler", '7', 70, "slash",60);
        this.ability = ability;
    }

    /**
     * Add StormRulerAction object into the actions List.
     * @return a new list of actions with StormRulerAction in it.
     */
    @Override
    public List<Action> getAllowableActions() {
        if (stormRulerAction == null) {
            if (this.ability == Abilities.CHARGE && charge == 0) {
                this.stormRulerAction = new StormRulerAction(this, Abilities.CHARGE);
                actions.add(stormRulerAction);
            }
//        } else if (this.ability == Abilities.WINDSLASH) {
//
//            actions.clear();
//            this.stormRulerAction = new StormRulerAction(this, Abilities.WINDSLASH);
//            actions.add(stormRulerAction);
        }
        return actions.getUnmodifiableActionList();
    }

    /**
     * The getter for Storm Ruler.
     * @return the number of charge by the player.
     */
    public static int getCharge(){
        return charge;
    }

    /**
     * The setter for Storm Ruler.
     * @param newCharge change the value of variable charge.
     */
    public static void setCharge(int newCharge){
        charge = newCharge;
    }

    /**
     * Implement setter where the skill can be changed.
     * @param skill the skill of Storm ruler.
     */
    public static void setSkill(Enum skill){
        ability = skill;
    }

    /**
     * The setter for changing the hitRate caused by Storm Ruler weapon.
     * @param hitRate the hit rate applied by the Storm Ruler weapon.
     */
    public void setHitRate(int hitRate){
        this.hitRate = hitRate;
    }

    /**
     * Changes the Actions
     * @param actions the actions in the Actions class.
     */
    public void setActions(Actions actions){
        this.actions = actions;
    }

    /**
     * Getter that return the ability.
     * @return the ability of StormRuler.
     */
    public static Enum getAbility(){
        return ability;
    }

    /**
     * change stormruler action.
     * @return stormruler action.
     */
    public static void setStormRulerAction(){
        stormRulerAction = null;
    }

}
