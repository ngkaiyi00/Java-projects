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

public class FireLinkShrineBonFire extends Bonfire{

    /**
     * Constructor.
     *
     * @param position The bonfire's position.
     */
    public FireLinkShrineBonFire(Location position){
        super("Fire Link Shrine", LastBonFire.FIRELINK_SHRINE, position);
        this.addCapability(Status.CANT_TELEPORT);
    }
}

