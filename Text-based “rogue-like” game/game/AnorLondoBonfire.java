package game;
import edu.monash.fit2099.engine.*;
import game.enums.LastBonFire;
import game.enums.Status;

/**
 * The Fire Link Shrine Bonfire
 *
 * @author Ng Kai Yi
 * @author Edmund Tai Weng Chen
 * @version 2
 * @see Bonfire
 */

public class AnorLondoBonfire extends Bonfire{

    /**
     * Constructor.
     *
     * @param position The bonfire's position.
     */
    public AnorLondoBonfire(Location position){
        super("Anor Londo", LastBonFire.ANOR_LONDO, position);
        this.addCapability(Status.NOT_ACTIVATED);
    }

}
