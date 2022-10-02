package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.enums.Abilities;

/**
 * The gorge or endless gap that is dangerous for the Player.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 1
 * @see Ground
 */

public class Valley extends Ground {

	/**
	 * Constructor.
	 *
	 */
	public Valley() {
		super('+');
	}

	/**
	 * FIXME: At the moment, the Player cannot enter it. It is boring.
	 *
	 * @param actor the Actor to check
	 * @return false or actor cannot enter.
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		return actor.hasCapability(Abilities.TOENTERVALLEY);
	}
}
