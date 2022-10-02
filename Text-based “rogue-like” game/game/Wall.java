package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;

/**
 * The gorge or endless gap that is dangerous for the Player.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 1
 * @see Ground
 */

public class Wall extends Ground {

	/**
	 * Constructor.
	 *
	 */
	public Wall() {
		super('#');
	}

	/**
	 * @param actor the Actor to check
	 * @return false or actor cannot enter.
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		return false;
	}

	// dont delete future maybe applicable.
	@Override
	public boolean blocksThrownObjects() { return true; }

}
