package lo43p;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;

public class GlobalSolutionView extends JPanel {
	private static final long serialVersionUID = 1L;
	private GanttChart gc;

	public GlobalSolutionView(ArrayList<Chauffeur> chauffeurs) {
		this.setLayout(new BorderLayout());
		gc = new GanttChart(chauffeurs);
		final ChartPanel chartPanel = new ChartPanel(gc.createChart());
		this.add(chartPanel);
	}
}
