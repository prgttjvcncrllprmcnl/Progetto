import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.Rotation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.System.out;
/**
 * Created by Daniel on 14/05/2017.
 */

public class test {

    static Population p = new Population(10000,10000,10000,10000);

    public static void main(String[] args) {
        p.setValues(15,20,3);
        printPop(p,p.currentPopulation());

        PieChartDemo2 pie = new PieChartDemo2("Popolazione");        //creo grafico a torta
        pie.pack();                                                  //per settare la finestra alla grandezza desiderata e farci entrare tutte le componenti
        RefineryUtilities.positionFrameOnScreen(pie,0.1,0.4);        //messo a sinistra sullo schermo
        pie.setVisible(true);                                        //la rendo visibile

        final DynamicLineAndTimeSeriesChart demo = new DynamicLineAndTimeSeriesChart("Dynamic Line And TimeSeries Chart");
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

            PieChartDemo2 demo2 = new PieChartDemo2("Popolazione");    //crea ogni volta un nuovo grafico a torta
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

public static class PieChartDemo2 extends ApplicationFrame {            //PRIMO GRAFICO (Piechart)

        /**
         * Creates a new demo.
         *
         * @param title  the frame title.
         */
        public PieChartDemo2(final String title) {

            super(title);
            // crea un dataset...
            final PieDataset dataset = createSampleDataset();
            // crea un grafico...
            final JFreeChart chart = createChart(dataset);
            //crea pannello, imposta le dimensioni e aggiunge il grafico al pannello...
            final ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(700, 470));
            setContentPane(chartPanel);

        }


        /**
         * Crea un dataset per il grafico (con i dati iniziali).
         *
         * @return un dataset.
         */
        public PieDataset createSampleDataset() {
            DefaultPieDataset result = new DefaultPieDataset();
            result.setValue("Morigerati", p.currentPopulation()[0]);
            result.setValue("Avventurieri", p.currentPopulation()[1]);
            result.setValue("Prudenti", p.currentPopulation()[2]);
            result.setValue("Spregiudicate", p.currentPopulation()[3]);
            return result;
        }

        /**
         * Crea un grafico.
         *
         * @param dataset il dataset.
         *
         * @return Un grafico.
         */
        private JFreeChart createChart(final PieDataset dataset) {

            final JFreeChart chart = ChartFactory.createPieChart3D(
                    "Battaglia Dei Sessi",  // Titolo
                    dataset,                // dati (dataset)
                    true,                   // includi legenda
                    true,                   // tooltips
                    false                   // urls
            );

            final PiePlot3D plot = (PiePlot3D) chart.getPlot();      //disegna il grafico a torta 3D usando i dati del dataset
            plot.setStartAngle(290);                                 //set l'angolo di partenza
            plot.setDirection(Rotation.CLOCKWISE);                   //direzione in cui vengono disegnati i le sezioni di torta
            plot.setForegroundAlpha(0.5f);                           //set trasparenza
            plot.setNoDataMessage("No data to display");             //messaggio da lanciare in assenza di dati
            return chart;

        }

    }

    public static class DynamicLineAndTimeSeriesChart extends ApplicationFrame implements ActionListener {      //SECONDO GRAFICO (line timechart)

        /** Time series data. */
        private TimeSeries series;
        private TimeSeries series1;                         //crea 4 timeseries
        private TimeSeries series2;                         //TimeSeries class: Represents a sequence of zero or more data items in the form (period, value) where 'period' is some instance of a subclass of RegularTimePeriod.
        private TimeSeries series3;                         //The time series will ensure that (a) all data items have the same type of period (for example, Day) and (b) that each period appears at most one time in the series

        /** Timer che aggiorna il grafico ogni quarto di secondo */
        private Timer timer = new Timer(250, this);

        /**
         * Costruisce un nuovo grafico dinamico.
         *
         * @param title  il titolo.
         */
        public DynamicLineAndTimeSeriesChart(final String title) {

            super(title);
            series = new TimeSeries("M");
            series1 = new TimeSeries("A");
            series2 = new TimeSeries("P");
            series3 = new TimeSeries("S");

            //Creo grafico con linee
            final JFreeChart linechart = createChart();

            //Delay prima che vengono stampati i dati sullo schema
            timer.setInitialDelay(1000);

            //Colore di background del grafico
            linechart.setBackgroundPaint(Color.LIGHT_GRAY);

            //Crea un JPanel per mostrare grafico sullo schermo
            final JPanel content = new JPanel(new BorderLayout());

            //Crea Chartpanel per l'area del grafico
            final ChartPanel chartPanel = new ChartPanel(linechart);

            //Aggiunge Chartpanel al panel principale
            content.add(chartPanel);

            //Setta la grandezza della finestra intera (JPanel)
            chartPanel.setPreferredSize(new java.awt.Dimension(900, 500));

            //Mette tutto il contenuto in un Frame
            setContentPane(content);

            timer.start();

        }

