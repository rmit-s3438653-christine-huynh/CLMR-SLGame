package slxGame;

import static org.junit.Assert.*;
import org.junit.Before;


import org.junit.Test;

public class EscapePointTest {
	 EscapePoint ep = new EscapePoint();
	@Before
	public void setUp()
	{
		int escapePt = 0;
	}

	@Test
	public final void testSnakeFirst () {
	ep.loseEscapePoint();
	assertEquals(0,ep.getEscapePoint());
		
	}
	@Test
	public final void testSnakeLadderSnake() {
		ep.loseEscapePoint();
		ep.gainEscapePoint();
		ep.loseEscapePoint();
		assertEquals(0,ep.getEscapePoint());
	}

	@Test
	public final void testTwoLaddersThreeSnakes() {
		ep.gainEscapePoint();
		ep.loseEscapePoint();
		ep.gainEscapePoint();
		ep.loseEscapePoint();
		ep.loseEscapePoint();
		assertEquals(0,ep.getEscapePoint());
	}

	@Test
	public final void test4Ladders() {
		ep.gainEscapePoint();
		ep.gainEscapePoint();
		ep.gainEscapePoint();
		ep.gainEscapePoint();
		assertEquals(3,ep.getEscapePoint());
	}
}
