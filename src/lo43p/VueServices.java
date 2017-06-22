package lo43p;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javafx.scene.chart.NumberAxis;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class VueServices extends JPanel {

	private static final long serialVersionUID = 1L;
	private final ArrayList<Chauffeur> chauffeurs;
	private final DefaultCategoryDataset  dataset = new DefaultCategoryDataset ();
        

	public VueServices(ArrayList<Chauffeur> chauffeurs) {
		this.chauffeurs = chauffeurs;
		createDataset();
		this.setLayout(new BorderLayout());
		this.add(new ChartPanel(createChart()), BorderLayout.CENTER);
	}

	private JFreeChart createChart() {
		return ChartFactory.createBarChart3D("Répartition des services", // chart
				"service",
                                "pourcentage",// title
				dataset, // data
                                PlotOrientation.VERTICAL,
				true, // include legend
				true, true);
	}
       
        
	private DefaultCategoryDataset createDataset() {
		double matin = 0, jour = 0, soir = 0, nuit = 0;
		for (Chauffeur chauffeur : chauffeurs) {
			for (Tache task : chauffeur.getTasks()) {
				final Calendar calendar = GregorianCalendar.getInstance();
				calendar.setTime(task.getHeureDepartMinutes());
				final int heureDepart = calendar.get(Calendar.HOUR_OF_DAY);

				if (heureDepart < 7)
					++matin;
				else if (heureDepart < 17)
					++jour;
				else if (heureDepart < 20)
					++soir;
				else
					++nuit;
			}
		}

		
                dataset.setValue(matin, "Matin", "");
               dataset.setValue(jour, "Jour", "");
               dataset.setValue(soir, "Soir", "");
               dataset.setValue(nuit, "Nuit", "");
              
                return dataset;
	}
}
