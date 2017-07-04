package slxGame;
import java.awt.*;

import javax.swing.*;

import java.util.*;

class Board extends JPanel implements Runnable
{
	static Scanner scan = new Scanner(System.in);
	private static JFrame frame = new JFrame("CLMR: Snakes & Ladders Extended"); //CH: modified name
	private int XMARGIN = 20;
	private int YMARGIN = 20;
	private Player players[];
	SnakePlayer sp = new SnakePlayer();//M --  for use in creating snake player
	private int pCount;


	private Snake[] ss = new Snake[10];
	private Ladder[] ls = new Ladder[10];
	//set to this to prevent too many snake players as there is no specified max
	private int maxnumP = 10;	
	private int snakesCount = 0;
	private int laddersCount = 0;
	private int maxSnakesCount = 10;  //LOVEPREET
	private int numSnakes = 0;
	private int numLadders = 0;
	private int maxLaddersCount = 10;
	private int spCount = 0; //CH: track amount of snake players

	public int getpCount()
	{
		return pCount;
	} 
	public int getsnakesCount()
	{
		return snakesCount;
	} 
	public int getnumSnakes()
	{
		return numSnakes;
	}
	public int getnumLadders()
	{
		return numLadders;
	}
	public int getmaxnumP()
	{
		return maxnumP;
	}
	public void setnumSnakes(int numSnakes) {
		this.numSnakes = numSnakes;
		this.snakesCount = numSnakes;
	}

	public void setnumLadders(int numLadders) {
		this.numLadders = numLadders;
		this.laddersCount = numLadders;
	}
	public void setnumPlayers(int maxnumP) {
		this.maxnumP = maxnumP;
	}

	// Default board setup with 3x snakes and ladders.


	public void setup()
	{

		add(new Ladder(12,49)); //bottom, top
		add(new Ladder(34,51));
		add(new Ladder(53,79));
		add(new Ladder(20,74));

		add(new Snake(75,42)); //head, tail
		add(new Snake(39,8));
		add(new Snake(80,60));
		add(new Snake(18,3));
		add(new Snake(16,5));
		add(new Snake(11,10));
		add(new Snake(73,47));

	}

	public void totalEscape(int index, Player newepPlayer)//MALSHA
	{

		for(int i=0; i<pCount; i++)
		{
			// for the logged in player's piece which aren't the one which is currently having a turn
			if(players[i].getIndex()== index && players[i]!=newepPlayer)
				players[i].setEscapePoint( newepPlayer.getEscapePoint());//sets other pieces of that players to the same escape points
		}
	}

	public void numSandL()//MALSHA
	{
		do {
			boolean wrong = false;

			do 
			{
				System.out.print("Enter number of snakes : 1..10 : ");
				try
				{
					wrong = false;
					numSnakes = scan.nextInt(); 

				}
				catch(InputMismatchException ime)//M
				{
					wrong = true;
					scan.nextLine();
					System.out.println("please enter a number");
				}

			} while(wrong);

			if ((numSnakes >= maxSnakesCount)  &&  numSnakes < 3) //LOVEPREET
			{
				System.out.print("The Maximum number of Snakes allowed are 10! Re-Enter:\n"); //LOVEPREET
				maxLaddersCount = numSnakes - 2 ; //M
				numLadders = 0;//M evaluates to false
			}
			else 
			{
				maxLaddersCount = numSnakes - 2 ; //M

				do
				{
					System.out.print("Enter number of ladders : 1..10 : ");
					wrong = false;
					try
					{
						numLadders = scan.nextInt();
					}
					catch(InputMismatchException ime)//M
					{
						wrong = true;
						scan.nextLine();
						System.out.println("please enter a number");

					}
				} while(wrong);
				if (numLadders > maxLaddersCount )
				{
					System.out.print("Ladders should be 2 or more less than the number of snakes! Re-enter:\n"); //LOVEPREET

				}
			}
		} while ( numSnakes < 3 || numSnakes > maxSnakesCount ||  numLadders > maxLaddersCount|| numLadders < 1);//M
	}

	public void numP()//MALSHA
	{
		do {
			boolean wrong = false;

			do 
			{
				System.out.print("Enter maximum number of players per person : 2 or 3 : ");
				try//M
				{
					wrong = false;
					maxnumP = scan.nextInt(); 

				}
				catch(InputMismatchException ime)
				{
					wrong = true;
					scan.nextLine();
					System.out.println("please enter a number");
				}

			} while(wrong);

			if ((maxnumP !=2) && maxnumP != 3) 
			{
				System.out.print("The number of Snake players allowed are 2 and 3! Re-Enter:\n"); //M

			}

		} while ( (maxnumP !=3) && maxnumP != 2);
	}


