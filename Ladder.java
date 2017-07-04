package slxGame;
import java.awt.*;

class Ladder extends Entity
{
	private int bottom;
	private int top;
	public Ladder(int b, int t)
	{
		bottom = b;
		top = t;
	}

	public int change()
	{
		return top - bottom;
	}

	public void draw(Graphics g)
	{
		double x1 = getX(bottom);
		double y1 = getY(bottom);
		double x2 = getX(top);
		double y2 = getY(top);


		if ( bottom >= top )
		{
			System.out.println("Ladder bottom must be lower than top. Ladder ignored");
			return;
		}
		int steps = (int) Math.sqrt((y2-y1)*(y2-y1) + (x2-x1)*(x2-x1)) / 25 + 1;

		int xinc = 5;
		if ( x1 > x2)
			xinc = -xinc;
		int yinc = 5;
		if ( y1 > y2)
			yinc = -yinc;

		g.drawLine((int)(x1-xinc),(int)(y1+yinc),(int)(x2-xinc),(int)(y2+yinc)); 
		g.drawLine((int)(x1-xinc)-1,(int)(y1+yinc),(int)(x2-xinc)-1,(int)(y2+yinc)); 
		g.drawLine((int)(x1-xinc),(int)(y1+yinc)-1,(int)(x2-xinc),(int)(y2+yinc)-1); 

		g.drawLine((int)(x1+xinc),(int)(y1-yinc),(int)(x2+xinc),(int)(y2-yinc)); 
		g.drawLine((int)(x1+xinc)-1,(int)(y1-yinc),(int)(x2+xinc)-1,(int)(y2-yinc)); 
		g.drawLine((int)(x1+xinc),(int)(y1-yinc)-1,(int)(x2+xinc),(int)(y2-yinc)-1); 

		double xstep = (x2-x1)/(steps+1);
		double ystep = (y2-y1)/(steps+1);
		for (int i=0; i<steps; i++)		// drawing the steps
		{
			x1 += xstep;  y1 += ystep;
			g.drawLine((int)(x1+xinc),(int)(y1-yinc),(int)(x1-xinc),(int)(y1+yinc)); 
			g.drawLine((int)(x1+xinc)-1,(int)(y1-yinc),(int)(x1-xinc)-1,(int)(y1+yinc)); 
			g.drawLine((int)(x1+xinc),(int)(y1-yinc)-1,(int)(x1-xinc),(int)(y1+yinc)-1); 
		}   
	}
	public int getBottom() 
	{ 
		return bottom; 
	}
	public int getTop() 
	{ 
		return top; 
	} 
}