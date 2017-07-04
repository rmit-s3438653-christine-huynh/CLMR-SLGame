/* Written by Charles Theva as part of the Introductory Programming Course  */
/* You are free to refactor and modify the program 							*/
package slxGame;
import java.util.*;



// The main system level class 
public class SLXGame
{

	private int pCount;
	private Board bd;
	private int turn = 0;
	//size is large due to snakeplayers not having a limit
	private Player players[] = new Player[100];//can have max 4 players with 2 additional pieces each
	static Scanner scan = new Scanner(System.in);
	private int regPCount = 0 ;//count before snakeplayers added in
	private int PlayersNo = 4;//no of logged in players
	private int maxPlayer = 0;//input from user

	public static void main(String args[])
	{    
		SLXGame sg = new SLXGame();
	}
	public SLXGame()
	{
		bd = new Board();
		char ch;
		System.out.println("***********Login***********");
		playerLogin();//all players must login or register to play the game

		// add the login in here before the menu
		do {
			ch = displayMenu();
			switch (ch)
			{
			case '1' : play();break;//Malsha
			case '2' :
				LandSSet();
				bd.customize(); break;
			case '3' : scoreBoard();break;
			}
		} while (ch != '4'); //CH: Quit

	}

	public char displayMenu()//MALSHA
	{
		System.out.println("*********** MENU **********");
		System.out.println("**    Snakes & Ladders   **"); 
		System.out.println("***************************");
		System.out.println("1.Play Game...............1");
		System.out.println("2.Customize Board.........2");
		System.out.println("3.View top 5 Players......3");
		System.out.println("4.Exit....................4");

		System.out.println("Enter 1/2/3/4     : ");
		System.out.println("***************************");
		String s = scan.nextLine();
		if(s.trim().isEmpty())//prevents error on enter key
			return '5';
		char snew = s.charAt(0);
		return snew;
	}

	public void changeTurn()
	{
		turn++;

		if (turn == (PlayersNo + bd.getSPCount()))//players plus snakeplayers
			turn = 0;
	}

	

	private void LandSSet() //MALSHA
	{

		int snake = 0, ladder = 0 ;
		for ( int i=0; i <regPCount; i=i+maxPlayer)// loops for each logged in  player
		{
			System.out.println("Enter values for player  " + players[i].getnFname() );
			System.out.println("--------------------------------------------");
			bd.numSandL();
			snake += bd.getnumSnakes();
			ladder += bd.getnumLadders();

		}
		bd.setnumSnakes((int)Math.ceil(snake/maxPlayer));
		bd.setnumLadders((int)Math.ceil(ladder/maxPlayer));

		System.out.println("*******************************************");
	}

	private void playerLogin() 
	{
		Player p = new Player();

		boolean wrong = false;
		do {
			wrong = false;
			try//M
			{

				System.out.print("Enter number of players to login: ");
				pCount = scan.nextInt();
			}
			catch(InputMismatchException ime)//M
			{
				wrong = true;
				System.out.println("please enter a numerical value");

			}
			if ( pCount> 4 || pCount < 2 && !wrong) //CH & M: Min players 2, max 4.
			{
				System.out.println("Minimum 2 players and Maximum 4 players");

			}
			scan.nextLine();
		} while( pCount> 4 || pCount < 2|| wrong);

		int auth = 0;//M
		PlayersNo = pCount;// sets the number of logged in players
		//sets the number of pieces per player
		for ( int i=0; i <pCount; i++)//M
		{
			System.out.println("player"+(i+1)+":");
			bd.numP();
			maxPlayer += bd.getmaxnumP(); 
		}		
		maxPlayer = (int)Math.ceil(maxPlayer/pCount);//M
		bd.setnumPlayers(maxPlayer);//M
		int totalPIndex = 0;//M
		int[] authen = new int[pCount];//M
		for ( int i=0; i <pCount; i++)//M
		{
			boolean duplicateuser = false;

			do {
				System.out.print("player " + (i + 1) + " -");
				auth = p.authenticateUser() ;
				authen[i] = auth;
				duplicateuser = false;
				for(int num = 0;num<authen.length;num++)
				{
					if((authen[num]==authen[i])&&(num!=i)&&authen[i]!=0)
					{
						duplicateuser = true;
						System.out.println("Cannot login more than once with the same login\nPlease re-enter details for this player");
					}
				}
			} while (auth == 0||duplicateuser);

			Dice dice = new Dice(bd.getGraphics());
			for ( int a = 0; a <maxPlayer; a++,totalPIndex++)//M
			{
				players[totalPIndex] = new Player(bd,dice,i,auth-1,a,1,p.users[auth-1].getFname(),p.users[auth-1].getScore());//M   

			}
		}
		pCount *= maxPlayer; //M ---count including all the pieces
		bd.add(players,pCount);//M
		regPCount = pCount; //M
		bd.setPCount(pCount);//M

	}
	
