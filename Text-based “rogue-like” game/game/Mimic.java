package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.Behaviour;
import game.interfaces.Soul;

import java.util.ArrayList;
import java.util.Random;

public class Mimic extends Enemy implements Soul {

    /**
     * behaviours Array List to store the behaviours Mimic has
     */
    private ArrayList<Behaviour> behaviours = new ArrayList<>();

    /**
     * followBehavior a follow behavior for Mimic
     */
    private FollowBehaviour followBehaviour;

    /**
     * initLocation
     */
    private Location initLocation;


    /**
     *
     * @param name name for Mimic
     * @param gameMap Game Map
     * @param x x coordinate
     * @param y y coordinate
     */
    public Mimic(String name, GameMap gameMap,int x, int y) {
        super(name, 'M', 100);
        behaviours.add(new WanderBehaviour());
        this.setSoulCount(200);
        this.initLocation = new Location(gameMap,x,y);
    }

    /**
     *
     * @return the Intrinsic Weapon of Mimic
     */
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(55, "kicks");
    }

    /**
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return a list of actions which allow the player to interact with.
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
            if(!otherActor.hasCapability(Status.DISARMED) && otherActor.getWeapon().getClass() != DarkmoonBow.class) {
                actions.add(new AttackAction(this, direction));
            }
        }
        return actions;
    }

    /**
     *
     * @return the initial location of Mimic
     */
    public Location getInitLocation() {
        return initLocation;
    }

    /**
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the action which will be performed by Mimic
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        // loop through all behaviours

        if (!actions.getUnmodifiableActionList().isEmpty()) {
            for (Action action : actions.getUnmodifiableActionList()) {
                if (action.getClass() == AttackAction.class) {
                    display.println("Mimic" + " [" + this.hitPoints + "/" + this.maxHitPoints + "]");
                    return action;
                }
            }
        }
        for (Behaviour Behaviour : behaviours) {
            if (behaviours.contains(followBehaviour)) {
                Action action = followBehaviour.getAction(this, map);
                display.println("Mimic" + " [" + this.hitPoints + "/" + this.maxHitPoints + "]");
                if(action != null){
                    return action;
                }
            }
        }

        return new DoNothingAction();
    }
}
