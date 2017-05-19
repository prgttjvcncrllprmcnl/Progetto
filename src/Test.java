import static java.lang.System.out;
/**
 * Created by Daniel on 14/05/2017.
 */
public class Test {
    public static void main(String[] args) {

        Population p = new Population(10000,10000,10000,10000);
        p.setValues(15,20,3);
        printPop(p,p.currentPopulation());
        /*
        for (int i = 0; i < 200; i++) {
            p.evolve();
            printPop(p,p.currentPopulation());
            if (i%25 == 0) out.println("---------TTL---------");
        }
        */
        while (true) {
            p.evolve();
            printPop(p,p.currentPopulation());
            if (p.currentPopulation()[0] == 0 || p.currentPopulation()[1] == 0 || p.currentPopulation()[2] == 0 || p.currentPopulation()[3] == 0) break;
        }

    }

    private static void printPop(Population p, int[] n) {
        double morp = p.getPercentage(Individual.Type.M);
        double avvp = p.getPercentage(Individual.Type.A);
        double prup = p.getPercentage(Individual.Type.P);
        double sprp = p.getPercentage(Individual.Type.S);
        String s = "[M: "+n[0]+"; A: "+n[1]+"; P: "+n[2]+"; S: "+n[3]+"]";
        String per = "[M: "+morp+"; A: "+avvp+"; P: "+prup+"; S: "+sprp+"]";
        String perPrudDonne = "  ---  %P: ";
        String perMorUomini = "; %M: ";
        out.print(s+ "  ---  ");
        out.print("Percentuali: "+per);
        out.println(perPrudDonne+((double)n[2])/((double)n[2]+(double)n[3])+perMorUomini+((double)n[0])/((double)n[0]+(double)n[1]));
    }
}
