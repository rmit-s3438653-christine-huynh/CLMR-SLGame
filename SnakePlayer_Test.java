package slxGame;

import static org.junit.Assert.*;

import org.junit.*;

/*	assertTrue(test) 
	assertTrue("message", test)

	assertFalse(test) 
	assertFalse("message", test) 

	assertEquals(expectedValue, value) 
	assertEquals("message", expectedValue, value)

	assertNotEquals(value1, value2) 
	assertNotEquals("message", value1, value2) 

	assertNull(value) 
	assertNull("message", value)

	assertNotNull(value) 
	assertNotNull("message", value) 

	assertSame(expectedValue, value) 
	assertSame("message", expectedValue, value) 
	assertNotSame(value1, value2) 
	assertNotSame("message", value1, value2) 

	fail() 
	fail("message")*/

public class SnakePlayer_Test 
{
	SnakePlayer testSnakePlayer;
	Snake mommy;
	Player p = new Player();

	@Before
	public void setup()
	{
		Board testBoard = new Board();
		Dice testDice = new Dice (null);
		int index = 0;
		int pos = 20;
		String name = "Snake-Player1";
		int head = 20;
		int tail = 10;
		mommy = new Snake(44, 33);
		testSnakePlayer = new SnakePlayer(testBoard, testDice, index, pos, name, head, tail, mommy,p);	
	}

	@Test /*Snake-player re-roll dice*/
	public void test_getPos() 
	{
		//return pos, which is head
		int testInt = 0;
		int delta = 0;
		testInt = testSnakePlayer.getPos();
		assertEquals(20, testInt, delta);
	}

	@Test
	public void test_setSPos()
	{
		int setInt = 50;
		int testInt = 0;
		int delta = 0;

		testSnakePlayer.setPos(setInt);
		testInt = testSnakePlayer.getPos();
		assertEquals(setInt, testInt, delta);

		testInt = testSnakePlayer.getHead();
		assertEquals(setInt, testInt, delta);
	}

	@Test/*Get index of snake-player, relates to player index*/
	public void test_GetIndexSP() 
	{
		int delta = 0;
		int testInt = 1;
		testInt = testSnakePlayer.getIndex();
		assertEquals(0, testInt, delta);
	}

	@Test /*Get the snake-player mom reference*/
	public void test_GetSnakeMom() 
	{
		Snake testMom = null;
		testMom = testSnakePlayer.getSnakeMom();
		assertEquals(mommy, testMom);
	}
}
