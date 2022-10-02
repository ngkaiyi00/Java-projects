package game;

import edu.monash.fit2099.engine.*;

import java.util.Random;

public class OpenChestAction extends Action {

    /**
     * Constructor.
     */
    public OpenChestAction() {}

    /**
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return the result of opening the chest (whether meeting Mimic / 1-3 token of soul)
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = "";
        Random rand = new Random();
        int chanceToBeMimic = rand.nextInt(100);
        Location here = map.locationOf(actor);
        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if(destination.getGround().getClass() == Chest.class){
                if(chanceToBeMimic >= 50){
                    map.addActor(new Mimic("Mimic",map, destination.x(), destination.y()),destination);
                    destination.setGround(new Dirt());
                    result += "Chest has turned into a mimic!";
                    return result;
                }
                else{
                    int chanceOfDropping = rand.nextInt(10);
                    if(chanceOfDropping < 3){
                        destination.addItem(new TokenOfSoul("tokenOfSoul",actor,100));
                        destination.setGround(new Dirt());
                        result += "One token of soul is dropped!";
                        return result;
                    }
                    else if(chanceOfDropping > 3 && chanceOfDropping < 7){
                        destination.addItem(new TokenOfSoul("tokenOfSoul",actor,100));
                        destination.addItem(new TokenOfSoul("tokenOfSoul",actor,100));
                        destination.setGround(new Dirt());
                        result += "Two tokens of soul are dropped!";
                        return result;

                    }
                    else{
                        destination.addItem(new TokenOfSoul("tokenOfSoul",actor,100));
                        destination.addItem(new TokenOfSoul("tokenOfSoul",actor,100));
                        destination.addItem(new TokenOfSoul("tokenOfSoul",actor,100));
                        destination.setGround(new Dirt());
                        result += "Three tokens of soul are dropped!";
                        return result;
                    }
                }
            }
        }return result;
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Open the chest";
    }
}
