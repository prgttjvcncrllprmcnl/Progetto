import java.util.List;
/**
 * 
 * @author Luca Iezzi, Daniel Hrituc
 *
 */
public interface Evolution {
	
	/**
	 * Crea la coppia (p1, p2) e le fa accoppiare (in maniera casuale) con eventuale generazione di figli.
	 * @param p1 il primo genitore.
	 * @param p2 il secondo genitore.
	 * @return True se l'accoppiamento va a buon fine, False altrimenti.
	 */
	boolean createCouples(Person<Individual.Type> p1, Person<Individual.Type> p2);
	
	/**
	 * Elimina l'Individuo dalla popolazione.
	 * @param p la persona da uccidere.
	 * @return True se la sua eliminazione ha avuto successo, False altrimenti.
	 */
	boolean killPerson(Person<Individual.Type> p);
	
	/**
	 * Esegue un controllo sull'individuo alla generazione di ogni figlio:
	 * 		Se il suo TTL arriva a 0 prima che esso sia riuscito a riprodursi, esso viene rimosso dalla popolazione;
	 *		Se il costo della generazione di un figlio lo porta in negativo prima che esso possa completare
	 *		quella di tutti gli altri previsti, esso viene rimosso dalla popolazione.
	 * @param p l'Individuo da controllare.
	 * @return True se l'individuo va rimosso dalla popolazione.
	 */
	boolean checkForDeath(Person<Individual.Type> p);
	
	/**
	 * Setta i seguenti parametri(nello schema ):
	 * @param a premio per il successo nella generazione di figli.
	 * @param b costo del crescere figli.
	 * @param c costo del corteggiamento.
	 */
	void setValues(int a, int b, int c);
	
	/**
	 * Controlla che i valori a, b, c siano settati.
	 * @return True se tali valori sono stati settati, False altrimenti.
	 */
	boolean valuesIsSet();
	
	/**
	 * Controlla che la popolazione attuale sia statisticamente simile in composizione di Individui a quelle con essa confrontate.
	 * Ciò viene fatto calcolando la variazione percentuale di ogni gruppo di individui da una popolazione all'altra, considerando 
	 * le probabilità di generazione dei figli da un passo evolutivo all'altro.
	 * @param other l'/le altra/e popolazione/i da confrontare con quella attuale.
	 * @return True se le popolazioni sono statisticamente simili, False altrimenti.
	 */
	boolean isNextTo(int[] other);
	
	/**
	 * Una popolazione viene considerata stabile se, osservato il suo variare in un range di passi evolutivi (i.e. 50), essa tende a 
	 * crescere in maniera proporzionale sul numero di gruppi di individui che la compongono, dimostrazione di un bilanciamento del
	 * rapporto costo/beneficio per la generazione di nuovi individui.
	 * @param l una lista che contiene gli ultimi N passi evolutivi da analizzare.
	 * @return True se la popolazione può essere considerata stabile, False altrimenti.
	 */
	boolean checkForStabilty(List<int[]> l);
	
	/**
	 * Restituisce il valore percentuale relativo alla presenza di una particolare classe di individui all'interno della sua popolazione.
	 * @param t il tipo dell'individuo in esame.
	 * @return il valore percentuale degli individui del tipo t in questa popolazione.
	 * @throws IllegalArgumentException
	 */
	double getPercentage(Individual.Type t) throws IllegalArgumentException;
	
}
