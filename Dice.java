package slxGame;

import java.awt.*;
import java.util.*;



class Dice
{
	private int n = -1;
	private Board sl;
	private Graphics g;

	public Dice(Graphics g)
	{
		this.g = g;
	}

	// Draw the dice value using a series of dots using fillOval
	public void draw()
	{
		g.setColor(Color.BLACK);
		g.fill3DRect(500,200,45,45,false);
		g.setColor(Color.WHITE);
		if ( n == 1)
		{
			g.fillOval(520,220,5,5);
		}          
		else if ( n == 2 )
		{
			g.fillOval(510,220,5,5);
			g.fillOval(530,220,5,5);
		}          
		else if ( n == 3 )
		{
			g.fillOval(520,210,5,5);
			g.fillOval(510,230,5,5);
			g.fillOval(530,230,5,5);
		}          
		else if ( n == 4 )
		{
			g.fillOval(510,210,5,5);
			g.fillOval(530,210,5,5);
			g.fillOval(510,230,5,5);
			g.fillOval(530,230,5,5);
		}          
		else if ( n == 5 )
		{
			g.fillOval(510,210,5,5);
			g.fillOval(530,210,5,5);
			g.fillOval(520,220,5,5);
			g.fillOval(510,230,5,5);
			g.fillOval(530,230,5,5);
		}          
		else if ( n == 6 )
		{
			g.fillOval(510,210,5,5);
			g.fillOval(530,210,5,5);
			g.fillOval(510,220,5,5);
			g.fillOval(530,220,5,5);
			g.fillOval(510,230,5,5);
			g.fillOval(530,230,5,5);
		}          
	}

	// simulate the drawing of the dice 
	public int roll()
	{
		int n = 0;
		for (int i=1; i<=200; i++) 
		{
			n = (int) (Math.random()*6) + 1;
			set(n,5);
		}
		/**-----------------------------------------------------------------**/
		Scanner scan = new Scanner(System.in);
		char snew;
		do{
			String s;
			s = "9";
			snew = s.charAt(0);


			System.out.println("enter dice value:");
			s= scan.nextLine();
			if(s.trim().isEmpty())//prevents error on enter key
				s ="9" ;
			snew = s.charAt(0);

		}while(snew!='1'&&snew!='2'&&snew!='3'&&snew!='4'&&snew!='5'&&snew!='6');
		n = Character.getNumericValue(snew);
		/**-----------------------------------------------------------------**///M
		return n;
	}

	// sets the dice value and draws it
	public void set(int val, int time)
	{
		if ( val >= 1 && val <= 6)
		{
			n = val;  

			draw();
			try {
				Thread.sleep(time);
			}
			catch(Exception e) {}
		}
	}

	//static int getThrow() { return (int) (Math.random()*6) + 1;    }   //change now     
	// static int getThrow() { 


}
