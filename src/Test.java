import static java.lang.System.out;
/**
 * Created by Daniel on 14/05/2017.
 */
public class Test {
    public static void main(String[] args) {

        Population p = new Population(10000,10000,10000,10000);
        p.setValues(15,20,3);
        printPop(p,p.currentPopulation());
        
        Graphics.PieChartDemo2 pie = new Graphics.PieChartDemo2("Popolazione");        //creo grafico a torta
        pie.pack();                                                  //per settare la finestra alla grandezza desiderata e farci entrare tutte le componenti
        RefineryUtilities.positionFrameOnScreen(pie,0.1,0.4);        //messo a sinistra sullo schermo
        pie.setVisible(true);                                        //la rendo visibile

        final Graphics.DynamicLineAndTimeSeriesChart demo = new Graphics.DynamicLineAndTimeSeriesChart("Dynamic Line And TimeSeries Chart");
        demo.pack();
        RefineryUtilities.positionFrameOnScreen(demo,0.9,0.4);       //leggi sopra
        demo.setVisible(true);
        
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
            
            Graphics.PieChartDemo2 demo2 = new Graphics.PieChartDemo2("Popolazione");    //crea ogni volta un nuovo grafico a torta
            pie.setContentPane(demo2.getContentPane());                //ne mette il contenuto nel pannello aggiornando il grafo
            pie.setVisible(true);
            
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
