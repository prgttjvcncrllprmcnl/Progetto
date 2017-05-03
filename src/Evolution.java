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
     * Staibilisce se due stati della popolazione sono vicini. Per avere senso, dovrebbe essere chimata su due stati
     * consecutivi dell'evoluzione, ovvero, se s1 è il primo stato, allora s2 è lo stato che si raggiunge dopo una
     * applicazione delle regole evolutive. In parole povere, usando un Array "a" di int[] (nell'int[] ci sono le
     * percentuali degli individui di tutti e 4 i tipi), s1 e s2 sono in posizioni i e i+1 nell'Array "a".
     * @param stato1 array di interi che rappresenta la popolazione in un preciso istante.
     * @param stato2 array di interi che rappresenta la popolazione nell'istante successivo a quello di stato1.
     * @return true se, chiamati x1,y1,z1,t1 i valori nell'array stato1 e x2,y2,z2,t2 i valori nell'array stato2, la
     * differenza in modulo tra valori con la stessa lettera è minore o uguale a un certo valore k da decidere. Bisogna
     * vedere con quale rapidità cresce la popolazione e decidere quanto è grande k (le specifiche vogliono che k sia
     * tale da rendere la differenza tra gli stati statisticamente irrilevante).
     */
    boolean isCloseTo(int[] stato1, int[] stato2);

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
