package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.Behaviour;
import game.interfaces.Soul;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class representing the Lord of Cinder, Aldrich.
 *
 * @author Jerrold Wong Youn Zhuet
 * @version 1
 * @see LordOfCinder
 */

public class AldrichTheDevourer extends LordOfCinder implements Soul {
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
     * the location of giant.
     */
    private Location initLocation;

    /**
     * ranged attack action variable
     */
    private RangedAttackAction rangedAttackAction;

    /**
     * Constructor.
     * All Undeads are represented by an 'u' and have 30 hit points.
     *
     * @param name the name of this Undead
     */
    public AldrichTheDevourer(String name, GameMap gameMap, int x, int y) {
        super(name, 'A', 350);
        this.initLocation = new Location(gameMap,x,y);
        this.setSoulCount(5000);
        this.addItemToInventory(new DarkmoonBow(this));
        this.addItemToInventory(new CindersOfTheLord(this));
    }

    /**
     * Gets the initial location of aldrich
     * @return initial location
     */
    public Location getInitLocation() {
        return initLocation;
    }

    /**
     * Adds the behaviour to list of behaviours
     * @param behaviour
     */
    public void addBehaviour(Behaviour behaviour){
        behaviours.add(behaviour);
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
            if (rangedAttackAction == null){
                this.rangedAttackAction = new RangedAttackAction(otherActor);
                behaviours.add(rangedAttackAction);
            }
            if (followBehaviour == null) {
                this.followBehaviour = new FollowBehaviour(otherActor);
                behaviours.add(this.followBehaviour);
            }
            if (!otherActor.hasCapability(Status.DISARMED) && otherActor.getWeapon().getClass() != DarkmoonBow.class){
                actions.add(new AttackAction(this, direction));
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
        // loop through all behaviours
        if (!actions.getUnmodifiableActionList().isEmpty()) {
            for (Action action : actions.getUnmodifiableActionList()) {
                if (action.getClass() == AttackAction.class) {
                    display.println(this.getClass().getSimpleName() + " [" + this.hitPoints + "/" + this.maxHitPoints + "] using " + this.getWeapon());
                    return action;
                }
            }

            for (Behaviour Behaviour : behaviours) {
                if (behaviours.contains(rangedAttackAction)) {
                    Action action = rangedAttackAction.getAction(this, map);
                    if (action != null){
                        display.println(this.getClass().getSimpleName() + " [" + this.hitPoints + "/" + this.maxHitPoints + "] using " + this.getWeapon());
                        return action;
                    }
                }
                else if (behaviours.contains(followBehaviour)) {
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
        }
        return new DoNothingAction();
    }


}
