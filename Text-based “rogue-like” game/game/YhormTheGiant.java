package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.Behaviour;
import game.interfaces.Soul;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class for YhormTheGiant.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 2
 * @see Action
 */
public class YhormTheGiant extends LordOfCinder implements Soul {

    /**
     * An array list to store the behaviours of skeleton such as wander and follow behaviour
     */
    private ArrayList<Behaviour> behaviours = new ArrayList<>();

    /**
     * Declaring a follow behaviour object
     */
    private FollowBehaviour followBehaviour;

    /**
     * Generates random number
     */
    private Random rand = new Random();

    /**
     * Check the number of usage.
     */
    private boolean firstTime = true;

    /**
     * the location of giant.
     */
    private Location initLocation;

    /**
     * Constructor.
     * All Undeads are represented by an 'u' and have 30 hit points.
     *
     * @param name the name of this Undead
     */
    public YhormTheGiant(String name, GameMap gameMap, int x, int y) {
        super(name, 'Y', 500);
        this.initLocation = new Location(gameMap,x,y);
        this.setSoulCount(5000);
        this.addItemToInventory(new GreatMachete(this));
        this.addItemToInventory(new CindersOfTheLord(this));
    }


    public Location getInitLocation() {
        return initLocation;
    }

    /**
     * At the moment, we only make it can be attacked by enemy that has HOSTILE capability
     * You can do something else with this method.
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return list of actions
     * @see Status#HOSTILE_TO_ENEMY
     */


    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();
        // it can be attacked only by the HOSTILE opponent, and this action will not attack the HOSTILE enemy back.
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            if (followBehaviour == null) {
                this.followBehaviour = new FollowBehaviour(otherActor);
                behaviours.add(this.followBehaviour);
            }
            if (!otherActor.hasCapability(Status.DISARMED) && otherActor.getWeapon().getClass() != DarkmoonBow.class) {
                actions.add(new AttackAction(this, direction));
            }
            if(otherActor.getWeapon().getClass() == StormRuler.class && StormRuler.getAbility() == Abilities.WINDSLASH){
                actions.add(new StormRulerAction((StormRuler) otherActor.getWeapon(), Abilities.WINDSLASH));
                StormRuler.setStormRulerAction();
            }

        }
        return actions;
    }

    /**
     * Figure out what to do next.
     * FIXME: A Skeleton wanders around at random and it cannot attack anyone.
     *
     * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        if (this.hitPoints < maxHitPoints / 2) {
            this.addCapability(Status.ENRAGED);
            display.println("YHORM THE GIANT HAS ENTERED ENRAGED FORM");
        }
        if (this.hasCapability(Status.STUNNED)) {
            this.removeCapability(Status.STUNNED);
            display.println(this + " is stunned this turn [" + this.hitPoints + "/" + this.maxHitPoints + "]");
            return new DoNothingAction();
        }

        // loop through all behaviours
        if (!actions.getUnmodifiableActionList().isEmpty()) {
            for (Action action : actions.getUnmodifiableActionList()) {
                if (action.getClass() == BurningGroundAction.class) {
                    if (firstTime) {
                        firstTime = false;
                        return action;
                    } else {
                        int random = rand.nextInt(100);
                        if (random >= 85) {
                            return action;
                        }
                    }
                }

                if (action.getClass() == AttackAction.class) {
                    display.println(this.getClass().getSimpleName() + " [" + this.hitPoints + "/" + this.maxHitPoints + "] using" + this.getWeapon());
                    return action;
                }
            }

            for (Behaviour Behaviour : behaviours) {
                if (behaviours.contains(followBehaviour)) {
                    Action action = followBehaviour.getAction(this, map);
                    display.println(this.getClass().getSimpleName() + " [" + this.hitPoints + "/" + this.maxHitPoints + "] using " + this.getWeapon());
                    if (action != null) {
                        return action;
                    }
                }
                Action action = Behaviour.getAction(this, map);
                if (action != null) {
                    return action;
                }
            }
        }return new DoNothingAction();
    }
}
