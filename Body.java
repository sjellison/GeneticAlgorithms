package ga;

import java.util.Random;

/**
 * 
 * @author Sean Jellison
 *
 *	A body object that represents a person's physical characteristics.
 *
 */
public class Body
{
	
	public final static int MINHEIGHT = 22;
	public final static int MAXHEIGHT = 108;
	public final static int MINWEIGHT = 4;
	public final static int MAXWEIGHT = 1400;
	public final static float MINPBF = 0.02f;
	public final static float MAXPBF = 0.85f;
	
	private int height; //min: 22, max: 108
	private int weight; //min: 4, max: 1400
	private float percentBodyFat; //min: .02, max: .85
	private Random rand = new Random();
	
	
	/**
	 * Constructor for a Body object.
	 * @param height
	 * 	How tall the person is in inches. Minimum of 22 and maximum of 108, based on world records.
	 * @param weight
	 * 	How much a person weighs in pounds. Minimum of 4 and maximum of 1400, based on world records.
	 * @param percentBodyFat
	 * 	The ratio of a person's weight that comes from fat. Minimum of 0.02, based on athletes, maximum of 0.85, based on estimations.
	 * @throws BodyException
	 */
	Body(Integer height, Integer weight, Float percentBodyFat) throws BodyException
	{
		if(height == null)
		{
			this.height = rand.nextInt(87) + 22;
		}
		else
		{
			if(height < MINHEIGHT || height > MAXHEIGHT)
			{
				throw new BodyException("Error height out of bounds: " + height + " Height must be 22-108 inclusive");
			}
			this.height = height;
		}
		
		if(weight == null)
		{
			this.weight = rand.nextInt(1306) + 4;
		}
		else
		{
			if(weight < MINWEIGHT || weight > MAXWEIGHT)
			{
				throw new BodyException("Error weight out of bounds: " + weight + " weight must be 4-1400 inclusive");
			}
			this.weight = weight;
		}
		
		if(percentBodyFat == null)
		{
			this.percentBodyFat = rand.nextFloat() % .84f + .02f;
		}
		else
		{
			if(percentBodyFat < MINPBF || percentBodyFat > MAXPBF)
			{
				throw new BodyException("Error PBF out of bounds: " + percentBodyFat + " PBF must be .02-.85");
			}
			this.percentBodyFat = percentBodyFat;
		}
	} //end body constructor
	
	/**
	 * Constructs a new Body object by making a deep copy of the given Body.
	 * @param b
	 * 	The Body object to copy
	 */
	Body(Body b)
	{
		this.height = b.height;
		this.weight = b.weight;
		this.percentBodyFat = b.percentBodyFat;
	}
	
	/**
	 * Returns the person's height in inches.
	 * @return
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * Returns the person's weight in pounds.
	 * @return
	 */
	public int getWeight()
	{
		return weight;
	}
	
	/**
	 * Returns the person's percent body fat.
	 * @return
	 */
	public float getPBF()
	{
		return percentBodyFat;
	}
	
	/**
	 * Sets the person's height. 
	 * @param height
	 * 	The new height of the person in inches.
	 * @throws BodyException
	 * 	Throws an exception if the new height is out of range.
	 */
	public void setHeight(int height) throws BodyException
	{
		if(height < MINHEIGHT || height > MAXHEIGHT)
		{
			throw new BodyException("Error setting height: " + height + " Height must be 22-108 inclusive.");
		}
		else
		{
			this.height = height;
		}
	}
	
	/**
	 * Sets the person's weight.
	 * @param weight
	 * 	The new weight in pounds.
	 * @throws BodyException
	 * 	Throws an exception if the new weight is out of range.
	 */
	public void setWeight(int weight) throws BodyException
	{
		if(weight < MINWEIGHT || weight > MAXWEIGHT)
		{
			throw new BodyException("Error setting weight: " + weight + " Weight must be 4-1400 inclusive.");
		}
		else
		{
			this.weight = weight;
		}
	}
	
	/**
	 * Sets the person's percent body fat (the ratio of weight that comes from the person's fat)
	 * @param percentBodyFat
	 * 	The person's new body fat percentage.
	 * @throws BodyException
	 * 	Throws an exception is the new percentage body fat is out of range.
	 */
	public void setPBF(float percentBodyFat) throws BodyException
	{
		if(percentBodyFat < MINPBF || percentBodyFat > MAXPBF)
		{
			throw new BodyException("Error setting percentBodyFat: " + percentBodyFat + " PBF must be .02-.85 inclusive.");
		}
		else
		{
			this.percentBodyFat = percentBodyFat;
		}
	}
	
	/**
	 * Mutates the body randomly.
	 * (For now, this will only affect the person's height because their weight and percent body fat are affected almost exclusively by outside factors, but affect a person's appearance)
	 */
	public void mutate()
	{
		if(rand.nextInt(100) < 33) //33 makes it 33% of the mutates to be to shrink
		{
			this.height -= 1;
		}
		else
		{
			this.height += 1;
		}
	}
	
	/**
	 * Mutates the body slightly to be more like the given Body.
	 * @param b
	 * 	The Body this person mutates to be more like.
	 */
	public void mutate(Body b)
	{
		if(this.height < b.height)
		{
			this.height += 1;
		}
		else if(this.height > b.height)
		{
			this.height -= 1;
		}
	}
	
	/**
	 * Creates a deep copy of this Body object.
	 * @return
	 * 	Returns a deep copy of this Body object.
	 * @throws BodyException
	 */
	public Body copy() throws BodyException
	{
		return new Body(this);
	}
	
	/**
	 * Determines if two bodies are equal.
	 * @param b
	 * 	The Body to compare to.
	 * @return
	 * 	Returns true if the two bodies have equal height, weight, and percent body fat
	 */
	public boolean equals(Body b)
	{
		if(this.height == b.height)
		{
			if(this.weight == b.weight)
			{
				if(this.percentBodyFat == b.percentBodyFat)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	class BodyException extends Exception
	{
		BodyException(String s)
		{
			super(s);
		}
	}
}
