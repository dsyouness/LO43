package lo43p;

import java.awt.Color;
import java.awt.Image;
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
    //Déclaration des variables du diagramme de Gantt
        private static class RainbowRenderer extends GanttRenderer {
		private static final long serialVersionUID = 1L;
		private static final Random randomGenerator = new Random();

		@Override
		public Paint getItemPaint(int row, int col) {
                   //Génération aléatoire de couleurs pour le diagramme de Gantt
			final int red = randomGenerator.nextInt(255);
			final int green = randomGenerator.nextInt(255);
			final int blue = randomGenerator.nextInt(255);

			return new Color(red, green, blue);
		}
	}
	
	private final TaskSeriesCollection dataset = new TaskSeriesCollection();

	private final String title, axeOrdonnee;

	public DiagrammeGantt(ArrayList<Chauffeur> chauffeurs) {
              //titre sur l'axe des ordonnées
            // où se trouve tous les chauffeurs de l'instance
		this.axeOrdonnee = "Chauffeurs";
                //Titre du diagramme de Gantt général au niveau de "Gantt" 
		this.title = "GANTT Géneral";
		this.setData(chauffeurs);
	}

	public DiagrammeGantt(Chauffeur chauffeur) {
            //titre des numéro d'instances sur l'axe des ordonnées
            //au niveau de la vue Solution
		this.axeOrdonnee = "Instance Numéro";
                
		this.title = "GANTT ";
		this.setData(chauffeur);
	}
        //Création des diagrammes de Gantt
	public JFreeChart createChart() {
		final JFreeChart gc = ChartFactory.createGanttChart(title, // chart
																	// title
				axeOrdonnee, // range axis label
				"Heures", // domain axis label
				this.dataset, // data
				false, // include legend
				true, // tooltips
				false // urls
				);
		gc.getCategoryPlot().setRenderer(0, new RainbowRenderer());
                gc.setBorderVisible(true);
                gc.setBackgroundPaint(new Color(13, 128, 200));
		return gc;
	}
        //Définition des séries de tâches correspondantes pour tous les chauffeurs
        // en fonction des tâches effectuées
	private void setData(ArrayList<Chauffeur> chauffeurs) {
		final TaskSeries taskSerie = new TaskSeries("Gantt Géneral");

		for (Chauffeur chauffeur : chauffeurs) {
			final Task tasksChauffeur = new Task(""
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
        //Définition des séries de tâches correspondantes à un chauffeur
        // en fonction des tâches effectuées
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
