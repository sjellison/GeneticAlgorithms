package ga;

import java.util.Random;

import ga.Body.BodyException;
import ga.Hair.HairException;

public class Beauty {

	private Eye eyes;
	private Body body;
	private Hair hair;
	private boolean gender; //False == Female, True == Male
	private Beauty target;
	private float mutationRate;
	private Random rand = new Random();
	
	/**
	 * Makes a new Beauty object, explicitly
	 * @param eyes
	 * @param body
	 * @param hair
	 * @param gender
	 * @param target
	 * @param mutationRate
	 */
	Beauty(Eye eyes, Body body, Hair hair, boolean gender, Beauty target, float mutationRate)
	{
		this.eyes = eyes;
		this.body = body;
		this.hair = hair;
		this.gender = gender;
		this.target = target;
		this.mutationRate = mutationRate;
	}
	
	/**
	 * Makes a new Beauty object, explicitly, no mutation
	 * @param eyes
	 * @param body
	 * @param hair
	 * @param gender
	 * @param target
	 */
	Beauty(Eye eyes, Body body, Hair hair, boolean gender, Beauty target)
	{
		this.eyes = eyes;
		this.body = body;
		this.hair = hair;
		this.gender = gender;
		this.target = null;
		this.mutationRate = 0.0f;
	}
	
	/**
	 * makes a new Beauty object, completely randomly
	 * @param mutationRate
	 * @throws BodyException
	 * @throws HairException
	 */
	Beauty(float mutationRate) throws BodyException, HairException
	{
		if(rand.nextInt() % 2 == 0)
		{
			gender = true;
		}
		else
		{
			gender = false;
		}
		eyes = new Eye();
		body = new Body(null, null, null);
		hair = new Hair(null, null, null);
		target = null;
		this.mutationRate = mutationRate;
	}
	
	/**
	 * makes a new Beauty object, completely randomly with no mutation
	 * @throws HairException 
	 * @throws BodyException 
	 */
	Beauty() throws HairException, BodyException
	{
		if(rand.nextInt() % 2 == 0)
		{
			gender = true;
		}
		else
		{
			gender = false;
		}
		eyes = new Eye();
		body = new Body(null, null, null);
		hair = new Hair(null, null, null);
		target = null;
		this.mutationRate = 0;
	}
	
	/**
	 * makes a new Beauty object by copying the given object
	 * @param b
	 */
	Beauty(Beauty b)
	{
		this.eyes = b.eyes;
		this.body = b.body;
		this.hair = b.hair;
		this.gender = b.gender;
		this.target = b.target;
		this.mutationRate = b.mutationRate;
	}
	
	//100 from eyes, 200 from hair, 600 from body
	public int calcFitness()
	{
		int fitness = 0;
 
		fitness += calcBodyFitness(gender);
		fitness += calcEyeFitness();
		fitness += calcHairFitness();
		
		return fitness;
	}
	
	public void mutate()
	{
		if(rand.nextInt(100) < mutationRate * 100)
		{
			eyes.mutate();
			body.mutate();
			hair.mutate();
		}
	}
	
	public void mutate(Beauty b)
	{
		if(b == null)
		{
			b = target;
		}
		
		if(rand.nextInt(100) < mutationRate * 100)
		{
			eyes.mutate(b.eyes);
			body.mutate(b.body);
			hair.mutate(b.hair);
		}
	}
	
	public Beauty copy()
	{
		Eye e = new Eye(eyes);
		Body b = new Body(body);
		Hair h = new Hair(hair);
		return new Beauty(e, b, h, gender, target);
	}
	
	public int calcEyeFitness()
	{
		int fitness = 0;
		
		if(this.eyes.equals(target.eyes))
		{
			fitness += 100;
		}
		else if(this.eyes.getColor() == Eye.BLUE)
		{
			fitness += 75;
		}
		else if(target.eyes.getColor() == Eye.GREEN && this.eyes.getColor() == Eye.BROWN)
		{
			fitness += 50;
		}
		else if(target.eyes.getColor() == Eye.BROWN && this.eyes.getColor() == Eye.GREEN)
		{
			fitness += 25;
		}
		
		return fitness;
	}
	
