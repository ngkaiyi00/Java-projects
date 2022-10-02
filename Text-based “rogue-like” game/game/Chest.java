package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;

public class Chest extends Ground {
    /**
     * Constructor which set the display character as '?'.
     */

    public Chest() {super('?');}


    /**
     *
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return a list of actions which has openChestAction in it.
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        Actions actions = new Actions();
        if(actor.hasCapability(Abilities.OPENCHEST)){
            actions.add(new OpenChestAction());
        }
        return actions;
    }

    /**
     *
     * @param actor the Actor to check
     * @return false as actor cannot enter
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }
}