	// Menu option 2: customize board, set snakes, ladders 
	public void customize() //LOVEPREET
	{
		snakesCount = 0;
		laddersCount = 0;
		repaint();
		int head = 0,tail = 0;
		int bottom = 0, top = 0;
		boolean wrong = false;
		boolean wrongSnake = false;//M
		boolean wrongLadder = false;//M

		for (int i=0; i<numSnakes; i++)
		{

			do {
				wrongSnake = false;//M

				do 
				{

					try//M
					{
						wrong = false;
						System.out.print("Enter Head pos of snake " + (i+1) + " : ");
						head = scan.nextInt();

					}
					catch(InputMismatchException ime)//M
					{
						wrong = true;
						scan.nextLine();
						System.out.println("please enter a number");

					}

				} while(wrong);

				if(head <= 10)//M
					wrongSnake = true;
				else 
				{
					try//M
					{
						wrong = false;
						System.out.print("Enter Tail pos of snake " + (i+1) + " : ");
						tail = scan.nextInt();

					}
					catch(InputMismatchException ime)//M
					{
						wrong = true;
						scan.nextLine();
						System.out.println("please enter a number");

					}

					if(!wrong)
					{
						if (outOfBounds(head, tail))
						{
							System.out.print("The number is out of bounds. Re-enter:\n");
							wrongSnake = true;

						} 
						else if ( tail >= head)
						{
							System.out.println("Head must be higher than the tail. ReEnter\n");
							wrongSnake = true;
						}
						else if (placeObjectError(tail, head))  
						{
							System.out.print("Snake must not be horizontal. Re-enter:\n");  //LOVEPREET
							wrongSnake = true;
						} 
						else if (overlapErrorS(head, tail))
						{
							System.out.print("Snakes cannot overlap. Re-enter:\n");  //LOVEPREET
							wrongSnake = true;
						}

						if(!wrongSnake)//M
						{
							add(new Snake(head,tail));

						}
					}
				}
			}     while ( wrongSnake||wrong);

		}
		for (int i=0; i<numLadders; i++)
		{
			do 
			{
				wrongLadder = false;//M
				do 
				{

					try//M
					{
						wrong = false;
						System.out.print("Enter Bottom pos of ladder " + (i+1) + " : ");
						bottom = scan.nextInt();

					}
					catch(InputMismatchException ime)//M
					{
						wrong = true;
						scan.nextLine();
						System.out.println("please enter a number");

					}

				} while(wrong);


				if(bottom >= 91)//M
				{
					wrongLadder = true;
				}
				else
				{

					do 
					{

						try//M
						{
							wrong = false;
							System.out.print("Enter Top pos of ladder " + (i+1) + " : ");
							top = scan.nextInt();

						}
						catch(InputMismatchException ime)//M
						{
							wrong = true;
							scan.nextLine();
							System.out.println("please enter a number");

						}

					} while(wrong);
					if(outOfBounds(bottom, top))
					{
						System.out.print("The number is out of bounds. Re-enter:\n");
						wrongLadder = true;//M
					}
					else if ( bottom >= top)
					{
						System.out.println("Top must be higher than the Bottom. ReEnter\n");
						wrongLadder = true;//M
					}
					else if (placeObjectError(bottom,top))  //LOVEPREET & M
					{
						System.out.print("Ladder must not be horizontal. Re-enter:\n");  //LOVEPREET
						wrongLadder  = true; //M
					}
					else if (overlapErrorL(top,bottom))
					{
						System.out.print("Ladders cannot overlap. Re-enter:\n");  //LOVEPREET
						wrongLadder = true;//M
					}

					if(!wrongLadder)//M
					{
						add(new Ladder(bottom,top));

					}
				}

			}while(wrongLadder);
		}
	}

	public boolean outOfBounds(int bottom, int top)//LOVEPREET: Ladder out of the board 
	{
		if (top > 100 || top <= 0 ||bottom >= 100 || bottom <= 0)
			return true;
		else	   
			return false;
	}

	public boolean placeObjectError(int bottom, int top)  //LOVEPREET: horizontal ladder
	{
		for(int i = 1;i<=9;i=i+2)
		{
			if ((top<=10*i && bottom>=1+(10*(i-1))))
				return true;
		}
		for(int i = 2;i<=10;i=i+2)
		{
			if ((bottom>=1+(10*(i-1)) && top<=10*i))
				return true;
		}
		return false;
	}

