/**
 * 
 * @author Luca Iezzi, Daniel Hrituc
 *
 */
public abstract class Individual<T extends Enum<T>> {
	
	/**
	 * @author Luca Iezzi
	 * Un Enum che fornisce tutti e quattro i tipi di Individuo accettati nella creazione di quest'ultimo:
	 * Uomini:
	 * 		-Morigerati;
	 * 		-Avventurieri;
	 * Donne:
	 * 		-Prudenti;
	 * 		-Spregiudicate;
	 */
	public enum Type{
		M, A, P, S
	};
	
	/**
	 * Calcola il payoff della generazione di un figlio a seconda del tipo degli individui genitori di
	 * questo (seguendo lo schema qui sotto).
	 * @param first il primo genitore.
	 * @param second il secondo genitore.
	 * @return un array di interi di lunghezza 2, dove alla posizione 0 c'è il payoff per first e alla posizione 1
	 * il payoff per second.
	 * 
	 * 			            M 						        A
	 * 		P    (a - b/2 - c, a - b/2 - c) 		-
	 * 	   S        (a - b/2, a - b/2)			(a - b, a)
	 */
	public int[] payoff(Person<T> first, Person<T> second){
		throw new UnsupportedOperationException();
	}
	
}