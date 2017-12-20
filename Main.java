package ga;

/**
 * Time taken, 7 days to go from concept to complete product
 * @author bebop
 *
 */

public class Main
{
	public static void main(String[] args)
	{
		Population pop = new Population();
		Person ideal = new Person();
		String idealGender = ideal.beauty.getGender() ? "male" : "female";
		int numGenerations = 10000;
		String idealString = "";
		idealString += "Target\r\n";
		idealString += "" + ideal.name + "\r\nGender: " + idealGender + "\r\n";
		idealString += "Hair[Color: " + ideal.beauty.getHair().getColor() + " MaxLength: " + ideal.beauty.getHair().getMaxLength() + " Curliness: " + ideal.beauty.getHair().getCurly() + "]\r\n";
		idealString += "Body[Height: " + ideal.beauty.getBody().getHeight() + " Percent Body Fat: " + ideal.beauty.getBody().getPBF() + " Weight: " + ideal.beauty.getBody().getWeight() + "]\r\n";
		idealString += "Eyes[Color: " + ideal.beauty.getEyes().getColor() + "]\r\n\r\n";

		String h = "Generation 0\r\n\r\n";
		h += idealString;
		String f = "";
		String path = "Generations/gen0.txt";
		
		for(int i = 0; i < 100; i++)
		{
			try
			{
				Beauty b = new Beauty();
				b.setTarget(ideal.beauty);
				Person p = new Person(b);
				pop.insert(p);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		pop.writePopToFile(path, h, f);
		
		
		int count = 1;
		long startTime = System.currentTimeMillis();
		while(count <= numGenerations)
		{
			try
			{
				System.out.println("*******************************************************");
				System.out.println("\n Generation " + count);
				pop.naturalSelection_NOREMOVAL_SAMEPOPSIZE();
				if(count % 100 == 0)
				{
					h = "Generation " + count + "\r\n\r\n";
					h += idealString;
					f = "";
					path = "Generations/gen"+count+".txt";
					pop.writePopToFile(path, h, f);
				}
				System.out.println("-------------------------------------------------------");
				count++;
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		long endTime = System.currentTimeMillis();
		
		System.out.println("Time taken: " + (endTime - startTime)/1000.0 + " sec");
//		System.out.println("Time taken: " + (startTime - endTime) + " millisec");

	}
}
