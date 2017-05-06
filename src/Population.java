import java.util.List;

/**
 * 
 * @author Luca Iezzi, Daniel Hrituc
 *
 */
public class Population implements Evolution{

	/**
	 * @author Luca Iezzi
	 *Classe locale.
	 */
	class Pair{
		private Individual.Type type;
		private int suggestion;
		/**
		 * Crea un oggetto Pair allo scopo di "tramandare" ad ogni figlio di due genitori, un valore "suggestion"
		 *che lo influenzerà di un parametro s circa la scelta di accoppiamento verso un tipo t.
		 * @param t il tipo dell'individuo.
		 * @param s il valore di suggestion.
		 */
		public Pair(Individual.Type t, int s){
			this.type = t;
			this.suggestion = s;
		}
	}
	
	private Pair suggestion(Person<Individual.Type> p1, Person<Individual.Type> p2){
		//TODO
		return null;
	}
	
	@Override
	public boolean createCouples(Person<Individual.Type> p1, Person<Individual.Type> p2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean killPerson(Person<Individual.Type> p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkForDeath(Person<Individual.Type> p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setValues(int a, int b, int c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean valuesIsSet() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNextTo(int[] other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkForStabilty(List<int[]> l) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getPercentage(Individual.Type t) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
