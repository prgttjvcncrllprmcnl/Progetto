import java.util.Objects;

import static java.lang.System.out;
/**
 * Created by Daniel on 14/05/2017.
 */
public class Test {
    public static void main(String[] args) {
        //out.println(Objects.equals(Individual.Type.A, Individual.Type.A));

        Population p = new Population(20000,20000,20000,20000);
        p.setValues(15,20,3);
        printPop(p,p.currentPopulation());
        for (int i = 0; i < 100; i++) {
            p.evolve();
            printPop(p,p.currentPopulation());
        }

    }

    private static void printPop(Population p, int[] n) {
        double morp = p.getPercentage(Individual.Type.M);
        double avvp = p.getPercentage(Individual.Type.A);
        double prup = p.getPercentage(Individual.Type.P);
        double sprp = p.getPercentage(Individual.Type.S);
        String s = "[M: "+n[0]+"; A: "+n[1]+"; P: "+n[2]+"; S: "+n[3]+"]";
        String per = "[M: "+morp+"; A: "+avvp+"; P: "+prup+"; S: "+sprp+"]";
        out.print(s+ "  ---  ");
        out.println("Percentuali: "+per);
    }
}