	public void SnakeTail(int pos)//MALSHA
	{  
		//for landing on snakeplayer tail
		for(int i=0; i<pCount; i++)
		{
			if(players[i] instanceof SnakePlayer)//checks if the player is a snake player
			{
				if(pos ==  ((SnakePlayer) players[i]).getTail())//landed on snake tail
				{
					//removes the snake player whose tail has been landed on
					remove(players[i]);
				}
			}
		}

		//landing on a snake tail
		for(int i=0; i<snakesCount; i++)
		{
			if(pos ==  ss[i].getTail())//landed on snake tail
			{

				for(int j=0; j<pCount; j++)
				{
					if(players[j] instanceof SnakePlayer)//checks this player is a snake player
					{
						if(ss[i] ==  ((SnakePlayer) players[j]).getSnakeMom())//landed on snake tail
						{
							//removes the child snakes
							remove(( players[j]));
						}
					}
				}
				//removes the snake mother
				remove(ss[i]);
			}
		}

	}


	public boolean overlapErrorS(int head, int bottom)
	{  //ladder base overlap with snake head

		if (head == bottom) //LOVEPREET
			return true;
		for(int i=0; i<snakesCount; i++)//M
		{
			if(head ==  ss[i].getHead())
				return true;
			else if(head ==  ss[i].getTail())
				return true;
			else if(bottom ==  ss[i].getHead())				
				return true;
			else if(bottom ==  ss[i].getTail())
				return true;
		}

		return false;
	}
	public boolean overlapErrorSL(int head, int bottom)
	{  //ladder base overlap with snake head

		if (head == bottom) //LOVEPREET
			return true;
		for(int i=0; i<snakesCount; i++)//M
		{
			if(head ==  ss[i].getHead())
				return true;
			else if(head ==  ss[i].getTail())
				return true;
			else if(bottom ==  ss[i].getHead())				
				return true;
			else if(bottom ==  ss[i].getTail())
				return true;
		}
		for(int i=0; i<laddersCount; i++)//M
		{
			if(bottom ==  ls[i].getBottom())
				return true;
			if(head ==  ls[i].getBottom())
				return true;
		}


		return false;
	}
	public void headAndTail() //MALSHA
	{
		int head;
		int tail;

		do {

			head = (int) ((Math.random()*88) + 11);//avoids first row
			tail = (int) ((Math.random()*89) + 1);
			sp.setHead(head);
			sp.setTail(tail);


		} while(tail>=head||snakeOnPlayer(head,tail)>0||overlapErrorSL(head, tail)||placeObjectError(tail, head));


	}

	public boolean overlapErrorSHead(int head,Player currentplayer)//MALSHA
	{  
		//the head is on a snake head
		for(int i=0; i<snakesCount; i++)//M
		{
			if(head ==  ss[i].getHead())
			{

				return true;
			}
		}
		//head is on a snake player head
		for ( int i=0; i <pCount; i++)
		{
			if(players[i] instanceof SnakePlayer)// checks the player is a snake player
			{
				if( head == players[i].getHead()&& players[i]!=currentplayer)
				{
					return true;
				}

			}
		}
		return false;
	}

	public boolean overlapErrorL(int head, int bottom) 
	{  //ladder base overlap with snake or ladder

		if (head == bottom)//LOVEPREET
			return true;
		for(int i=0; i<laddersCount; i++)//M
		{
			if(bottom ==  ls[i].getBottom())
				return true;
			if(bottom ==  ls[i].getTop())
				return true;
		}
		for(int i=0; i<snakesCount; i++)//M
		{

			if(  bottom == ss[i].getHead()||bottom ==ss[i].getTail())
				return true;
			if(  head == ss[i].getHead())
				return true;
		}

		return false;
	}
	// Computes the new position taking into account the positions of the snakes 
	// and ladders
	public int newPos(int pos) //CH: Modify to check if a regular piece or a snake-player --MODIFY--
	{ 
		/*if regular piece, proceed with update position by ladder or snake
		 * else if snake-player, ignore ladder or snake and re-roll dice to change position*/
		int val = pos;

		for (int i=0; i<laddersCount; i++)	// starting from 0, and repeat for the amount of ladders in board
			if ( pos == ls[i].getBottom() )	// if position is equal to ladder(number) bottom position,
				val = ls[i].getTop();		// then val is equal to ladder(number) top position

		for (int i=0; i<snakesCount; i++)	// starting from 0, and repeat for the amount of snakes in board
			if ( pos == ss[i].getHead() )	// if position is equal to snake(number) head position
				val = ss[i].getTail();		// then val is equal to snake(number) tail position

		if ( val < pos)		// SNAKE: snake tail position(val) is less than snake head position(pos)
		{
			System.out.println("You are bitten by a snake. Press any key to continue"); //MALSHA
			String s = scan.nextLine();
			if(s.trim().isEmpty())//prevents error on enter key
				s = "no error";

		}        
		else if ( val > pos)	//LADDER: ladder top position(val) is greater than ladder bottom position(pos)
		{
			System.out.println("You are going up the ladder. Press any key to continue"); //MALSHA
			scan.nextLine();
		}        
		return val;
	}