	public int calcBodyFitness(boolean gender)
	{
		int fitness = 0;
		double idealRatio = target.body.getWeight() / target.body.getPBF(); //ex. 200lbs, 10% body fat = 2000
		double zeroRatio;
		if(gender)
		{
			zeroRatio = (target.body.getWeight() * .25) / (target.body.getPBF() * 2); //ex. 50 / .2 = 250
		}
		else
		{
			zeroRatio = (target.body.getWeight() * .35) / (target.body.getPBF() * 1.5); //less lenient toward body weight and fat ratio
		}
		double weightToFat = this.body.getWeight() / this.body.getPBF();
		//Fitness is 600 when idealRatio is met while 0 if the zeroRatio is met while it is somewhere in between otherwise
//		double base = Math.pow(600, (1/idealRatio)); //attempt at exponential benefit
		//Linear fitness///
		double intercept = ((500 - 500*(idealRatio) / (idealRatio + zeroRatio)) * (idealRatio + zeroRatio)) / (zeroRatio - idealRatio);
		double rate = (((500 - 500*(idealRatio) / (idealRatio + zeroRatio)) * (idealRatio + zeroRatio)) / (zeroRatio - idealRatio)) / (zeroRatio);
		
		if(this.body.getHeight() == target.body.getHeight())
		{
			fitness += 100;
		}
		else if(this.body.getHeight() < target.body.getHeight() + 5 && this.body.getHeight() > target.body.getHeight() - 5)
		{
			fitness += 80;
		}
		else if(this.body.getHeight() < target.body.getHeight() + 10 && this.body.getHeight() > target.body.getHeight() - 10)
		{
			fitness += 45;
		}
		
		if(weightToFat == idealRatio)
		{
			fitness += 500;
		}
		else 
		{
			if(weightToFat < idealRatio)
			{
				fitness += Math.max(0, intercept + rate * weightToFat);
			}
			else
			{
				double val = (intercept + rate * weightToFat) - 500;
				if(val < 0)
				{
					val = 0;
				}
				fitness += val;
			}
		}
		
		return fitness;
	} //end calcBodyFitness
	
	public int calcHairFitness()
	{
		int fitness = 0;
		if(this.hair.getColor() == target.hair.getColor())
		{
			fitness += 100;
		}
		else if(target.hair.getColor() == Hair.BLOND && this.hair.getColor() == Hair.BROWN)
		{
			fitness += 70;
		}
		else if((target.hair.getColor() == Hair.BROWN && this.hair.getColor() == Hair.BLACK) || target.hair.getColor() == Hair.BLACK && this.hair.getColor() == Hair.BROWN)
		{
			fitness += 60;
		}
		else if(target.hair.getColor() == Hair.RED)
		{
			fitness += 40;
		}
		
		if(this.hair.getCurly() == target.hair.getCurly())
		{
			fitness += 100;
		}
		else if(this.hair.getCurly() < target.hair.getCurly() + 7 || this.hair.getCurly() > target.hair.getCurly() - 7)
		{
			fitness += 80;
		}
		else if(this.hair.getCurly() < target.hair.getCurly() + 12 || this.hair.getCurly() > target.hair.getCurly() - 12)
		{
			fitness += 65;
		}
		return fitness;
	}
	
	public void setTarget(Beauty b)
	{
		target = b;
	}
	
	public boolean equals(Beauty b)
	{
		return this.eyes.equals(b.eyes) && this.body.equals(b.body) && this.hair.equals(b.hair) && this.mutationRate == b.mutationRate && this.gender == b.gender;
	}
	
	public Eye getEyes()
	{
		return eyes;
	}
	
	public Body getBody()
	{
		return body;
	}
	
	public Hair getHair()
	{
		return hair;
	}
	
	public boolean getGender()
	{
		return gender;
	}
	
	public Beauty getTarget()
	{
		return target;
	}
	
	public float getMutationRate()
	{
		return mutationRate;
	}
	
	public void setMutationRate(float mutationRate)
	{
		if(mutationRate < 0 || mutationRate > 1000)
		{
			this.mutationRate = 0;
		}
		else
		{
			this.mutationRate = mutationRate;
		}
	}
}
