package slxGame;


abstract class Draw
{
   protected static int XMARGIN = 20;
   protected static int YMARGIN = 20;
   protected static double factor = 0.2;

   public static int getX(int pos)
   {
      pos--;
      if ( (pos/10) % 2 == 0 )
	  return XMARGIN + 10 + pos%10 * 40;
      else
	  return  XMARGIN  + 370 - pos%10 * 40;
   }
   public static int getY(int pos)		// returns the x coordinate given the board position 
   {
      pos--;
      return YMARGIN - 30 + 400 - pos/10 * 40; // returns the y coordinate given the board position
   }
}