//CH
package slxGame;


import java.awt.*;



public class SnakePlayer extends Player
{
	private int head = 55;//not used just initializing
	private int tail = 45;//not used just initializing
	boolean rollAgain = false;
	private Player Playerindex;//player who created the snake player
	private Snake snakeMom;//snake who gave rise to the snake player
	private String name;
	public boolean wrongSnake = false;//M

	public int getHead() // Derived from Snake class
	{ 
		return head; //returns position of head
	}

	public int getTail() // Derived from Snake class
	{ 
		return tail; // returns position of tail
	}


	public void setHead(int head) //CH: added 20/04 @ 12:50pm --CHECKED by MALSHA--
	{
		this.head = head;

	}
	public void setTail(int tail) //M: added 13/05 @ 8:40pm
	{
		this.tail = tail;

	}

	public Snake getSnakeMom()//M
	{
		return snakeMom;
	}

	/* Variables extended from Player Class
	 * private String name;
	 * private int pos = 1;		// the current position of the player piece
	 * private int index;		// represents the player index 0, 1, 2 or 3 if 4 players
	 * private Dice dice;
	 * private Board bd;*/

	/* Methods from Player Class
	 * getName()
	 * getPos()
	 * setPos()
	 * computePos()
	 * move()*/


	public SnakePlayer(Board bd, Dice dice, int index, int pos, String name, int head, int tail, Snake mommy,Player Playerindex) 
	{
		super(bd, dice,index,0,0, pos, name,0);
		this.head = head;
		this.tail = tail;
		this.snakeMom = mommy;//M
		this.Playerindex = Playerindex;//M
		this.name = name;
		this.pos = head;//M
	}
	public int getCreatorindex()
	{
		return Playerindex.getIndex();
	}
	public Player getPlayer()
	{
		return Playerindex;
	}
	public String getName()
	{
		return name;
	}
	public SnakePlayer() {
		// allows calls to be made to this class
	}


	// The snakes are drawn by a series of ovals. 
	// The number of ovals is determined by the length of the snake  
	public void draw(Graphics g) //CH ---- Derived from Snake class
	{
		// the x and y coordinates of the snake end-points are based on the board 
		// position of the head and the tail 
		int x1 = getX(head);
		int y1 = getY(head);
		int x2 = getX(tail);
		int y2 = getY(tail);

		if ( head < tail )
		{
			System.out.println("Snake head must be placed higher than tail. Re-Enter"); //CH: print position of tail
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
				if(Playerindex.getIndex() == 0) //CH: player 1
				{
					g.setColor(Color.RED); 
					odd = true;	
				}
				else if(Playerindex.getIndex() == 1) //CH: player 2
				{
					g.setColor(Color.BLUE);
					odd = true;
				}
				else if(Playerindex.getIndex() == 2) //CH: Player 3
				{
					g.setColor(Color.GREEN);
					odd = true;
				}
				else if(Playerindex.getIndex() == 3) //CH: player 4
				{
					g.setColor(Color.YELLOW); 
					odd = true;
				}
				else //CH: random regular snake
				{
					g.setColor(Color.CYAN);
					odd = true;
				}
			}
			if ( x2 > x1)
				g.fillOval((int)(x+inc),(int)(y-inc),20-10*i/steps,20-10*i/steps); 
			else
				g.fillOval((int)(x-inc),(int)(y-inc),20-10*i/steps,20-10*i/steps); 
		}   
	}

	public int move() //MALSHA
	{ 

		String resp;
		int val = 0;

		System.out.println("***** Turn of  " + getName()  +"of player "+ Playerindex.getnFname()+" ******" );   
		do 
		{		
			rollAgain = false; // condition for while
			System.out.print("Press Enter to throw dice ");
			resp = scan.nextLine();
			System.out.println("Rolling the dice .... Please wait");
			val = dice.roll(); //get rollDice somewhere
			System.out.println("You threw a " + val);
			computePos(val);	// computes the new position based on the dice value  

			bd.repaint();		// causes the board and pieces to be redrawn
			System.out.println(getName() + " is now at position " + pos);   

			int a = bd.snakeOnPlayerError(pos);
			head = pos;
			if (head == 100)
				return index;	// returns the index of the player winning the game


			if (val == 6)
			{
				System.out.println("Previously thrown a 6, " + getName() + " awarded another roll.");
				rollAgain = true; // so can enter while
			}
			//checks if a snake head or sanke player head is on the same square ascurrent players head
			else if (bd.overlapErrorSHead(head,this))
			{
				System.out.println("landed on a snake head, " + getName() + " awarded another roll.");
				rollAgain = true; // so can enter while
			}			
			if(a>0)//M -- a indicates number of players on the head
			{
				for(int i=0;i<a;i++)
				{
					//the snake head moves to a position with player(s) on it
					bd.EscapecalcSP(head,tail,Playerindex);//either uses escape points or moves player(s) to tail				
				}
			}


		} while (rollAgain);
		return -1;
	}



	public void computePos(int val)//C
	{
		if ( head + val <= 100)
		{
			setPos(head + val);   

		}
		else
			setPos(head);

	}


	

}
