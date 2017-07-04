package slxGame;
import java.io.*;
import java.util.*;
import java.awt.*;

import javax.swing.*;


class Player extends Draw
{
	private String fname; //R
	private String lname; //R
	private String username; //R
	private String password; //R
	private int count = 0; //R
	protected int pos = 1;	// the current position of the player piece
	protected int index;		// represents the player index 0, 1, 2 or 3 if 4 players
	protected Dice dice;
	protected Board bd;
	protected int escpt = 0;	// M
	private int score;		//M
	Player users[] = new Player[100]; //R
	EscapePoint ep = new EscapePoint(); //M
	private String nfname;//M
	private int storedindex;//M
	private int pieceno;//M
	static Scanner scan = new Scanner(System.in);

	//R
	public String getFname()
	{
		return fname;
	}
	//R
	public void setFname(String fname)
	{
		this.fname = fname;
	}
	public String getnFname()//M
	{
		return nfname;
	}



	//R
	public String getLname()
	{
		return(lname);
	}
	//R
	public String getUsername()
	{
		return(username);
	}	
	//R
	public String getPassword()
	{
		return(password);
	}
	//R
	public int getCount()//M -- returns the number of registered users
	{
		return (count);
	}
	public int getPos()//M
	{
		return pos;
	}
	protected void setPos(int p)//M
	{
		pos = p;
	}

	public int getEscapePoint() //M
	{
		return (escpt);
	}	
	//M
	public int getScore() //M
	{
		return (score);
	}	
	public void setScore(int score) //M
	{
		this.score = score;
	}
	public int getsIndex() //M
	{
		return (storedindex);
	}	
	public int getIndex() //M
	{
		return (index);
	}	
	public int getPieceNo() //M
	{
		return (pieceno);
	}

	public void setEscapePoint(int escpt) //M
	{
		this.escpt = escpt;
	}	

	//R
	public Player()
	{
		//Default constructor
		try
		{
			Scanner input = new Scanner(new File("players.txt"));
			//System.out.println("Reading from the file");
			while(input.hasNext())
			{
				String firstname = input.next();
				String lastname = input.next();
				String username = input.next();
				String password = input.next();
				int score = input.nextInt();
				input.nextLine();
				users[count++] = new Player(firstname, lastname, username, password, score);	
			}
			/*for(int r = 0 ; r < count ; r++)
			{
				System.out.println(users[r].getFname());
			}*/
			input.close();	
		}
		catch(Exception e)
		{

		}
	}
	//R
	public Player(String fname, String lname, String username, String password, int score)
	{
		this.fname = fname;
		this.lname = lname;
		this.username = username;
		this.password = password;
		this.score = score;
	}
	//M
	public Player(Board bd, Dice dice, int index,int storedindex,int pieceno, int pos, String nfname, int score)
	{
		this.bd = bd;
		this.nfname = nfname;
		this.pos = pos;
		this.index = index;
		this.dice = dice;
		this.score = score;
		this.storedindex = storedindex;
		this.pieceno = pieceno;

	}
	//R
	public void write()
	{
		PrintWriter p = null;
		try
		{
			p = new PrintWriter(new BufferedWriter(new FileWriter("players.txt")));
			for(int i = 0 ; i < count ; i++)
			{
				p.println(users[i].getFname()+"\t"+users[i].getLname()+"\t"+users[i].getUsername()+"\t"+users[i].getPassword()+"\t"+users[i].getScore());
			}
			p.close();	
		}
		catch(Exception e)
		{

		}
	}
	//R : Function to let a user login
	public int authenticateUser()
	{
		Scanner get = new Scanner(System.in);
		String usern;
		String passwd;
		System.out.println("Login:");
		System.out.println("Enter a username");
		usern = get.next();
		System.out.println("Enter a password");
		passwd = get.next();
		int c = invalidUname(usern);
		int d = invalidUser(usern, passwd);
		if(c == 1)
		{
			
			System.out.println("Invalid username");
			System.out.print("If you have not registered, press any key to register or enter '0'  to type in the login again :");
			String ch1 = scan.nextLine();
			if(ch1.trim().isEmpty())//prevents error on enter key
				return 0;
			char ch = ch1.charAt(0);
			if(ch == '0')
				return 0;
			else
			{
				int e = registerUser();
				if(e == 2)
				{
					c = 2;
					d = 2;
					System.out.println("Registered User Successfully");
					return 0;
				}	

			}

		}
		if(d == 3)
		{
			System.out.println("Invalid Combination of Username and Password");
			return 0;
		}
		if(c == 0 && d == 0)
		{
			System.out.println("Login Successful");
			return indexvalue (usern)+1; // avoids returning 0
		}

		//get.close();
		return 1;
	}

	//R : Function to register a player
	public int registerUser()
	{
		String firstname;
		String lastname;
		String uname;
		String pwd;
		int score = 0;
		Scanner sc = new Scanner(System.in);
		int i;
		System.out.println("Registration:");
		System.out.println("Enter Firstname");
		firstname = sc.next();
		System.out.println("Enter Lastname");
		lastname = sc.next();
		System.out.println("Enter a unique username");
		uname = sc.next();
		for(i = 0; i< count ; i++)
			while(users[i].getUsername().compareTo(uname)==0)
			{
				System.out.println("Username taken. Please enter a different one");
				uname = sc.next();
			}
		System.out.println("Enter a password");
		pwd = sc.next();	
		if(pwd.length() < 8)
		{
			System.out.println("Oops! Your password is short.");
			System.out.println("Please enter a password that is atleast 8 characters long");
			pwd = sc.next();
		}
		//System.out.println("Registered User Successfully");
		Player user = new Player(firstname, lastname, uname, pwd, 0); //The score of a newly registered player will be set to 0
		users[count++] = user; 	
		write();
		//sc.close();
		return 2;
	}
	//R : Function to check valid user
	public int invalidUser(String uname, String pwd)
	{
		int i;
		for(i = 0 ; i < count ; i++)
		{
			if(users[i].getUsername().equals(uname) && users[i].getPassword().equals(pwd))
			{

				return 0; //returns 0 for valid combination of username and password
			}
		}
		return 3; //returns 3 for invalid combination of username and password
	}

