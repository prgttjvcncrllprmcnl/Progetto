/**
 * Interfaccia che deve essere implementata da una classe Person.
 */
public interface Individual {

    /**
     * I tipi accettati per una persona.
     */
    enum allowedType {
        M, A, P, S
    }

    /**
     * Getter del tipo dell'oggetto Individuo.
     * @return il tipo dell'individuo.
     */
    allowedType getIndividualType();
}
