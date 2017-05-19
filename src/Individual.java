/**
 * Classe astratta che generalizza il tipo Person.
 * @author Luca Iezzi, Daniel Hrituc
 *
 */
public abstract class Individual<T extends Enum<T>> {

	/**
	 * Enum dei tipi ammessi.
	 * M = Morigerato - A = Avventuriero
	 * P = Prudente - S = Spregiudicata
	 */
	public enum Type{
		M, A, P, S

	}

    /**
     * Calcola il payoff ottenuto dalla generazione di un figlio.
     * @param first un genitore.
     * @param second l'altro genitore.
     * @param a il guadagno per la generazione del figlio.
     * @param b il costo per la crescita del figlio.
     * @param c il costo del corteggiamento.
     * @return una tupla contenente i payoff di first e second.
     */
	public static int[] payoff(Person<Individual.Type> first, Person<Individual.Type> second, int a, int b, int c){
		int[] payoff = new int[2];
	    if ((first.getType().equals(Type.M) && second.getType().equals(Type.P)) || (first.getType().equals(Type.P) && second.getType().equals(Type.M))) {
		    payoff[0] = payoff[1] = a-b/2-c;
        } else if ((first.getType().equals(Type.M) && second.getType().equals(Type.S)) || (first.getType().equals(Type.S) && second.getType().equals(Type.M))) {
            payoff[0] = payoff[1] = a-b/2;
        } else if ((first.getType().equals(Type.A) && second.getType().equals(Type.S)) || (first.getType().equals(Type.S) && second.getType().equals(Type.A))) {
	        payoff[0] = a-b;
	        payoff[1] = a;
        } else {
	        payoff[0] = payoff[1] = 0;
        }
        return payoff;
	}
}