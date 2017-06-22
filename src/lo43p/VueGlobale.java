/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lo43p;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;

/**
 *
 * @author Med Amine
 */
public class VueGlobale extends JPanel {
	private static final long serialVersionUID = 1L;
	private DiagrammeGantt gc;
        

	public VueGlobale(ArrayList<Chauffeur> chauffeurs) {
		this.setLayout(new BorderLayout());
		gc = new DiagrammeGantt(chauffeurs);
		final ChartPanel chartPanel = new ChartPanel(gc.createChart());
		this.add(chartPanel);
	}
}
