package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

import java.util.Random;

/**
 * Class representing the cemetery.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 2
 * @see Ground
 */

public class Cemetery extends Ground {
    private Undead undead;
    Random rand = new Random();

    /**
     * Constructor.
     *
     */
    public Cemetery() {
        super('C');
    }


    @Override
    public void tick(Location location) {
        boolean add = false;
        int x = location.x();
        int y = location.y();
        super.tick(location);
        int chance = rand.nextInt(100);
        if(chance >= 75){
            for(Exit exit : location.getExits()){
                Location destination = exit.getDestination();
                if(!location.map().isAnActorAt(destination) && !add){
                    undead = new Undead("Undead", location.map(), destination.x(), destination.y());
                    location.map().addActor(undead,destination);
                    add = true;
                }
            }

        }
    }

    /**
     *?????????????????????????????????????????????????
     * @return false
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }
}
