package ga;

public abstract class DNA
{

	/**
	 * Returns the fitness of the gene.
	 * @return
	 * 	The fitness of the gene.
	 */
	public abstract int calcFitness();
	
	/**
	 * Modifies the gene slightly.
	 */
	public abstract void mutate();
}
