import java.util.Objects;

/**
 * 
 * @author Luca Iezzi, Daniel Hrituc
 *
 * @param <T> il tipo di una persona
 */

public class Person<T extends Enum<T>> extends Individual<T> {
    public boolean isParent = false;
	private Individual.Type type;
	private int TTL;
	private int suggestion = 0;
    private Individual.Type suggestedType;
    public int sex;

    /**
     * Costruttore di una persona. Inizializza il suo tipo e il TTL (Time to Live) che vine usato per stabilire il
     * numero di turni che la persna può rimanere nel "mercato".
     * @param t il tipo della persona. Può essere M, A, P o S.
     */
	public Person(Individual.Type t) {
		this.type = t;
		this.TTL = 2;
		setSex();
	}

    /**
     * Setta il sesso della persona basandosi sul suo tipo. 0 = femmina, 1 = maschio
     */
	private void setSex() {
	    if (type.equals(Type.P) || type.equals(Type.S)) sex = 0;
	    else sex = 1;
    }

    /**
     * Setter per il valore del consiglio dei genitori.
     * @param n il valore.
     */
    public void setSuggestion(int n) {
	    suggestion = n;
    }

    /**
     * Setter per il tipo suggerito dai genitori.
     * @param t il tipo suggerito.
     */
    public void setSuggestedType(Individual.Type t) {
	    suggestedType = t;
    }

    /**
     * Getter del tipo.
     * @return il tipo della persona.
     */
	public Individual.Type getType() {
	    return type;
    }

    /**
     * Getter del TTL.
     * @return il TTL.
     */
    public int getTTL() {
	    return TTL;
    }

    /**
     * Riduce di 1 il TTL.
     */
    public void decreaseTTL() {
        TTL--;
    }

    /**
     * Getter della suggestion.
     * @return la sugestion.
     */
    public int getSuggestion() {
	    return suggestion;
    }

    /**
     * Getter per il suggestedType.
     * @return il tipo suggerito dai genitori.
     */
    public Individual.Type getSuggestedType() {
        return suggestedType;
    }
	
	@Override
    public boolean equals(Object i) {
        if(i == this) return true;
        if(!(i instanceof Person)) return false;
        return (Objects.equals(((Person) i).type, this.type));
    }

    @Override
    public int hashCode(){
        return Objects.hash(type, TTL, suggestion);
    }
	
}
