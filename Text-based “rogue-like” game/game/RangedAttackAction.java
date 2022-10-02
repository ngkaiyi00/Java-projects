package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;
import game.interfaces.Behaviour;

import java.util.Random;

/**
 * Class representing the RangedAttackAction.
 *
 * @author Jerrold Wong Youn Zhuet
 * @version 1
 * @see Actor, AttackAction
 */
public class RangedAttackAction extends Action implements Behaviour {
    /**
     * The Actor that is to be attacked
     */
    protected Actor target;

    /**
     * Random number generator
     */
    protected Random rand = new Random();

    /**
     * Constructor.
     *
     * @param target the Actor to attack
     */
    public RangedAttackAction(Actor target) {
        this.target = target;
    }

    /**
     * Instance variable to check if there is a wall in path of attack
     */
    private boolean wall;

    /**
     * A method used in execute created to reduce repetition of code due to it being recurring
     * @param actor the non player character
     * @param map in case where actor drops an item
     */
    public void transferSouls(Actor actor, GameMap map){
        target.asSoul().transferSouls(actor.asSoul());
        Actions dropActions = new Actions();
        // drop all item
        for (Item item : target.getInventory())
            dropActions.add(item.getDropAction(actor));
        for (Action drop : dropActions)
            drop.execute(target, map);
        // remove actor
        map.removeActor(target);

    }

    /**
     * This method is used to execute the attack instruction when there is an actor within the adjacent spaces of other
     * actors.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return result, a descriptive message of the damage done by the actor to the target
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        int damage;
        Weapon weapon = actor.getWeapon();
        if(weapon.getClass() == DarkmoonBow.class){
			int dealDoubleDmg = rand.nextInt((100));
			if(dealDoubleDmg >= 85){
				damage = weapon.damage() * 2;
			}else{
				damage = weapon.damage();
			}
		}
        else{
            damage = weapon.damage();
        }

        if (!(rand.nextInt(100) <= weapon.chanceToHit())) {
            return actor + " misses " + target + ".";
        }
        if(wall){
            return actor + "'s attack hit a wall";
        }
        String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
        target.hurt(damage);
        if (!target.isConscious()) {
            //special condition check for skeleton to give him a 50% chance to revive
            if (target.getClass() == Skeleton.class) {
                Skeleton skeleton = (Skeleton) target;
                if (skeleton.isSkeletonFirstDeath()) {
                    int randomInt = rand.nextInt(100);
                    if (randomInt >= 50) {
                        skeleton.heal(1000);
                        skeleton.setSkeletonFirstDeath(false);
                        result += System.lineSeparator() + target + " is revived.";
                    }else{
                        transferSouls(actor,map);
                        result += System.lineSeparator() + target + " is killed.";
                    }
                } else { //if the target is not a skeleton then it will just proceed as normal when the target is not conscious
                    transferSouls(actor,map);
                    result += System.lineSeparator() + target + " is killed.";
                }
            }else if(target.getClass() == YhormTheGiant.class){
                transferSouls(actor,map);
                result += System.lineSeparator() + "LORD OF CINDER FALLEN";
            }else if(target.getClass() == Undead.class){
                transferSouls(actor,map);
                result += System.lineSeparator() + target + " is killed.";
            }else if(target.getClass() == AldrichTheDevourer.class){
                transferSouls(actor,map);
                result += System.lineSeparator() + "LORD OF CINDER FALLEN";
            }else if(target.getClass() == Player.class){
                target.addCapability(Status.DEAD);
                TokenOfSoul tokenOfSoul = new TokenOfSoul("tokenOfSoul", target, 0);
                target.asSoul().transferSouls(tokenOfSoul.asSoul());
                map.locationOf(target).addItem(tokenOfSoul);
                map.moveActor(target, map.at(38,12));
                if(actor.getClass() == Skeleton.class){
                    Skeleton skeleton = (Skeleton) actor;
                    map.moveActor(skeleton, map.at(skeleton.getInitLocation().x(),skeleton.getInitLocation().y()));
                }
                else if(actor.getClass() == Undead.class){
                    Undead undead = (Undead) actor;
                    map.moveActor(undead, map.at(undead.getInitLocation().x(),undead.getInitLocation().y()));
                }else if(actor.getClass() == YhormTheGiant.class){
                    YhormTheGiant yhormTheGiant = (YhormTheGiant) actor;
                    map.moveActor(yhormTheGiant, map.at(yhormTheGiant.getInitLocation().x(),yhormTheGiant.getInitLocation().y()));
                    yhormTheGiant.removeCapability(Status.ENRAGED);
                }
                target.heal(1000);
                Player player = (Player) target;
                player.getEstusFlask().setCharge(3);
                actor.heal(1000);
                result += "\n YOU ARE DEAD AND HAS BEEN SENT BACK TO BONFIRE";
            }


        }
        return result;
    }

    /**
     * Aldrich will call this method to return a ranged attack action if player is within 3 squares (7x7)
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return this RangedAttackAction
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        this.wall = false;
        Location here = map.locationOf(actor);
        Location there = map.locationOf(target);

        NumberRange xs, ys;
        if (there.x() >= here.x()-3 && there.x() <= here.x()+3){
            if (there.y() >= here.y()-3 && there.y() <= here.y()+3){
                xs = new NumberRange(Math.min(here.x(), there.x()), Math.abs(here.x() - there.x()) + 1);
                ys = new NumberRange(Math.min(here.y(), there.y()), Math.abs(here.y() - there.y()) + 1);

                for (int x : xs) {
                    for (int y : ys) {
                        if (map.at(x, y).getGround().blocksThrownObjects()) {
                            this.wall = true;
                            return this;
                        }
                    }
                }
                return this;
            }
        }
        return null;
    }

    /**
     * Descriptive message to describe the actor hitting the target in which direction
     * @param actor The actor performing the action.
     * @return a string of descriptive message
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " shoots " + target;
    }
}
