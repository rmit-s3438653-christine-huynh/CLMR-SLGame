package slxGame;

import java.util.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class JUnitTestLogin 
{
	Player l1 = new Player();
	//TestLogin t = new TestLogin();
	
	Scanner sc1 = new Scanner(System.in);
	String passwd;
	String usern;


	
	@Test
	//Invalid username
	public void testInvalidUsername () {
		usern = "josh";
		passwd = "123456";
		int c = l1.invalidUname(usern);
		assertEquals(c,1);
	}

	@Test
	//Invalid combination of username and password
	public void testInvalidUser() 
	{
	usern = "sradhu8";
	passwd = "qwerty1";
	int d = l1.invalidUser(usern,passwd);
	assertEquals(d,3);
	}

	@Test
	//Valid combination of username and password
	public void testValidUser() 
	{
	usern = "sradhu8";
	passwd = "qwerty123";
	int d = l1.invalidUser(usern, passwd);
	assertEquals(d,0);
	}

/*	public static void main(String args[])
	{
	
		usern = sc1.next();
		System.out.println("Enter password");
		passwd = sc1.next();
		int c = t1.invalidUname(usern);
		System.out.println("c = " +c);
		int d = t.invalidUser(usern, passwd);
		System.out.println("d = " +d);
		 
		if(c == 1)
		{
			System.out.println("Invalid username");
			System.out.println("If you have not registered, please register to play");
			int e = t.registerUser();
			if(e == 2)
			{
				c = 2;
				d = 2;
				System.out.println("Registered User Successfully");
			}	
		}
				
		if(d == 3)
			System.out.println("Invalid Combination of Username and Password");
		
		if(c == 0 && d == 0)
			System.out.println("Login Successful");
		sc1.close();
	}*/
	
}
