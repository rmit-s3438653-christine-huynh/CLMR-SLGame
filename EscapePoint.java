package slxGame;

class EscapePoint //MALSHA
{
	private int MAXESCAPEPOINTS = 3;
	private int escapePt;
	public int getEscapePoint()
	{
		return escapePt;
	}
	public void setEscapePoint(int escapePt) {
		this.escapePt = escapePt;
	}

	public int gainEscapePoint() 
	{
		if(getEscapePoint() == MAXESCAPEPOINTS) 
		{
			return escapePt;
			 
		}
		else
		{
			return escapePt++;
		}
	}
	public int loseEscapePoint()
	{
		if(getEscapePoint() <= 0) 
		{
			return escapePt;
		}
		else
		{
			return escapePt--;
		}
	}
}