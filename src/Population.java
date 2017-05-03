import java.util.Random;

/**
 * Created by Daniel on 02/05/2017.
 */
public class Population implements Evolution {

    public Population(int mor, int avv, int pru, int spr) {
        //TODO
    }

    @Override
    public int getPercentage(Type type) throws IllegalArgumentException {
        return 0;
    }

    @Override
    public void setValues(int a, int b, int c) {

    }

    @Override
    public boolean valuesSetted() {
        return false;
    }

    @Override
    public boolean isCloseTo(int[] stato1, int[] stato2) {
        return false;
    }

    @Override
    public boolean checkForDeath(Person gen1, Person gen2) {
        return false;
    }

    @Override
    public boolean createChildren(Person gen1, Person gen2) {
        return false;
    }

    @Override
    public void createCouples() {

    }

    /**
     * La funzione calcola il numero di figli di una coppia. Il calcolo dovrebbe seguire delle probabilità razionali,
     * ovvero la probabilità di avere un figlio è più alta di quella di averne 2 e così via.
     * Per esempio la funzione [ 1/(ln(x+1.34)) - 0.47] * 100 (si prende la parte intera inferiore) offre percentuali
     * interessanti:
     *      x = 1 ---> 70%
     *      x = 2 ---> 35%
     *      x = 3 ---> 21%
     *      x = 4 ---> 12%
     *      x = 5 ---> 7%
     *      x = 6 ---> 3%
     * Per valori più alti la percentuale è 0
     *
     * Funzione inversa: e^(100/y+47)-1.34
     * Scelgo y a caso tra gli interi [0,100] e arrotondo con Math.floor(y) il risultato ottenendo il numero di figli.
     */
    public int childrenNumber() {
        int y = new Random().nextInt(101);
        return (int)Math.floor(Math.exp(100/(y+47))-1.34);
    }

    /**
     * Metodo che stabilisce il sesso e il tipo di un figlio. Per il sesso abbiamo un 50% di probabilità per entrambe
     * le possibilità. Per il tipo, ho applicato questa regola:
     *      - Mamma P e papà M ---> 75% possibilità che sia un P o un M; 25% possibilità che sia S o A
     *      - Mamma S e papà M ---> 50% possibilità che sia un P o un M; 50% possibilità che sia S o A
     *      - Mamma S e papà A ---> 25% possibilità che sia un P o un M; 75% possibilità che sia S o A
     * @param parent1 un genitore.
     * @param parent2 l'altro genitore.
     * @throws IllegalArgumentException se si fornisce come genitori la coppia AP o PA, oppure se si forniscono input
     * che non corrispondono a nessun tipo.
     * @return un array di interi in cui la prima posizione è un valore per il sesso (0 è femmina, 1 maschio), mentre
     * la seconda posizione è un valore per il tipo (0 = P, 1 = M, 2 = S, 3 = A)
     */
    public int[] typeOfChildren(String parent1, String parent2) throws IllegalArgumentException {
        if (parent1.equals(Type.P) && parent2.equals(Type.A) || (parent1.equals(Type.A) && parent2.equals(Type.P))) {
            throw new IllegalArgumentException();
        }
        if ((!parent1.equals(Type.M)) || !parent1.equals(Type.A) || !parent1.equals(Type.S) || !parent1.equals(Type.P)) {
            throw new IllegalArgumentException();
        }
        if ((!parent2.equals(Type.M)) || !parent2.equals(Type.A) || !parent2.equals(Type.S) || !parent2.equals(Type.P)) {
            throw new IllegalArgumentException();
        }
        int[] figlio = new int[2];
        figlio[0] = new Random().nextInt(2);
        if ((parent1.equals(Type.M) && parent2.equals(Type.P)) || parent1.equals(Type.P) && parent2.equals(Type.M)) {
            int n = new Random().nextInt(101);
            if (n <= 75) {
                figlio[1] = (figlio[0] == 0 ? 0 : 1);
            } else {
                figlio[1] = (figlio[0] == 0 ? 2 : 3);
            }
        } else if ((parent1.equals(Type.M) && parent2.equals(Type.S)) || (parent1.equals(Type.S) && parent2.equals(Type.M))) {
            int n = new Random().nextInt(2);
            if (n <= 1) {
                figlio[1] = (figlio[0] == 0 ? 0 : 1);
            } else {
                figlio[1] = (figlio[0] == 0 ? 2 : 3);
            }
        } else {
            int n = new Random().nextInt(101);
            if (n <= 25) {
                figlio[1] = (figlio[0] == 0 ? 0 : 1);
            } else {
                figlio[1] = (figlio[0] == 0 ? 2 : 3);
            }
        }
        return figlio;
    }
}
