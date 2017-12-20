package ga;

import java.util.Random;

/**
 * 
 * @author Sean Jellison
 *
 *	A Hair object that represents the natural hair of a person.
 *
 */
public class Hair {

	public final static int NUMCOLORS = 4;
	public final static int MAXCURLY = 100;
	public final static int MINCURLY = 0;
	public final static int MINLENGTH = 0;
	public final static int BLOND = 0;
	public final static int BLACK = 1;
	public final static int BROWN = 2;
	public final static int RED = 3;
	
	private int color; //0 == Blonde, 1 == Black, 2 == Brown, 3 == Red
	private int maxLength; //No limit, this is self imposed
	private int curly; //Between 0 - 100 inclusive
	
	private Random rand = new Random();
	
	/**
	 * Constructor for a Hair object.
	 * @param color
	 * 	The hair color represented as an integer. Zero based.
	 * @param maxLength
	 * 	The maximum hair length of a person in inches. Minimum of 0, default of 10. 
	 * @param curly
	 * 	A representation of how curly the hair is. 0 is perfectly straight, 100 is very curly. (human average would be around 30-40, but how this is interpreted is up to the implementer)
	 * @throws HairException
	 */
	Hair(Integer color, Integer maxLength, Integer curly) throws HairException
	{
		if(color == null)
		{
			this.color = rand.nextInt(4);
		}
		else
		{
			if(color < 0 || color >= NUMCOLORS)
			{
				throw new HairException("Hair color out of range: " + color);
			}
			this.color = color;
		}
		
		if(maxLength == null)
		{
			this.maxLength = 10;
		}
		else
		{
			if(maxLength < 0)
			{
				throw new HairException("Error setting hair length: " + maxLength + " Must be non-negative.");
			}
			this.maxLength = maxLength;
		}
		
		if(curly == null)
		{
			this.curly = rand.nextInt(101);
		}
		else
		{
			if(curly < 0 || curly > 100)
			{
				throw new HairException("Invalid Curliness: " + curly + " Must be between 0-100 inclusive.");
			}
			this.curly = curly;
		}
	}
	
	/**
	 * Constructor that makes a deep copy of the given Hair object.
	 * @param h
	 * 	The Hair object to make a deep copy of.
	 */
	Hair(Hair h)
	{
		this.color = h.color;
		this.curly = h.curly;
		this.maxLength = h.maxLength;
	}
	
	/**
	 * Mutates the hair object randomly.
	 */
	public void mutate()
	{
		//TODO NEED TO DETERMINE WHAT VALUES RELATE TO WHAT HAIR COLOR
		//Possible hair colors: Blonde, Black, Brown, Red
		int r = rand.nextInt() % 100;
		
		if(r < curly)
		{
			maxLength -= 1;
			color += 1;
		}
		else
		{
			color -=1;
			maxLength +=1;
		}
		
		if(color > curly)
		{
			curly -= 1;
		}
		else if(color == curly)
		{
			curly += 2;
		}
		
		if(curly < 0)
		{
			curly = 100;
		}
		else if(curly > 100)
		{
			curly = 0;
		}
	}
	
	/**
	 * Mutates the Hair object slightly to be more like the given Hair object.
	 * @param target
	 * 	The Hair object to become more like.
	 */
	public void mutate(Hair target)
	{
		if(target.curly > curly)
		{
			curly += 1;
		}
		else if(target.curly < curly)
		{
			curly -= 1;
		}
		if(target.color > color)
		{
			color += 1;
		}
		else if (target.color < color)
		{
			color -= 1;
		}
		if(target.maxLength > maxLength)
		{
			maxLength += 1;
		}
		else if(target.maxLength < maxLength)
		{
			maxLength -= 1;
		}
	}
	
	/**
	 * Returns the Hair color
	 * @return
	 */
	public int getColor()
	{
		return color;
	}
	
	/**
	 * Returns the Hair curliness
	 * @return
	 */
	public int getCurly()
	{
		return curly;
	}
	
	/**
	 * Returns the maximum length of the Hair
	 * @return
	 */
	public int getMaxLength()
	{
		return maxLength;
	}
	
	/**
	 * Sets the Hair's color
	 * @param color
	 * 	Color to change to
	 * @throws HairException
	 */
	public void setColor(int color) throws HairException
	{
		if(color < 0 || color >= NUMCOLORS)
		{
			throw new HairException("Error setting color: " + color + " 0=Blonde, 1=Black, 2=Brown, 3=Red");
		}
		else
		{
			this.color = color;
		}
	}
	
	/**
	 * Sets the Hair's curliness
	 * @param curly
	 * 	How curly to make the hair
	 * @throws HairException
	 */
	public void setCurly(int curly) throws HairException
	{
		if(curly < 0 || curly > 100)
		{
			throw new HairException("Error setting curly: " + curly + " Must be between 0-100 inclusive");
		}
		else
		{
			this.curly = curly;
		}
	}
	
	/**
	 * Sets the Hair's max length
	 * @param maxLength
	 * 	The maximum length the Hair can be in inches. Minimum of 0.
	 * @throws HairException
	 */
	public void setMaxLength(int maxLength) throws HairException
	{
		if(maxLength < 0)
		{
			throw new HairException("Error setting maxLength: " + maxLength + " Must be non-negative value");
		}
		else
		{
			this.maxLength = maxLength;
		}
	}
	
	/**
	 * Compares this Hair object to the given Hair object.
	 * @param h
	 * 	The Hair object to compare to.
	 * @return
	 * 	Returns true if the Hair's color, curliness, and maximum length are equal
	 */
	public boolean equals(Hair h)
	{
		if(this.color == h.color)
		{
			if(this.curly == h.curly)
			{
				if(this.maxLength == h.maxLength)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Makes a deep copy of this Hair object
	 * @return
	 * 	Returns a deep copy of this Hair object
	 */
	public Hair copy()
	{
		return new Hair(this);
	}
	
	class HairException extends Exception
	{
		public HairException(String s)
		{
			super(s);
		}
	}
}
