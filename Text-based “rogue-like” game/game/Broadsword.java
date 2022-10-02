package game;

import edu.monash.fit2099.engine.Actor;

/**
 * A class for Broadsword weapon.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 2
 * @see MeleeWeapon
 */
public class Broadsword extends MeleeWeapon{

    /**
     * The price of Bronzesword weapon.
     */
    private int soulPrice = 500;

    /**
     * Constructor.
     */
    public Broadsword() {
        super("Broadsword", 'B', 30, "hits", 80);
    }

    /**
     * Gets the soulPrice of the Broadsword
     * @return soulprice
     */
    public int getSoulPrice() {
        return soulPrice;
    }
}
