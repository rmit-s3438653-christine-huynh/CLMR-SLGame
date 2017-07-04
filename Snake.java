package slxGame;
import java.awt.*;

class Snake extends Entity
{
	private int head;
	private int tail;
	public Snake(int h, int t)
	{
		head = h;
		tail = t;
	}

	public int change()
	{
		return tail - head;
	}

	// The snakes are drawn by a series of ovals. 
	// The number of ovals is determined by the length of the snake  
	public void draw(Graphics g)
	{
		// the x and y coordinates of the snake end-points are based on the board 
		// position of the head and the tail 
		int x1 = getX(head);
		int y1 = getY(head);
		int x2 = getX(tail);
		int y2 = getY(tail);

		if ( head < tail )
		{
			System.out.println("Snake head must be placed higher than tail. Re-Enter"); //CH:  modified text
			return;
		}

		// The number of steps is based on the length of the snake
		int steps = (int) Math.sqrt((y2-y1)*(y2-y1) 
				+ (x2-x1)*(x2-x1)) / 150 * 18 + 24;

		double xstep = (double)(x2-x1)/(steps+1);
		double ystep = (double)(y2-y1)/(steps+1);

		double inc;
		double x = x1,y = y1;

		boolean odd = true;
		for (int i=0; i<steps+1; i++)
		{
			if ( (i%12) % 12 == 0)
				inc = 0;
			else if ( (i%12)% 11 == 0)
				inc = 10 * factor;
			else if ( (i%12)% 10 == 0)
				inc = 13 * factor;
			else if ( (i%12)% 9 == 0)
				inc = 15 * factor;
			else if ( (i%12)% 8 == 0)
				inc = 13 * factor;
			else if ( (i%12)% 7 == 0)
				inc = 10 * factor;
			else if ( (i%12)% 6 == 0)
				inc = 0 * factor;
			else if ( (i%12)% 5 == 0)
				inc = -10 * factor;
			else if ( (i%12)% 4 == 0)
				inc = -13 * factor;
			else if ( (i%12)% 3 == 0)
				inc = -15 * factor;
			else if ( (i%12)% 2 == 0)
				inc = -13 * factor;
			else 
				inc = -10 * factor;
			x += xstep;  y += ystep;
			if (odd) 
			{
				g.setColor(Color.BLACK);
				odd = false;
			}
			else 
			{
				g.setColor(Color.CYAN);
				odd = true;
			}
			if ( x2 > x1)
				g.fillOval((int)(x+inc),(int)(y-inc),20-10*i/steps,20-10*i/steps); 
			else
				g.fillOval((int)(x-inc),(int)(y-inc),20-10*i/steps,20-10*i/steps); 
		}   
	}

	public int getHead() 
	{ 
		return head; 
	}
	public int getTail() 
	{ 
		return tail; 
	} 
}