	//R : Function to check valid username
	public int invalidUname(String uname)
	{
		int j;
		for(j = 0; j< count ; j++)
		{
			if(users[j].getUsername().equals(uname))
				return 0; //returns 0 if the username exists in the file
		}
		return 1; //returns 1 if the username does not exist in the file
	}
	public int indexvalue (String uname)
	{
		int j;
		for(j = 0; j< count ; j++)
		{
			if(users[j].getUsername().equals(uname))
				return j; //returns 0 if the username exists in the file
		}
		return j;
	}

	/*M: Computes player's new position with val from dice.*/
	public void computePos(int val)
	{
		pos = bd.newPos(pos);
	}
	public void Escapecalc(int val)//M
	{
		if ( pos + val <= 100)
		{
			pos += val;      
		}

		int type = bd.Escape(pos);
		ep.setEscapePoint(escpt);

		switch (type)
		{
		case 0 : 
			boolean repeat = true;

			if(ep.getEscapePoint() > 0)
			{
				while(repeat)
				{
					System.out.println(" Press 1 to use snake escape point");
					int  condition = scan.nextInt();
					if(condition == 1)
					{
						ep.loseEscapePoint();//sets value in player array

						System.out.printf("\nyou now have %d escape point(s)\n",ep.getEscapePoint());

						repeat = false;	
					}
				}

			}
			else
			{	
				int snakeindex = bd.MomSnake(pos)-1;
				computePos(val);	// computes the new position based on the dice value  
				bd.createSnakeP(this, snakeindex);
				bd.repaint();		// causes the board and pieces to be redrawn

			}
			break;
		case 1 : 

			computePos(val);	// computes the new position based on the dice value  
			ep.gainEscapePoint() ;//sets value in player array
			System.out.printf("you have gained an escape point\n you now have %d escape point(s)\n",ep.getEscapePoint());
			bd.repaint();		// causes the board and pieces to be redrawn

			break;

		case 2:
			computePos(val);	// computes the new position based on the dice value 
			bd.SnakeTail(pos);//M----WRITTEN--15/5/16
			bd.repaint();		// causes the board and pieces to be redrawn
			break;
		}

		escpt = ep.getEscapePoint();// was used to get escape poitns when using only one player piece each
		bd.totalEscape(index,this);//sets all other pieces of this players to the same escape pts
	}

	// Causes the dice to be thrown and the new position to be computed
	public int move() //M
	{
		System.out.println("***** Turn of  " + nfname + " piece "+ (getPieceNo()+1) +" ******" );           
		String resp;
		int val;
		do {
			System.out.print("Press Enter to throw dice ");
			resp = scan.nextLine();
			System.out.println("Rolling the dice .... Please wait");
			val = dice.roll();
			System.out.println("You threw a " + val);
			Escapecalc(val);//M
			System.out.println(nfname + " is now at position " + pos);   
			if (pos == 100)
				return index;	// returns the index of the player winning the game

			if (val == 6)
				System.out.println("You get to throw again");
			int a = bd.snakeOnPlayerError(pos);
			if(a>0)//M -- a indicates number of players on the head
			{
				for(int i=0;i<a;i++)
				{
					//the snake head moves to a position with player(s) on it
					bd.invEscapecalcSP(pos,this);//either uses escape points or moves player(s) to tail				
				}
			}

		} while (val == 6);
		return -1;
	}

	// Drawing the positions of the player pieces using different colors 
	public void draw(Graphics g)
	{
		String piece =Integer.toString(pieceno+1);
		if (index == 0) //CH: Player 1
		{
			g.setColor(Color.RED);   //CH colors modified, 
			g.fillOval((int)getX(pos)-10,getY(pos)-10,20,20); 
			g.setColor(Color.BLACK);   
			g.drawString(piece,(int)getX(pos)-5,getY(pos)+5); 
		}
		if (index == 1) //CH: Player 2
		{
			g.setColor(Color.BLUE);   //CH
			g.fillOval((int)getX(pos)+10,getY(pos)-10,20,20); 
			g.setColor(Color.BLACK);   
			g.drawString(piece,(int)getX(pos)+15,getY(pos)+5); 
		}
		if (index == 2) //CH: Player 3
		{
			g.setColor(Color.GREEN);   //CH
			g.fillOval((int)getX(pos)-10,getY(pos)+10,20,20); 
			g.setColor(Color.BLACK);   
			g.drawString(piece,(int)getX(pos)-5,getY(pos)+25); 
		}
		if (index == 3) //CH: Player 4
		{
			g.setColor(Color.YELLOW);   //CH
			g.fillOval((int)getX(pos)+10,getY(pos)+10,20,20); 
			g.setColor(Color.BLACK);   
			g.drawString(piece,(int)getX(pos)+15,getY(pos)+25); 
		}
	}
	public int getHead() 
	{
		return 0;
	}
	public Player getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}
	public int getTail() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int getCreatorindex() {
		// TODO Auto-generated method stub
		return 0;
	}

}