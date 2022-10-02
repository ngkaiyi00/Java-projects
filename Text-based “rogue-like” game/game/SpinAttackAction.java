package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;

import java.util.Random;

import java.util.ArrayList;

/**
 * A class for spin attack action.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 2
 * @see Action
 */

public class SpinAttackAction extends Action {
    /**
     * GiantAxe's attributes
     */
    protected GiantAxe giantAxe;

    /**
     * The Actor that is to be attacked
     */
    protected Actor target;

    /**
     * Generates random number
     */
    protected Random rand = new Random();

    /**
     * Constructor.
     *
     * @param giantAxe the attributes of GiantAxe.
     */
    public SpinAttackAction(GiantAxe giantAxe) {
        this.giantAxe = giantAxe;
    }

    /**
     * Transfers the soul from a soul object to another
     * @param actor the player
     * @param map the location where the souls is drop.
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
     * This method is used to execute the interaction between the NPC and the player when they are in combat.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return result, a descriptive message.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = actor.getClass().getSimpleName() + " Spin Giant Axe";
        Weapon weapon = actor.getWeapon();
        int damage = weapon.damage() / 2;
        Location here = map.locationOf(actor);
        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if(map.isAnActorAt(destination)) {
                target = destination.getActor();
                if(!target.hasCapability(Status.DEAD)) {
                    target.hurt(damage);
                }
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
                    }
                    else if(target.getClass() == Mimic.class){

                        Random rand = new Random();
                        int chanceOfDropping = rand.nextInt(10);
                        if(chanceOfDropping < 3){
                            map.locationOf(target).addItem(new TokenOfSoul("tokenOfSoul",actor,100));
                            result += System.lineSeparator() + target + " is killed and one token of soul is dropped.";
                        }
                        else if(chanceOfDropping > 3 && chanceOfDropping < 7){
                            map.locationOf(target).addItem(new TokenOfSoul("tokenOfSoul",actor,100));
                            map.locationOf(target).addItem(new TokenOfSoul("tokenOfSoul",actor,100));
                            result += System.lineSeparator() + target + " is killed and two tokens of soul are dropped.";
                        }
                        else{
                            map.locationOf(target).addItem(new TokenOfSoul("tokenOfSoul",actor,100));
                            map.locationOf(target).addItem(new TokenOfSoul("tokenOfSoul",actor,100));
                            map.locationOf(target).addItem(new TokenOfSoul("tokenOfSoul",actor,100));
                            result += System.lineSeparator() + target + " is killed and three tokens of soul are dropped.";
                        }
                        transferSouls(actor,map);

                    }

                    else if(target.getClass() == Player.class){
                        target.addCapability(Status.DEAD);
                        TokenOfSoul tokenOfSoul = new TokenOfSoul("tokenOfSoul", target, 0);
                        target.asSoul().transferSouls(tokenOfSoul.asSoul());
                        map.locationOf(target).addItem(tokenOfSoul);
                        map.moveActor(target, map.at(38,12));
                        if(actor.getClass() == Skeleton.class){
                            Skeleton skeleton = (Skeleton) actor;
                            map.moveActor(skeleton, map.at(skeleton.getInitLocation().x(),skeleton.getInitLocation().y()));
                        }
                        Player player = (Player) target;
                        player.getEstusFlask().setCharge(3);
                        target.heal(1000);
                        actor.heal(1000);
                        result += "\n YOU ARE DEAD AND HAS BEEN SENT BACK TO BONFIRE";
                    }


                }
            }
        }
        return result;
    }

    /**
     * Descriptive message shows the player uses the skill SpinAttack action.
     * @param actor The actor performing the action.
     * @return a string of descriptive message
     */
    @Override
    public String menuDescription(Actor actor) {
        String res = "Unkindled (" + actor.getClass().getSimpleName() + ") Spin Giant Axe";
        return res;
    }


}