	public void scoreBoard ()//Radha
	{
		Player s = new Player();
		int i,j;
		Player temp = new Player();
		for(i = 0 ; i < s.getCount() ; i++)
		{
			for(j = 0 ; j < s.getCount() ; j++)
			{
				if(s.users[i].getScore() >= s.users[j].getScore())
				{
					temp = s.users[i];
					s.users[i] = s.users[j];
					s.users[j] = temp;
				}
			}
		}
		System.out.println("-----------------------------------Snakes & Ladders - Extended-------------------------------------\n \n");
		System.out.println("-----------------------------------------SCOREBOARD------------------------------------------------\n\n");
		System.out.println("Player\t\tUsername\tScore\n");
		for(i = 0 ; i < 5 ; i++)
		{
			System.out.println(s.users[i].getFname()+"\t\t"+s.users[i].getUsername()+"\t\t"+s.users[i].getScore()+ "\n");
		}
	}


	public void play()//MALSHA
	{

		Player p = new Player();
		boolean nextTurn = true;
		int numOfP = 1;
		boolean notnumber = false;
		boolean leave = false;
		while(true)
		{

			if(nextTurn&&turn<PlayersNo)
			{
				System.out.println("*************************");
				System.out.println("enter which piece to move");
				do{
					notnumber = false;
					try//M
					{
						numOfP= scan.nextInt();
					}
					catch(InputMismatchException ime)
					{


						System.out.println("please enter a number");
						scan.nextLine();
						notnumber = true;

					}
				}while(notnumber);
			}


			for(int x = 0; x < bd.getPCount();x++)
			{
				

				if(players[x].getPieceNo()== numOfP-1 && players[x].getIndex()== turn)
				{
					int pos = players[x].move();   // players get to move in turn

					if ( pos != -1)
					{
						System.out.println("**** GAME OVER " 
								+ players[x].getnFname() 
								+ " is the winner ******");

						for ( int i = x; i <regPCount; i=i+maxPlayer) // score for  regular players only
						{
							if(i != x)
							{
								int sc = players[i].getScore();
								int index = players[i].getsIndex();
								p.users[index].setScore(--sc);
							}
							if(i == x)
							{
								int sc = players[i].getScore();
								int index = players[i].getsIndex();
								p.users[index].setScore(++sc);
							}

						}
						leave = true;
						p.write();
						break;
					}
					else
					{
						changeTurn();
						nextTurn = false;
						if(turn<PlayersNo)
						{
							System.out.println("*************************");

							do{
								notnumber = false;
								System.out.println("enter which piece to move");
								try//M
								{
									numOfP= scan.nextInt();
								}
								catch(InputMismatchException ime)//M
								{


									System.out.println("please enter a number");
									scan.nextLine();
									notnumber = true;

								}

							} while(notnumber||numOfP< 1||numOfP>maxPlayer);

						}
						else
							numOfP=1;
					}
				}

			}
			if(leave)
				break;
		}

	}
}
