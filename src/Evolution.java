/**
 * Interfaccia che definisce il concetto di evoluzione. Verrà poi implementata da un'opportuna classe Population che
 * rappresenterà una particolare popolazione che evolve.
 */
public interface Evolution {

    /**
     * Gestisce i tipi ammessi per una popolazione:
     *      M: Uomini morigerati
     *      A: Uomini avventurieri
     *      P: Donne prudenti
     *      S: Donne spregiudicate
     */
    enum Type {
        M, A, P, S
    }

    /**
     * Calcola il valore percentuale di un tipo di individui.
     * @param type un valore di un oggetto di tipo enum che può essere M, A, P, o S.
     * @return il valore percentuale del tipo corrispondente rispetto alla popolazione totale.
     * @throws IllegalArgumentException se viene fornita in input un tipo diverso da M, A, P, o S.
     */
    int getPercentage(Type type) throws IllegalArgumentException;

    /**
     * Imposta i valori che guideranno l'evoluzione della popolazione. Questi valori dovranno essere settati alla
     * creazione dell'oggetto Population e verranno richiesti all'utente nella versione grafica del progetto.
     * @param a premio per il successo nella generazione di un figlio.
     * @param b costo per crescere un figlio.
     * @param c costo del corteggiamento.
     */
    void setValues(int a, int b, int c);

    /**
     * Metodo di controllo per verificare se i valori a,b,c sono stati settati. L'evoluzione non può cominciare senza
     * aver settato quei valori. Nella versione grafica del progetto i valori saranno chiesti in input all'utente.
     * @return true se i valori sono stati settati; false altrimenti.
     */
    boolean valuesSetted();

    /**
     * Due popolazioni sono considerate vicine se tra le due configurazioni la differenza in termini percentali sulla presenza di individui non è rilevante
     * e possono essere considerate "simili".
     * @param other un'altra popolazione da confrontare con this.
     * @return true se le due configurazioni sono vicine, false altrimenti.
     */
    boolean isCloseTo(Population other);

    /**
     * Controlla per i due genitori se a seguito della generazione di figli il loro valore di mana è negativo. In quel
     * caso viene uccisa la persona.
     * @param gen1 uno dei genitori.
     * @param gen2 l'altro genitore.
     * @return true se almeno uno dei due viene ucciso (a scopo di debugging)
     */
    boolean checkForDeath(Person gen1, Person gen2);

    /**
     * Funzione che calcola il numero di figli di una coppia e il loro tipo.
     * @param gen1 uno dei genitori
     * @param gen2 l'altro genitore
     * @return true se generano figli (a scopo di debugging)
     */
    boolean createChildren(Person gen1, Person gen2);

    /**
     * Funzione che effettivamente aziona il passo evolutivo: è il famoso "mercato" o "discoteca" dove avvengono gli
     * incontri. Come funziona:
     *      - vengno scelti in maniera proporzionale al loro numero le persone di tutti i tipi (percentuale che andrà
     *      regolata per far tornare la stabilità; mettiamo 50% all'inizio)
     *      - le persone scelte vengono divise in 4 array e ciclicamente per ognuno dei tipi, prende la prima persona
     *      e trova la sua coppia in base al vantaggio evolutivo che ottiene. Se non trova una coppia compatibile,
     *      muore.
     * La funzione poi aggiorna i valori di percentuali una volta terminato il processo di accoppiamento.
     */
    void createCouples();
}