        /**
         * Creates a sample chart.
         *
         * @return A sample chart.
         */
        private JFreeChart createChart() {
            final XYDataset dataset = this.createDataset(series);
            final JFreeChart result = ChartFactory.createTimeSeriesChart(
                    "Evolution",            //Titolo
                    "Time",                 //Titolo asse x
                    "Value",                //Titolo asse y
                    dataset,                //dataset
                    true,                   //legenda
                    true,                   //
                    false                   //
            );

            final XYPlot plot = result.getXYPlot();                     //creo XYplot(classe generica per disegnare i dati in formato coppia(x,y))

            plot.setBackgroundPaint(new Color(0));                      //se preferisci mettere il bianco inserisci 0xffffe0 (Colore background del grafico)
            plot.setDomainGridlinesVisible(true);                       //linee tratteggiate asse y
            plot.setDomainGridlinePaint(Color.lightGray);               //colore
            plot.setRangeGridlinesVisible(true);                        //linee tratteggiate asse x
            plot.setRangeGridlinePaint(Color.lightGray);                //

            this.firstSeries(plot);
            this.secondSeries(plot);
            this.thirdSeries(plot);
            this.fourthSeries(plot);

            return result;
        }

        private void firstSeries(final XYPlot plot){
            final ValueAxis xaxis = plot.getDomainAxis();               //ValueAxis class: The base class for axes that display value data, where values are measured using the double primitive.
            xaxis.setAutoRange(true);                                   //flag che determina quando lo schema viene aggiustato automaticamente per far entrare i dati

            //Mostra nello schema i dati di 60 secondi
            xaxis.setFixedAutoRange(60000.0);  // 60 seconds
            xaxis.setVerticalTickLabels(true);

            //Mostra dati nel range x,y
            final ValueAxis yaxis = plot.getRangeAxis();
            yaxis.setRange(0.0, 100000.0);

            final XYItemRenderer renderer = plot.getRenderer();
            renderer.setSeriesPaint(0, Color.RED);

            //Colore numeri sull'asse y
            final NumberAxis yAxis1 = (NumberAxis) plot.getRangeAxis();
            yAxis1.setTickLabelPaint(Color.BLACK);

        }
        private void secondSeries(final XYPlot plot) {
            final XYDataset secondDataset = this.createDataset(series1);
            plot.setDataset(1, secondDataset); // secondo dataset (zero-based numbering)
            plot.mapDatasetToDomainAxis(1, 0); // stesso schema,dataset differente
            plot.mapDatasetToRangeAxis(1, 0); // stesso schema,dataset differente

            final XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);  //renderer che connette i punti dati
            renderer.setSeriesPaint(0, Color.BLUE);                                   //mostra linee=true
            plot.setRenderer(1, renderer);                                            //mostra forme=false
        }
        private void thirdSeries(final XYPlot plot) {
            final XYDataset thirdDataset = this.createDataset(series2);
            plot.setDataset(2, thirdDataset); // terzo dataset (zero-based numbering)
            plot.mapDatasetToDomainAxis(2, 0); // stesso schema,dataset differente
            plot.mapDatasetToRangeAxis(2, 0); // stesso schema,dataset differente

            final XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);  //renderer che connette i punti dati
            renderer.setSeriesPaint(0, Color.GREEN);                                   //mostra linee=true
            plot.setRenderer(2, renderer);                                          //mostra forme=false
            
          
        }
        private void fourthSeries(final XYPlot plot) {
            final XYDataset fourthDataset = this.createDataset(series3);
            plot.setDataset(3, fourthDataset); // quarto dataset (zero-based numbering)
            plot.mapDatasetToDomainAxis(3, 0); // stesso schema,dataset differente
            plot.mapDatasetToRangeAxis(3, 0); // stesso schema,dataset differente

            final XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);  //renderer che connette i punti dati
            renderer.setSeriesPaint(0, Color.YELLOW);                                  //mostra linee=true
            plot.setRenderer(3, renderer);                                           //mostra forme=false
            
            
        }

        /**
         * Aggiorna i dati ogni quarto di secondo
         *
         * @param e  the action event.
         */
        public void actionPerformed(final ActionEvent e) {
            series.add(new Millisecond(), p.currentPopulation()[0]);
            series1.add(new Millisecond(), p.currentPopulation()[1]);
            series2.add(new Millisecond(), p.currentPopulation()[2]);
            series3.add(new Millisecond(), p.currentPopulation()[3]);

        }
        private XYDataset createDataset(final TimeSeries series) {
            return new TimeSeriesCollection(series);
        }
}
}
