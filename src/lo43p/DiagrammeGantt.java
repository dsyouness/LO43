package lo43p;

import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.category.GanttRenderer;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;

public class DiagrammeGantt {
	private static class RainbowRenderer extends GanttRenderer {
		private static final long serialVersionUID = 1L;
		private static final Random randomGenerator = new Random();

		@Override
		public Paint getItemPaint(int row, int col) {
			final int red = randomGenerator.nextInt(255);
			final int green = randomGenerator.nextInt(255);
			final int blue = randomGenerator.nextInt(255);

			return new Color(red, green, blue);
		}
	}
	private final TaskSeriesCollection dataset = new TaskSeriesCollection();

	private final String title, axeOrdonnee;

	public DiagrammeGantt(ArrayList<Chauffeur> chauffeurs) {
		this.axeOrdonnee = "Chauffeurs";
		this.title = "Informations globales";
		this.setData(chauffeurs);
	}

	public DiagrammeGantt(Chauffeur chauffeur) {
		this.axeOrdonnee = "Instance Numéro";
		this.title = "GANTT ";
		this.setData(chauffeur);
	}

	public JFreeChart createChart() {
		final JFreeChart gc = ChartFactory.createGanttChart(title, // chart
																	// title
				axeOrdonnee, // range axis label
				"Heures", // domain axis label
				this.dataset, // data
				false, // include legend
				false, // tooltips
				false // urls
				);
		gc.getCategoryPlot().setRenderer(0, new RainbowRenderer());
		return gc;
	}

	private void setData(ArrayList<Chauffeur> chauffeurs) {
		final TaskSeries taskSerie = new TaskSeries("Gantt Géneral");

		for (Chauffeur chauffeur : chauffeurs) {
			final Task tasksChauffeur = new Task("Chauffeur "
					+ chauffeur.getId(), chauffeur.getDebutService(),
					chauffeur.getFinService());

			for (Tache tache : chauffeur.getTasks()) {
				final Task task = new Task(String.valueOf(tache.getId()),
						new SimpleTimePeriod(tache.getHeureDepartMinutes(),
								tache.getHeureArriveeMinutes()));
				tasksChauffeur.addSubtask(task);
			}
			taskSerie.add(tasksChauffeur);
		}
		this.dataset.add(taskSerie);
	}

	public void setData(Chauffeur chauffeur) {
		this.dataset.removeAll();
		final TaskSeries ts = new TaskSeries(String.valueOf(chauffeur.getId()));

		for (Tache tache : chauffeur.getTasks())
			ts.add(new Task(
					String.valueOf(tache.getId()), new SimpleTimePeriod(tache
					.getHeureDepartMinutes(), tache.getHeureArriveeMinutes())));

		this.dataset.add(ts);
	}
}
