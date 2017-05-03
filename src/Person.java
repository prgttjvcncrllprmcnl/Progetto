/**
 * Created by Daniel on 03/05/2017.
 */
public class Person implements Individual {

    @Override
    public allowedType getIndividualType() {
        throw new UnsupportedOperationException("Cancellare questa riga quando si implementa il metodo");
    }

    /**
     * Metodo che verifica se due persone sono uguali. Per essere uguali devono avere lo stesso tipo.
     * @param p la persona con cui si sta confrontando this.
     * @return true se this e p hanno lo stesso tipo; false altrimenti
     */
    public boolean equals(Object p) {
        throw new UnsupportedOperationException("Cancellare questa riga quando si implementa il metodo");
    }

    /**
     * Crea un hashcode per ogni persona. (Vedi vecchio progetto Silvestri per delucidazioni)
     * @return il valore di hash.
     */
    public int hashCode() {
        throw new UnsupportedOperationException("Cancellare questa riga quando si implementa il metodo");
    }
}
