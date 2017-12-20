package ga;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import ga.Body.BodyException;
import ga.Eye.EyeException;
import ga.Hair.HairException;

public class Population {

	public final static int MAXFITNESS = 3000;
	
	private ArrayList<Person> pop;
	private Random rand = new Random();
	private ArrayList<Person> males = new ArrayList<Person>();
	private ArrayList<Person> females = new ArrayList<Person>();
	
	Population()
	{
		pop = new ArrayList<Person>();
	}
	
	public void insert(Person p)
	{
		pop.add(p);
	}
	
	public int getSize()
	{
		return pop.size();
	}
	
	//THE STABLE MARRIAGE PROBLEM IS WORTH LOOKING INTO FOR THIS
	public void naturalSelection_NOREMOVAL_SAMEPOPSIZE() throws EyeException, HairException, BodyException, PopulationException
	{
		calcFitness1();
		long sumMales = 0;
		long sumFemales = 0;
		float mtof = males.size() / females.size();
		ArrayList<Person> newPop = new ArrayList<Person>();
		Person[] male = new Person[males.size()];
		Person[] female = new Person[females.size()];
		males.toArray(male);
		females.toArray(female);
		Long[] maleProb = new Long[males.size()];
		Long[] femaleProb = new Long[females.size()];
		
		if(males.size() == 0 || females.size() == 0)
		{
			throw new PopulationException("There wasn't enough males or females. Males: " + males.size() + " Females: " + females.size());
		}
		
		for(int i = 0; i < Math.max(males.size(), females.size()); i++)
		{
			if(i < males.size())
			{
				sumMales += i+1;
				maleProb[i] = sumMales;
			}
			if(i < females.size())
			{
				sumFemales += i+1;
				femaleProb[i] = sumFemales;
			}
		}

		for(int i = 0; i < pop.size(); i++)
		{
			long probMale = Math.abs(rand.nextLong()) % sumMales;
			long probFemale = Math.abs(rand.nextLong()) % sumFemales;
		
			int nm = getNearest(maleProb, probMale);
			int nf = getNearest(femaleProb, probFemale);
			
			Person m = male[getNearest(maleProb, probMale)];
			Person f = female[getNearest(femaleProb, probFemale)];
			
			newPop.add(m.reproduce(f, mtof));
		}
		pop = newPop;
	}
	
	public void printPopulation()
	{
		calcFitness1();
		int num = 1;
		for(Person p : pop)
		{
			String name = p.name; //TODO
//			String name = "Person " + num;
			int hairColor = p.beauty.getHair().getColor();
			int hairLength = p.beauty.getHair().getMaxLength();
			int hairCurliness = p.beauty.getHair().getCurly();
			int bodyHeight = p.beauty.getBody().getHeight();
			float bodyPBF = p.beauty.getBody().getPBF();
			int bodyWeight = p.beauty.getBody().getWeight();
			int eyeColor = p.beauty.getEyes().getColor();
			int fitness = p.fitness;
			String gender = p.beauty.getGender() ? "male" : "female";
			
			System.out.println();
			//TODO May need to include carriage returns (/r) for windows OS, we'll see
			System.out.printf("Name: %s\nGender: %s\nHair[Color: %3d Max Length: %3d Curliness: %3d]\nBody[Height: %3d Percent Body Fat: %4f Weight: %4d]\nEyes[Color: %3d]\nFitness Score: %4d, adjusted: %4d\n",
					name, gender, hairColor, hairLength, hairCurliness, bodyHeight, bodyPBF, bodyWeight, eyeColor, fitness, (fitness - 2000));

			//Reference for what the printf is outputting
//					name\n
//					gender\n
//					Hair[Color: %3d Max Length: %3d Curliness: %3d]\n
//					Body[Height: %3d Percent Body Fat: %4f Weight: %4d]\n
//					Eyes[Color: %3d]\n
//					Fitness Score: %4d\n
			
			num++;
		}
	}
	
	public void writePopToFile(String path, String header, String footer)
	{
		FileWriter fw = null;
		BufferedWriter bw = null;
		try
		{
			fw = new FileWriter(path);
			bw = new BufferedWriter(fw);
			bw.write(header);
			
			int num = 1;
			for(Person p : pop)
			{
				String name = p.name;
				String p1 = "";
				String p2 = "";
				if(p.parent1 != null)
				{
					p1 = p.parent1.name;
				}
				else
				{
					p1 = "NONE";
				}
				if(p.parent2 != null)
				{
					p2 = p.parent2.name;
				}
				else
				{
					p2 = "NONE";
				}
				int hairColor = p.beauty.getHair().getColor();
				int hairLength = p.beauty.getHair().getMaxLength();
				int hairCurliness = p.beauty.getHair().getCurly();
				int bodyHeight = p.beauty.getBody().getHeight();
				float bodyPBF = p.beauty.getBody().getPBF();
				int bodyWeight = p.beauty.getBody().getWeight();
				int eyeColor = p.beauty.getEyes().getColor();
				int fitness = p.fitness;
				String gender = p.beauty.getGender() ? "male" : "female";
				
				bw.write("\n\r");
				String s = String.format("Name: %s\r\n"
						+ "Parent 1: %s, Parent2: %s\r\n"
						+ "Gender: %s\r\n"
						+ "Hair[Color: %3d Max Length: %3d Curliness: %3d]\r\n"
						+ "Body[Height: %3d Percent Body Fat: %4f Weight: %4d]\r\n"
						+ "Eyes[Color: %3d]\r\n"
						+ "Fitness Score: %4d\r\n\r\n",
						name, p1, p2, gender, hairColor, hairLength, hairCurliness, bodyHeight, bodyPBF, bodyWeight, eyeColor, fitness);
				bw.write(s);
				num++;
			}
			bw.write(footer);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(bw != null)
				{
					bw.close();
				}
				if(fw != null)
				{
					fw.close();
				}
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private <T extends Comparable<T>> int getNearest(T[] arr, T target)
	{
		return getNearestRec(arr, target, 0, arr.length - 1);
	}
	
	private <T extends Comparable<T>> int getNearestRec(T[] arr, T target, int start, int end)
	{
		int mid = (start + end) / 2;
		
		if(mid < 0 || mid >= arr.length)
		{
			System.out.println("WTH! " + start + "|" + end + "|" + mid);
		}
		
		if(mid == start || mid == end)
		{
			return mid;
		}
		
		if(arr[mid].compareTo(target) > 0)
		{
			return getNearestRec(arr, target, start, mid);
		}
		else
		{
			return getNearestRec(arr, target, mid, end);
		}
	}
	
	/*
	 * Calculates the individual fitness' of the population and stores the people into arrays for male and females
	 * in order from lowest to highest fitness
	 */
	private void calcFitness1()
	{
		AVLTree<Person> mFit = new AVLTree<Person>();
		AVLTree<Person> fFit = new AVLTree<Person>();
		
		for(Person p : pop)
		{
			p.calcFitness();
			if(p.beauty.getGender())
			{
				mFit.insert(p);
			}
			else
			{
				fFit.insert(p);
			}
		}
		
		mFit.getInOrder(males);
		fFit.getInOrder(females);
		
	} //end calcFitness
	
	class PopulationException extends Exception
	{
		public PopulationException(String s)
		{
			super(s);
		}
	}
}
