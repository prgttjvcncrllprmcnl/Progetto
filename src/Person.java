import java.util.Objects;

/**
 * Created by Daniel on 03/05/2017.
 */
public class Person implements Individual {
	
	 /**
     * Il tipo dell'individuo.
     */
    private allowedType individualType;
    
    /**
     * Costruttore dei tipi Person.
     * @param s stringa per inferire il tipo dell'individuo.
     * @throws IllegalArgumentException se il tipo inferito non fa parte di quelli accettati da  {@link allowedType}
     */
    public Person(String s) throws IllegalArgumentException{
        try{
            allowedType adjustedS = allowedType.valueOf(s.toUpperCase());
            this.individualType = adjustedS;
        } catch(IllegalArgumentException e){
            System.out.println(e + " Non esiste l'individuo di tipo " + s.toUpperCase() + ".");
        }

    }

    @Override
    public allowedType getIndividualType() {return this.individualType;}

    @Override
    public boolean equals(Object p){
        if(p == this) return true;
        if(!(p instanceof Person)) return false;
        if(Objects.equals(((Person) p).getIndividualType(), this.getIndividualType())) return true;
        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(individualType);
    }
}
