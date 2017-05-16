import java.util.List;
/**
 * 
 * @author Luca Iezzi, Daniel Hrituc
 *
 */
public interface Evolution {

    /**
     * Il cuore dell'evoluzione. Definisce le regole del "mercato" dove si incontrano le persone. Il metodo sceglierà a
     * caso le persone che devono andare al mercato (30% di ogni tipo), gestirà le coppie chiamando il metodo
     * createFamily e gestisce il problema dell'eliminazione:
     * l'idea per eliminare una persona è accedere alla corrispondente lista dove si trovava tramite l'indice che la
     * person aveva nella lista. tuttavia, facendo un'eliminazione, tutti gli altri successivi indici shifteranno di 1.
     * bisogna dunque gestire un controllo che aggiorni gli indici decrementandoli di 1 se sono superiori a quello della
     * persona che deve essere rimossa dalla lista.
     */
    void evolve();

	/**
	 * Metodo che crea una famiglia. Chiama i metodi per il numero di figli e loro tipo.
	 * @param p1 un genitore.
	 * @param p2 l'altro genitore.
     * @param i1 l'indice di p1 nella lista delle persone
     * @param i2 l'indice di p2 nella lista delle persone
	 * @return true se la famiglia si forma, false altrimenti (a scopo di debugging).
	 */
	boolean createFamily(Person<Individual.Type> p1, Person<Individual.Type> p2, int i1, int i2);

	/**
	 * Metodo che uccide una persona.
	 * @param p la persona da uccidere.
	 * @param i l'indice della persona nella lista di persone
	 * @return true se viene uccisa, false altrimenti (a scopo di debugging).
	 */
	boolean killPerson(Person<Individual.Type> p, int i);

    /**
     * Setta i valori di a, b, c.
     * @param a il guadagno per la generazione del figlio.
     * @param b il costo per la crescita del figlio.
     * @param c il costo del corteggiamento.
     */
	void setValues(int a, int b, int c);

    /**
     * Metodo che controlla se i valori a, b, c sono stati settati.
     * @return true se sono stati settati, false altrimenti.
     */
	boolean valuesIsSet();

    /**
     * Controlla che due stati di una popolazione siano vicini. Per essere vicini, le percentuali dei singoli tipi non
     * devono variare più di un certo valore k che dovrà essere deciso.
     * @param first il primo stato.
     * @param other l'altro stato.
     * @return true se sono vicini, false altrimenti.
     */
	boolean isNextTo(int[] first, int[] other);

    /**
     * Controlla che una popolazione sia stabile. Per fare ciò chiama il metodo isNextTo e controlla 50 stati
     * consecutivi (numero ancora da decidere).
     * @param l un array di stati.
     * @return true se è stabile, false altrimenti.
     */
	boolean checkForStability(List<int[]> l);

    /**
     * Calcola la percentuale di un tipo nella popolazione.
     * @param t il tipo di cui vogliamo calcoalre la percentuale.
     * @return la percentuale calcolata.
     * @throws IllegalArgumentException se il tipo non è un tipo ammesso.
     */
	double getPercentage(Individual.Type t) throws IllegalArgumentException;
}
