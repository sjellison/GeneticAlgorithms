package ga;

import java.util.Random;

public class Eye {

	public final static int BLUE = 0;
	public final static int BROWN = 1;
	public final static int GREEN = 2;
	public final static int NUMCOLORS = 3;
	
	private int color; //0-Blue, 1-Brown, 2-Green
	private Random rand = new Random();
	
	//TODO Eye cannot take in a null value because all constructors have only one input and null resolves to both
//	Eye(Integer color) throws EyeException //Desired implementation
	Eye(int color) throws EyeException
	{
		if(color < 0 || color > 2)
		{
			throw new EyeException("Error making eye: " + color + " 0-Blue, 1-Brown, 2-Green");
		}
		
//		if(color == null)
//		{
//			this.color = rand.nextInt(3);
//		}
//		else
//		{
			this.color = color;
//		}
	}
	
	Eye(Eye e)
	{
		this.color = e.color;
	}
	
	Eye()
	{
		color = rand.nextInt(NUMCOLORS);
	}
	
	public int getColor()
	{
		return color;
	}
	
	public void setColor(int color) throws EyeException
	{
		if(color < 0 || color > 2)
		{
			throw new EyeException("Error making eye: " + color + " 0-Blue, 1-Brown, 2-Green");
		}
		
		this.color = color;
	}
	
	public void mutate()
	{
		if(color == 0)
		{
			color += 2;
		}
		else
		{
			color -= 1;
		}
	}
	
	public void mutate(Eye e)
	{
		if(color < e.color)
		{
			color += 1;
		}
		else if(color > e.color)
		{
			color -= 1;
		}
	}
	
	public boolean equals(Eye e)
	{
		if(color == e.color)
		{
			return true;
		}
		return false;
	}
	
	public Eye copy() throws EyeException
	{
		return new Eye(color);
	}
	
	class EyeException extends Exception
	{
		EyeException(String s)
		{
			super(s);
		}
	}
}
