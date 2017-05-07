import java.util.Objects;

/**
 * 
 * @author Luca Iezzi, Daniel Hrituc
 *
 * @param <T>
 */

public class Person<T extends Enum<T>> extends Individual<T>{
	
	/**
	 * Il tipo dell'individuo.
	 */
	private Individual.Type type;
	
	/**
	 * TTL : TimeToLive, inizializzato a 2, verrà decrementato ogniqualvolta un Individuo in grado di 
	 * 		accoppiarsi non lo farà; quando TTL = 0, l'Individuo verrà ucciso.
	 * suggestion: parametro passato da genitore a figlio, che lo influenzerà leggermente sulla scelta 
	 * 			del futuro partner, in base alla propria esperienza positiva.
	 */
	private  int TTL, suggestion;
	
	/**
	 * Costruttore dei primi individui della popolazione.
	 * @param t in tipo inferito dell'Individuo.
	 */
	public Person(Individual.Type t){
		this.type = t;
		this.TTL = 2;
	}
	
	/**
	 * Costruttore dei successivi individui della popolazione.
	 * @param t il tipo inferito dell'Individuo.
	 * @param s il valore di suggestion inferito dai genitori di questo Individuo.
	 */
	public Person(Individual.Type t, int s){
		this(t);
		this.suggestion = s;
	}
	
	@Override
    public boolean equals(Object i){
        if(i == this) return true;
        if(!(i instanceof Person)) return false;
        if(Objects.equals(((Person) i).type, this.type)) return true;
        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(type, TTL, suggestion);
    }
	
}
