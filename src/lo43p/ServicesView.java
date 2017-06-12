package lo43p;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class ServicesView extends JPanel {

	private static final long serialVersionUID = 1L;
	private final ArrayList<Chauffeur> chauffeurs;
	private final DefaultPieDataset dataset = new DefaultPieDataset();

	public ServicesView(ArrayList<Chauffeur> chauffeurs) {
		this.chauffeurs = chauffeurs;
		createDataset();
		this.setLayout(new BorderLayout());
		this.add(new ChartPanel(createChart()), BorderLayout.CENTER);
	}

	private JFreeChart createChart() {
		return ChartFactory.createPieChart("Répartition des services", // chart
																		// title
				dataset, // data
				true, // include legend
				true, true);
	}

	private PieDataset createDataset() {
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

		dataset.setValue("Matin", matin);
		dataset.setValue("Jour", jour);
		dataset.setValue("Soir", soir);
		dataset.setValue("Nuit", nuit);
		return dataset;
	}
}