	public int MomSnake (int pos)//MALSHA
	{

		for (int i=0; i<snakesCount; i++)
			if ( pos == ss[i].getHead() )
			{
				return i+1;
			}
		return 0;
	}
	public int Escape(int pos) //MALSHA
	{ 
		int val = pos;

		for (int i=0; i<laddersCount; i++)
			if ( pos == ls[i].getBottom() )
				val = ls[i].getTop();

		for (int i=0; i<snakesCount; i++)
			if ( pos == ss[i].getHead() )
				val = ss[i].getTail();

		if ( val < pos)
		{
			return 0;//snake
		}        
		else if ( val > pos)
		{
			return 1;//ladder
		}  
		else
		{
			return 2;//neither
		}  
	}
	public int snakeOnPlayerError(int head)//MALSHA -- can have many players on the snake head
	{
		int a = 0;
		for ( int i=0; i <pCount; i++)
		{

			if(head == players[i].getPos())
			{
				a++;
			}

		}
		return a;
	}
	public int snakeOnPlayer(int head,int tail)//MALSHA -- can have many players on the snake head
	{
		int a = 0;
		for ( int i=0; i <pCount; i++)
		{

			if(head == players[i].getPos()||tail == players[i].getPos())
			{
				a++;
			}

		}
		return a;
	}

	public Player getPlayerOnSnake(int head)//MALSHA
	{
		for ( int i=0; i <pCount; i++)
		{
			if(head == players[i].getPos())
				return players[i];
		}
		return players[0];
	}

	public void EscapecalcSP(int head,int tail, Player creator)//MALSHA --- escape pointts for pieces a snake player lands on
	{
		EscapePoint ep = new EscapePoint(); //M

		for ( int i=0; i <pCount; i++) // score for  regular players only
		{
			if(head == players[i].getPos()&& head!=creator.getPos()&& players[i].getIndex()!=creator.getIndex())
				//lands on piece other than the one from the player who gave rise to it
			{

				ep.setEscapePoint(players[i].getEscapePoint());
				boolean repeat = true;

				if(ep.getEscapePoint() > 0)
				{
					while(repeat)
					{
						System.out.println(" Press 1 to use snake escape point");
						int  condition = scan.nextInt();
						if(condition == 1)
						{
							ep.loseEscapePoint();//---M----16/5/16

							System.out.printf("\nyou now have %d escape point(s)\n",ep.getEscapePoint());

							repeat = false;	
						}

					}
					players[i].escpt = ep.getEscapePoint();
					totalEscape(players[i].getIndex(),players[i]);	
				}
				else
				{	

					players[i].setPos(tail);	// computes the new position based on the dice value  and does not give rise to a snake player
					repaint();		// causes the board and pieces to be redrawn
				}
			}
		}
	}
	public void invEscapecalcSP(int val, Player current)//MALSHA --- escape points for pieces that land on snakeplayer
	{
		EscapePoint ep = new EscapePoint(); //M

		for ( int i=0; i <pCount; i++) // score for  regular players only
		{
			if( players[i] instanceof SnakePlayer) //find the snake player previously on this position
			{
				//position is snakeplayer head ,  snakeplayer's creator is not the current player , snakeplayer's creators index is not the current players index
				if(val == players[i].getHead()&& players[i].getPlayer()!= current && (players[i].getCreatorindex()!= current.getIndex() ))
					//lands on piece other than the one from the player who gave rise to it
				{

					ep.setEscapePoint(current.getEscapePoint());
					boolean repeat = true;

					if(ep.getEscapePoint() > 0)
					{
						while(repeat)
						{
							System.out.println(" Press 1 to use snake escape point");
							int  condition = scan.nextInt();
							if(condition == 1)
							{
								ep.loseEscapePoint();//---M----16/5/16

								System.out.printf("\nyou now have %d escape point(s)\n",ep.getEscapePoint());

								repeat = false;	
							}

						}
						current.escpt = ep.getEscapePoint();
						totalEscape(current.getIndex(),current);	
					}
					else
					{	

						current.setPos(players[i].getTail());	// computes the new position based on the dice value  and does not give rise to a snake player
						repaint();		// causes the board and pieces to be redrawn
					}
				}
			}
		}
	}

