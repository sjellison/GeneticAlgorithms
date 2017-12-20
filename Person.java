package ga;

import java.util.Random;

import ga.Body.BodyException;
import ga.Eye.EyeException;
import ga.Hair.HairException;

public class Person implements Comparable<Person>
{

	Beauty beauty;
	int fitness;
	Person parent1;
	Person parent2;
	
	public String name; //THIS IS NOT VERY USEFULL RIGHT NOW!
	
	//EconomicFitness ef;
	//SocialFitness sf;
	
	Person()
	{
		try
		{
			this.beauty = new Beauty();
			this.beauty.setTarget(new Beauty());
			this.parent1 = null;
			this.parent2 = null;
			this.fitness = calcFitness();
			this.name = "" + fitness;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
	
	Person(Beauty beauty)
	{
		this.beauty = beauty;
		this.parent1 = null;
		this.parent2 = null;
		this.fitness = calcFitness();
		this.name = "" + fitness;
	}
	
	Person(Beauty beauty, Person parent1, Person parent2)
	{
		this(beauty);
		this.parent1 = parent1;
		this.parent2 = parent2;
//		this.name = parent1.name + ":" + parent2.name + ":" + this.fitness;
	}
	
	/**
	 * Makes a new Person by making a deep copy of the given Person
	 * @param p
	 */
	Person(Person p)
	{
		this.beauty = p.beauty.copy();
		this.fitness = p.fitness;
		this.parent1 = p.parent1;
		this.parent2 = p.parent2;
		this.name = p.name + "C";
	}
	
	//Right now, this is the father
	public Person getParent1()
	{
		return parent1;
	}
	
	//Right now, this is the mother
	public Person getParent2()
	{
		return parent2;
	}
	
	public Beauty getBeauty()
	{
		return beauty;
	}
	
	private int getBeautyFitness()
	{
		return beauty.calcFitness();
	}
	
	private int getEconomicFitness()
	{
		return 1000; //TODO place holder
	}
	
	private int getSocialFitness()
	{
		return 1000; //TODO place holder
	}
	
	//this will do more in the future, this just future proofs things
	public int calcFitness()
	{
		if(fitness == 0)
		{
			fitness = getBeautyFitness();
			fitness += getEconomicFitness();
			fitness += getSocialFitness();
		}
		return fitness;
	}
	
	public Person reproduce(Person partner, double mtof) throws EyeException, HairException, BodyException
	{
		boolean newGender = false;
		Random rand = new Random();
		
		//Gender is female by default, and based on the male to female ratio of the current generation, may change to male
		if(mtof > 1)
		{
			if(rand.nextDouble() < Math.max(0.1, 0.5/(mtof - 1)))
			{
				newGender = true;
			}
		}
		else
		{
			if(rand.nextDouble() < Math.min(0.9, 0.5/mtof))
			{
				newGender = true;
			}
		}
		
		Hair childHair = null;
		Body childBody = null;
		Eye childEyes = null;
		Beauty childTarget = null;
		
		if(newGender == this.beauty.getGender())
		{
			if(rand.nextDouble() < .65)
			{
				childHair = this.beauty.getHair().copy();
			}
			else
			{
				childHair = partner.beauty.getHair().copy();
			}
			
			if(rand.nextDouble() < .7)
			{
				childEyes = this.beauty.getEyes().copy();
			}
			else
			{
				childEyes = partner.beauty.getEyes().copy();
			}
			
			childBody = this.beauty.getBody();
			childTarget = this.beauty.getTarget();
		}
		else
		{
			if(rand.nextDouble() < .3)
			{
				childHair = this.beauty.getHair().copy();
			}
			else
			{
				childHair = partner.beauty.getHair().copy();
			}
			
			if(rand.nextDouble() < .15)
			{
				childEyes = this.beauty.getEyes().copy();
			}
			else
			{
				childEyes = partner.beauty.getEyes().copy();
			}
			
			childBody = partner.beauty.getBody();
			childTarget = partner.beauty.getTarget();
		}
		
		Beauty childBeauty = new Beauty(childEyes, childBody, childHair, newGender, childTarget, this.beauty.getMutationRate());
		childBeauty.mutate();
		
		return new Person(childBeauty, this, partner);
	} //end reproduce

	@Override
	public int compareTo(Person o)
	{
		int res = this.calcFitness() - o.calcFitness();
		return res;
	}
}