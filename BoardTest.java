package slxGame;
import static org.junit.Assert.*;
import org.junit.Test;

public class BoardTest {
Board b = new Board();
int bottom,top;
int head, tail;
boolean val;

	@Test
	public void testOutOfBounds() 
	{
		 bottom = 0;
		 top = 102;
		 val = b.outOfBounds(bottom,top);
	//	 test = 1;
		 assertTrue(val);
	}
	
	@Test
	public void testOutOfBoundsL() 
	{
		 bottom = 5;
		 top = 98;
		 val = b.outOfBounds(bottom,top);
	//	 test = 1;
		 assertFalse(val);
	}

// ---------------------------------------------------------------------------
	@Test
	public void testPlaceObjectErrorTS() {
		tail = 22;
		head = 29;
		val = b.placeObjectError(tail,head);
		
			 assertTrue(val);
	}
	
	@Test
	public void testPlaceObjectErrorFS() {
		tail = 5;
		head = 25;
		val = b.placeObjectError(tail,head);
		//	 test = 1;
			 assertFalse(val);
	}
// --------------------------------------------------------------------------------	
	@Test//Lovepreet
	public void testOverlapError1() {
		bottom = 57;
		head = 57;
		val = b.overlapErrorS(bottom,head);
			 assertTrue(val);
	}
	
	@Test//M
	public void testOverlapError2() {
		// no overlap
		b.add(new Snake(30,19));
		bottom = 10;
		head = 20;
		val = b.overlapErrorL(head,bottom);
		//	 test = 1;
			 assertFalse(val);
	}
	
	@Test//M
	public void testOverlapError3() {
		// overlapping snake and ladder
		b.add(new Snake(30,19));
		bottom = 30;
		head = 20;
		val = b.overlapErrorL(head,bottom);
		//	 test = 1;
			 assertTrue(val);
	}
	
	@Test//M
	public void testOverlapError4() {
		// overlapping snakes
		b.add(new Snake(30,19));
		bottom = 10;
		head = 30;
		val = b.overlapErrorS(head,bottom);
		//	 test = 1;
			 assertTrue(val);
	}

}