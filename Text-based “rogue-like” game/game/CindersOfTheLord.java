package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.DropItemAction;
import edu.monash.fit2099.engine.PickUpItemAction;

public class CindersOfTheLord extends PortableItem {

    /**
     * lordOfCinder which shows the origin of this cinders of the lord
     */
    protected LordOfCinder lordOfCinder;

    /**
     * Constructor
     * @param lordOfCinder which stores the origin of this cinders of the lord
     */
    public CindersOfTheLord(LordOfCinder lordOfCinder) {
        super("Cinders Of The Lord",'L');
        this.lordOfCinder = lordOfCinder;
    }

    /**
     *
     * @param actor an actor that will interact with this item
     * @return true if this item is portable
     */
    @Override
    public PickUpItemAction getPickUpAction(Actor actor) {
        return super.getPickUpAction(actor);
    }

    /**
     *
     * @param actor an actor that will interact with this item
     * @return true if this item is portable
     */
    @Override
    public DropItemAction getDropAction(Actor actor) {
        return super.getDropAction(actor);
    }
}