	public void add(Snake s)
	{
		if ( snakesCount < 10)
		{
			ss[snakesCount] = s;
			snakesCount++;
		}
	}

	public void add(Ladder l)
	{
		if ( laddersCount < 10)
		{
			ls[laddersCount] = l;
			laddersCount++;
		}
	}



	public void remove(Snake s) //M & CH
	{

		for (int i=0; i<snakesCount; i++)
		{
			if (ss[i] == s)
			{
				int last = 0;
				for(int j = i+1;j<=snakesCount;j++,i++)
				{
					ss[i] = ss[j];// moves the array up to fill in missing space
					last = j;
				}

				ss[last] = null;// will be removed
			}


		}
		ss = Arrays.copyOf(ss,snakesCount--);//decreases array size by 1


	}

	public void remove(Player snp) //M & CH
	{
		for (int i=0; i<pCount; i++)	// starting from 0, and repeat for the amount of snakes-player in board
		{
			if( players[i] == snp && (players[i] instanceof SnakePlayer)) 
				// if player[i] mom is snake landed on then kill kid
			{

				int last = 0;
				for(int j = i+1;j<=pCount;j++,i++)
				{
					players[i] = players[j];// moves the array up to fill in missing space
					last = j;
				}
				players[last] = null;
				pCount--;
				spCount--;

			}	
		}
	}

	public Board()
	{
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());

		frame.getContentPane().add(this,BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640,520);
		frame.setVisible(true);
		setup();
		new Thread(this).start();
	}

	public void add(Player players[], int pCount)
	{
		this.players = players;
		this.pCount = pCount; 
	}
	public int getPCount()//returns the number of registered users
	{
		return (pCount);
	}
	public int getSPCount()//returns the number of registered users
	{
		return (spCount);
	}

	//Malsha: add new snake player
	public void createSnakeP(Player playerindex, int Snakeindex)
	{
		Dice dice = new Dice(getGraphics()); 
		headAndTail();

		int head = sp.getHead();

		int tail = sp.getTail();

		Snake mommy = ss[Snakeindex]; //gets the mother snake
		int logIndex = players[pCount-1].getIndex() +1;//add one to index of last player
		players[pCount] = new SnakePlayer(this,dice,logIndex,0,"SnakePlayer ",head,tail,mommy,playerindex);//M   
		/////////////////////////////////////////////////////////////
		pCount++;//increases the number of players by one

		spCount++;//the number of snake players for this player

		add(players,pCount);//updates the player array
	}

	// This method is used to animate the snake
	// The timing can be changed by varying the sleep time
	public void run()
	{
		double inc = 0.05;
		while (true)
		{
			try {
				Thread.sleep(120); //CH: modified snake animate speed from 1000
			}
			catch (Exception e) {}
			Draw.factor += inc;; 
			if (Draw.factor > 0.5 || Draw.factor < -0.5)
				inc = -inc;
			repaint();
		}
	}

	// this method is called in response to repaint 
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		for (int i=0; i<10; i++)
			for (int j=0; j<10; j++){
				if ((i+j)%2 == 0) 
					g.setColor(Color.GRAY); 			//CH:  modified board color yellow
				else 
					g.setColor(Color.DARK_GRAY);      	//CH: modified board color orange    
				g.fillRect(XMARGIN + 40*i,YMARGIN+40*j, 40,40);
			}
		g.setColor(Color.BLACK);
		for ( int i=0; i<100; i++)    
			if ( (i/10) % 2 == 0 )         
				g.drawString("" + (i+1),XMARGIN + 5 + i%10 * 40 
						,YMARGIN -5 + 400 - i/10 * 40);
			else
				g.drawString("" + (i+1),XMARGIN  + 370 - (i%10 * 40) 
						,YMARGIN - 5 + 400 - i/10 * 40);

		for (int i=0; i<snakesCount; i++)
			ss[i].draw(g);
		for (int i=0; i<laddersCount; i++)
			ls[i].draw(g);
		for (int i=0; i<pCount; i++)
			players[i].draw(g);     
	}
	public void setPCount(int pCount) //MALSHA
	{
		this.pCount = pCount;

	}
}