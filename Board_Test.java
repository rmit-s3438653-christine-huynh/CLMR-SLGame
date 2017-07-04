package slxGame;

import static org.junit.Assert.*;
import org.junit.*;

public class Board_Test 
{
	Board testBoard;
		
	Player p[] = new Player[10];
	Snake s[] = new Snake[2];
	
	@Before
	public void setup()
	{
		testBoard = new Board(); 
		Dice testDice = new Dice(null);
		testBoard.add(new Ladder(32, 45));
		s[0] = new Snake(70, 60);
		s[1] = new Snake(50, 40);
		testBoard.add(s[0]);
		testBoard.add(s[1]);
		testBoard.setnumSnakes(2);
	
		String name1 = "Playerl";
		String name2 = "Player2";
		p[0] = new Player(testBoard, testDice, 0, 0, 1, 5, name1, 0);
		p[1] = new Player(testBoard, testDice, 1, 1, 1, 6, name2, 0);
		testBoard.add(p, 2);
		testBoard.setPCount(2);
		
		testBoard.createSnakeP(p[0],0);
		testBoard.createSnakeP(p[1],1);

	}

	/*Player 1 gains snake-player
	 * snake-player count + 1*/
	@Test
	public void test_AddSnakePlayer()  //not sure
	{
		int testInt = 0;
		testInt = testBoard.getSPCount(); 
		assertEquals(2, testInt); //Test returns 0 as true
	}

	@Test
	/*Snake-player(s) will be removed
	 * Snake-mom will also be removed*/
	public void test_RemoveSnake() 
	{
		int testInt = 0;
		testInt = testBoard.getSPCount();
		assertEquals(testInt,2);//checks if the snake player is deleted
		assertEquals((testBoard.getsnakesCount()),2);//initial number of snakes
		testBoard.remove(s[1]);
		assertEquals((testBoard.getsnakesCount()),1);//tests to see if one snake is removed
		testInt = testBoard.getSPCount();
		assertEquals(testInt,2);//checks if the snake player is deleted

		
	}
	public void test_RemoveSnakePlayer() 
	{
		int testInt = 0;
		testInt = testBoard.getSPCount();
		assertEquals(testInt,2);//checks if the snake player is deleted
		testBoard.remove(p[1]);
		testInt = testBoard.getSPCount();
		assertEquals(testInt,1);//checks if the snake player is deleted

		
	}
}
