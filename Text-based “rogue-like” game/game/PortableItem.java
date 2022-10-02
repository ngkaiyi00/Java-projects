package game;

import edu.monash.fit2099.engine.Item;

/**
 * A class for portable item.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 2
 * @see
 */

public class PortableItem extends Item {

	/**
	 * Constructor.
	 *
	 * @param name the player
	 * @param displayChar the character of the item displayed in the map
	 */
	public PortableItem(String name, char displayChar) {
		super(name, displayChar, true);
	}
}
