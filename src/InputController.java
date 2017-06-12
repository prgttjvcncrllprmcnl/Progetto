/**
 * Created by Nikita on 11/06/2017.
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static java.lang.System.out;

public class InputController {
    private int def_a = new Integer(10000);
    private int def_b = new Integer(10000);
    private int def_c = new Integer(10000);
    private int def_d = new Integer(10000);
    private int def_a1 = new Integer(30);
    private int def_b1 = new Integer(40);
    private int def_c1 = new Integer(6);
    boolean bool = true;

    @FXML
    private TextField tf_morigerati;

    @FXML
    private TextField tf_avventurieri;

    @FXML
    private TextField tf_prudenti;

    @FXML
    private TextField tf_spregiudicate;

    @FXML
    private TextField tf_costocorteggiamento;

    @FXML
    private TextField tf_costocrescita;

    @FXML
    private TextField tf_premiogenerazionefigli;

    @FXML
    void onAvviaClick(ActionEvent event) {
        //Prendo le stringhe dentro i campi compilati
        String a = tf_morigerati.getText();
        String b = tf_avventurieri.getText();
        String c = tf_prudenti.getText();
        String d = tf_spregiudicate.getText();
        String a1 = tf_premiogenerazionefigli.getText();
        String b1 = tf_costocrescita.getText();
        String c1 = tf_costocorteggiamento.getText();
        //Trasformo le stringhe in int
        int aa = Integer.parseInt(a);
        int bb = Integer.parseInt(b);
        int cc = Integer.parseInt(c);
        int dd = Integer.parseInt(d);
        int aa1 = Integer.parseInt(a1);
        int bb1 = Integer.parseInt(b1);
        int cc1 = Integer.parseInt(c1);
        //Metto a condizione che se non si rispettano i valori di immissione il programma partirà con valori di default
        if (aa <= 0 || bb <= 0 || cc <= 0 || dd <= 0) {
            aa = def_a;
            bb = def_b;
            cc = def_c;
            dd = def_d;
            aa1 = def_a1;
            bb1 = def_b1;
            cc1 = def_c1;
        }
        Population p = new Population(aa,bb,cc,dd);
        p.setValues(aa1,bb1,cc1);
        while(bool) {
            p.evolve();
            printPop(p, p.currentPopulation());
            if (p.currentPopulation()[0] == 0 || p.currentPopulation()[1] == 0 || p.currentPopulation()[2] == 0 || p.currentPopulation()[3] == 0) {
                break;
            }
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
