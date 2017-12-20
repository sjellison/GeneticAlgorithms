package ga;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.*;

import ga.Body.BodyException;
import ga.Eye.EyeException;
import ga.Hair.HairException;
import ga.Population.PopulationException;

public class test
{

	Hair h, g;
	Body b, c;
	Eye e, f;
	Beauty be, au;
	Person p, o;
	AVLTree<Integer> at;
	Population pop;
	Random rand = new Random();
	
	@Before
	public void init() throws HairException, BodyException, EyeException
	{
		h = new Hair(0, 0, 0);
		g = new Hair(null, null, null);
		b = new Body(22, 4, .02f);
		c = new Body(null, null, null);
		e = new Eye(0);
		f = new Eye(1);
		au = new Beauty();
		be = new Beauty(e, b, h, true, au, .05f);
		p = new Person(be);
		o = new Person();
		o.beauty.setTarget(new Beauty());
		at = new AVLTree<Integer>(0);
		pop = new Population();
	}
	
	@Test
	public void testHairColor1()
	{
		assertEquals("Hair Color Not Equal", 0, h.getColor());
	}
	
	@Test
	public void testHairColor2()
	{
		assertTrue("Hair Color Not Properly Randomized", g.getColor() == Hair.BLOND || g.getColor() == Hair.BLACK || g.getColor() == Hair.BROWN || g.getColor() == Hair.RED);
	}
	
	@Test
	public void testHairLength1()
	{
		assertTrue("Hair Length Below 0", h.getMaxLength() >= Hair.MINLENGTH);
	}
	
	@Test
	public void testHairLength2()
	{
		assertEquals("Hair Length Not 10", 10, g.getMaxLength());
	}
	
	@Test
	public void testHairCurly1()
	{
		assertEquals("Hair Curly Not Equal", 0, h.getCurly());
	}
	
	@Test
	public void testHairCurly2()
	{
		assertTrue("Hair Curly Not Properly Randomized", g.getCurly() >= Hair.MINCURLY && g.getCurly() <= Hair.MAXCURLY);
	}
	
	@Test
	public void testHairEquals()
	{
		boolean explicit = (h.getColor() == g.getColor()) && (h.getCurly() == g.getCurly()) && (h.getMaxLength() == g.getMaxLength());
		assertTrue("Equals resolved to incorrect value", h.equals(g) == explicit);
	}
	
	@Test
	public void testHairCopy()
	{
		Hair n = h.copy();
		boolean explicit = (h.getColor() == n.getColor()) && (h.getCurly() == n.getCurly()) && (h.getMaxLength() == n.getMaxLength());
		assertTrue("Copy Failed", explicit);
	}
	
	@Test
	public void testHairMutate1() throws HairException
	{
		Hair n = new Hair(h.getColor(), h.getMaxLength(), h.getCurly());
		n.mutate();
		boolean explicit = (h.getColor() == n.getColor()) && (h.getCurly() == n.getCurly()) && (h.getMaxLength() == n.getMaxLength());
		assertFalse("Mutate failed", explicit);
	}
	
	@Test
	public void testHairMutate2() throws HairException
	{
		Hair n = new Hair(h.getColor(), h.getMaxLength(), h.getCurly());
		n.mutate(g);
		boolean explicit = (h.getColor() == n.getColor()) && (h.getCurly() == n.getCurly()) && (h.getMaxLength() == n.getMaxLength());
		assertFalse("Mutate failed", explicit);
	}
	
	//************************************************************************************************//
	
	@Test
	public void testBodyHeight1()
	{
		assertEquals("Body Height Not Equal", 22, b.getHeight());
	}
	
	@Test
	public void testBodyHeight2()
	{
		assertTrue("Body Height Not Properly Randomized", c.getHeight() >= Body.MINHEIGHT && c.getHeight() <= Body.MAXHEIGHT);
	}
	
	@Test
	public void testBodyWeight1()
	{
		assertEquals("Body Weight Not Equal", 4, b.getWeight());
	}
	
	@Test
	public void testBodyWeight2()
	{
		assertTrue("Body Weight Not Properly Randomized", c.getWeight() >= Body.MINWEIGHT && c.getWeight() <= Body.MAXWEIGHT);
	}
	
	@Test
	public void testBodyPBF1()
	{
		assertEquals("Body PBF Not Equal", 0.02f, b.getPBF(), 0.001);
	}
	
	@Test
	public void testBodyPBF2()
	{
		assertTrue("Body PBF Not Properly Randomized " + c.getPBF(), c.getPBF() >= Body.MINPBF && c.getPBF() <= Body.MAXPBF);
	}
	
	@Test
	public void testBodyEquals()
	{
		boolean explicit = ((b.getHeight() == c.getHeight()) && (b.getPBF() == c.getPBF()) && (b.getWeight() == c.getWeight()));
		assertTrue("Body Equals Not Correct", b.equals(c) == explicit);
	}
	
	@Test
	public void testBodyCopy() throws BodyException
	{
		Body n = b.copy();
		boolean explicit = ((n.getHeight() == b.getHeight()) && (n.getPBF() == b.getPBF()) && (n.getWeight() == b.getWeight()));
		assertTrue("Body Copy Failed", explicit);
	}
	
