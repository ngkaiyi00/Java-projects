package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;

import java.util.Random;

/**
 * A class where the ground is burning.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 2
 * @see Ground
 */

public class BurningGround extends Ground {
    /**
     * YhormTheGiant's attributes
     */
    private Actor actor;

    /**
     * The time for the ground to burn.
     */
    private int burningTurn = 0;

    /**
     * Constructor.
     *
     * @param actor actor's attributes.
     */
    public BurningGround(Actor actor){
        super('V');
        this.actor = actor;
    }

    protected Actor target;


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
     * Ground can also experience the joy of time.
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {

        if(burningTurn == 3){
            location.setGround(new Dirt());
            burningTurn = 0;
        }

        String result = "";
        if(location.map().isAnActorAt(location) && location.map().getActorAt(location).getClass() != YhormTheGiant.class){
            location.map().getActorAt(location).hurt(25);
            this.target = location.map().getActorAt(location);
            System.out.println(target.getClass().getSimpleName() + " is burnt for 25 damage.");
            if(!location.map().getActorAt(location).isConscious()){
                if (target.getClass() == Skeleton.class) {
                    Skeleton skeleton = (Skeleton) target;
                    if (skeleton.isSkeletonFirstDeath()) {
                        Random rand = new Random();
                        int randomInt = rand.nextInt(100);
                        if (randomInt >= 50) {
                            skeleton.heal(1000);
                            skeleton.setSkeletonFirstDeath(false);
                            result += System.lineSeparator() + target + " is revived.";
                            System.out.println(result);
                        }else{
                            transferSouls(actor, location.map());
                            result += System.lineSeparator() + target + " is killed by fire";
                            System.out.println(result);
                        }
                    } else { //if the target is not a skeleton then it will just proceed as normal when the target is not conscious
                        transferSouls(actor, location.map());
                        result += System.lineSeparator() + target + " is killed by fire";
                        System.out.println(result);
                    }
                }else if(target.getClass() == YhormTheGiant.class){
                    transferSouls(actor, location.map());
                    result += System.lineSeparator() + "LORD OF CINDER FALLEN";
                    System.out.println(result);
                }else if(target.getClass() == AldrichTheDevourer.class){
                    transferSouls(actor, location.map());
                    result += System.lineSeparator() + "ALDRICH THE DEVOURER FALLEN";
                    System.out.println(result);
                }
                else if(target.getClass() == Undead.class){
                    transferSouls(actor, location.map());
                    result += System.lineSeparator() + target + " is killed by fire";
                    System.out.println(result);
                }
                else if(target.getClass() == Mimic.class){

                    Random rand = new Random();
                    int chanceOfDropping = rand.nextInt(10);
                    if(chanceOfDropping < 3){
                        location.map().locationOf(target).addItem(new TokenOfSoul("tokenOfSoul",actor,100));
                        result += System.lineSeparator() + target + " is killed and one token of soul is dropped.";
                        System.out.println(result);
                    }
                    else if(chanceOfDropping > 3 && chanceOfDropping < 7){
                        location.map().locationOf(target).addItem(new TokenOfSoul("tokenOfSoul",actor,100));
                        location.map().locationOf(target).addItem(new TokenOfSoul("tokenOfSoul",actor,100));
                        result += System.lineSeparator() + target + " is killed and two tokens of soul are dropped.";
                        System.out.println(result);
                    }
                    else{
                        location.map().locationOf(target).addItem(new TokenOfSoul("tokenOfSoul",actor,100));
                        location.map().locationOf(target).addItem(new TokenOfSoul("tokenOfSoul",actor,100));
                        location.map().locationOf(target).addItem(new TokenOfSoul("tokenOfSoul",actor,100));
                        result += System.lineSeparator() + target + " is killed and three tokens of soul are dropped.";
                        System.out.println(result);
                    }
                    transferSouls(actor,location.map());

                }
                else{
                    target.addCapability(Status.DEAD);
                    TokenOfSoul tokenOfSoul = new TokenOfSoul("tokenOfSoul", target, 0);
                    target.asSoul().transferSouls(tokenOfSoul.asSoul());
                    location.map().locationOf(target).addItem(tokenOfSoul);
                    location.map().moveActor(target, location.map().at(38,12));
                    this.actor.removeCapability(Status.ENRAGED);
                    location.map().moveActor(actor, location.map().at(6,25));
                    location.setGround(new Dirt());
                    Player player = (Player) target;
                    player.getEstusFlask().setCharge(3);
                    target.heal(1000);
                    actor.heal(1000);
                    System.out.println("YOU ARE BURNT TO DEATH");
                    System.out.println("YOU WILL BE SENT BACK TO BONFIRE");

                }

            }
        }
        burningTurn += 1;
    }

    /**
     * Player can enter the burning ground.
     * @param actor The actor performing the action.
     * @return true
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return true;
    }

}
