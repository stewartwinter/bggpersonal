package ca.winterfamily.bggpersonaltest;

import ca.winterfamily.bggpersonal.Game;
import junit.framework.TestCase;

public class TestGame extends TestCase {

	public void testGetName() {
		Game game = new Game("abc");
		assertEquals(game.getName(), "abc");
	}

	public void testSetName() {
		Game game = new Game("abc");
		game.setName("def");
		assertEquals(game.getName(), "def");
	}

	public void testToString() {
		Game game = new Game("abc");
		assertEquals(game.toString(), "abc (plays: 0)");
		
		game.mOwned = true;
		assertEquals(game.toString(), "abc (own) (plays: 0)");
	}

	public void testPopulateFromXML() {
//		fail("Not yet implemented");
	}

}