	@Test
	public void testBodyMutate1() throws BodyException
	{
		Body n = new Body(b.getHeight(), b.getWeight(), b.getPBF());
		n.mutate();
		boolean explicit = ((n.getHeight() == b.getHeight()) && (n.getPBF() == b.getHeight()) && (n.getWeight() == b.getWeight()));
		assertFalse("Body Mutate Failed", explicit);
	}
	
	@Test
	public void testBodyMutate2() throws BodyException
	{
		Body n = new Body(b.getHeight(), b.getWeight(), b.getPBF());
		n.mutate(c);
		boolean explicit = ((n.getHeight() == b.getHeight()) && (n.getPBF() == b.getHeight()) && (n.getWeight() == b.getWeight()));
		assertFalse("Body Mutate Failed", explicit);
	}
	
	//****************************************************************************************************//
	
	@Test
	public void testEyeColor1()
	{
		assertEquals("Eye Color Not Equal", 0, e.getColor());
	}
	
	@Test
	public void testEyeColor2()
	{
		assertTrue("Eye Color Not Properly Randomized", e.getColor() >= 0 && e.getColor() <= 2);
	}
	
	@Test
	public void testEyeEquals()
	{
		boolean explicit = (e.getColor() == f.getColor());
		assertTrue("Eye Equals Failed", e.equals(f) == explicit);
	}
	
	@Test
	public void testEyeCopy() throws EyeException
	{
		Eye n = e.copy();
		boolean explicit = (n.getColor() == e.getColor());
		assertTrue("Eye Copy Failed", explicit);
	}
	
	@Test
	public void testEyeMutate1() throws EyeException
	{
		Eye n = new Eye(e.getColor());
		n.mutate();
		assertFalse("Eye Mutate Failed", e.getColor() == n.getColor());
	}
	
	@Test
	public void testEyeMutate2() throws EyeException
	{
		Eye n = new Eye(e.getColor());
		n.mutate(f);
		boolean explicit = e.getColor() == n.getColor();
		assertTrue("Eye Mutate Failed", n.equals(e) == explicit);
	}
	
	//************************************************************************************//
	
	@Test
	public void testBeautyBodyFit()
	{
		int f = be.calcBodyFitness(true);
		assertTrue("Beauty CalcBodyFitness Failed", f >= 0 && f <= 700);
	}
	
	@Test
	public void testBeautyHairFit()
	{
		int f = be.calcHairFitness();
		assertTrue("Beauty CalcHairFitness Failed", f >= 0 && f <= 200);
	}
	
	@Test
	public void testBeautyEyeFit()
	{
		int f = be.calcEyeFitness();
		assertTrue("Beauty CalcEyeFitness Failed", f >= 0 && f <= 100);
	}
	
	@Test
	public void testBeautyMutate()
	{
		Beauty n = be.copy();
		n.mutate();
		assertFalse("Beauty Mutate Failed", n.equals(be));
	}
	
	//**********************************************************************//
	
	@Test
	public void testPersonGetBeauty()
	{
		assertTrue("Person GetBeauty Failed", p.getBeauty().equals(be));
	}
	
	@Test
	public void testPersonCalcFitness()
	{
		assertTrue("Person GetBeautyFitness Failed", p.calcFitness() == be.calcFitness() + 2000);
	}
	
	//*****************************************************************************************//
	
	@Test
	public void testAVLTreeInsert()
	{
		int numChild = 250;
		int treeHeight = at.getTreeHeight();
		int[] arr = new int[numChild];
		for(int i = 0; i < numChild; i++)
		{
			arr[i] = rand.nextInt(131);
		}
		for(int k = 0; k < arr.length; k++)
		{
			at.insert(arr[k]);
		}
		
		assertTrue("AVLTree Insert Failed", treeHeight < at.getTreeHeight() && at.getSize() == arr.length + 1);
	}
	
	@Test
	public void testAVLGetinOrder()
	{
		Integer[] arr = new Integer[8];
		int[] control = {0,0,1,2,3,4,5,6};
		boolean bool = true;
		
		for(int i = 0; i < 7; i++)
		{
			at.insert(i);
		}
		ArrayList<Integer> al = new ArrayList<Integer>();
		at.getInOrder(al);
		al.toArray(arr);
		
		for(int i = 0; i < control.length; i++)
		{
			if(control[i] != arr[i])
			{
				bool = false;
				break;
			}
		}
		assertTrue("AVLTree GetInOrder Failed", bool);
	}
	
	//**************************************************************************************************//
	
	@Test
	public void testPopulationInsert() throws HairException, BodyException
	{
		int initPopSize = pop.getSize();
		for(int i = 0; i < 17; i++)
		{
			Person per = new Person();
			per.beauty.setTarget(new Beauty());
			pop.insert(per);
		}
		
		assertTrue("Population Failed To Insert", initPopSize < pop.getSize());
	}
	
	@Test
	public void testPopulationNaturalSelection1() throws HairException, BodyException
	{
		for(int i = 0; i < 17; i++)
		{
			Person per = new Person();
			per.beauty.setTarget(new Beauty());
			pop.insert(per);
		}

		try
		{
			pop.naturalSelection_NOREMOVAL_SAMEPOPSIZE();
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e);
		}
		assertTrue("Population Natural Selection Failed", pop.getSize() == 17);
	}
}
