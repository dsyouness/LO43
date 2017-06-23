package lo43p;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import org.jfree.chart.ChartPanel;


import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartPanel;

public class VueSolution extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final ArrayList<Chauffeur> chauffeurs;
	private final Configuration config;
	private ChartPanel chartPanel;
	private JTextPane infoChauffeur;
	private int chauffeurActuel = 0; // index du chauffeur actuel
	private DiagrammeGantt gc;

	public VueSolution(ArrayList<Chauffeur> chauffeurs, Configuration config) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.chauffeurs = chauffeurs;
		this.config = config;
		CreateUI();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent ae) {
		final JComboBox<String> cb = (JComboBox<String>) ae.getSource();
		chauffeurActuel = cb.getSelectedIndex();
		updateInfoChauffeur();
		gc = new DiagrammeGantt(this.chauffeurs.get(chauffeurActuel));
		chartPanel.setChart(gc.createChart());
	}

	private int calculCoutTotal() {
		int cout = 0;
		for (Chauffeur chauffeur : chauffeurs)
			cout += chauffeur.getCost();
		return cout;
	}

	private int calculNbTaches() {
		int nbtaches = 0;
		for (Chauffeur chauffeur : chauffeurs)
			nbtaches += chauffeur.getTasks().size();
		return nbtaches;
	}

	private void CreateUI() {
		final JPanel gantt = new JPanel();
		gantt.setLayout(new BoxLayout(gantt, BoxLayout.X_AXIS));

		final JPanel informations = new JPanel();
		informations.setLayout(new BoxLayout(informations, FlowLayout.CENTER));

              

		final JComboBox<String> combobox = new JComboBox<String>();
            
		combobox.addActionListener(this);

		infoChauffeur = new JTextPane();
		infoChauffeur.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(0, 0, 0), 1),
				"Informations sur le chauffeur", 0, 0));
                infoChauffeur.setBackground(new Color(112, 111, 111));
                infoChauffeur.setForeground(Color.WHITE);
		

		final JTextPane infoLegales = new JTextPane();
		infoLegales.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(0, 0, 0), 1),
				"Informations Configuration", 0, 0));
                infoLegales.setBackground(new Color(112, 111, 111));
                infoLegales.setForeground(Color.WHITE);
		infoLegales.setText("- Durée légale d'une pause: "
				+ config.getBreakTime() + '\n'
				+ "- Durée légale d'une journée: " + config.getWorkTime()
				+ '\n' + "- Durée de travail supplémetaire maximale: "
				+ config.getExtraWorkTime()+ '\n');

		gc = new DiagrammeGantt(this.chauffeurs.get(0)); // On affiche le premier
														// chauffeur
		chartPanel = new ChartPanel(gc.createChart());

		for (Chauffeur chauffeur : chauffeurs)
			combobox.addItem("Chauffeur " + chauffeur.getId());

		updateInfoChauffeur();
		informations.add(combobox);
		informations.add(infoChauffeur);
		//informations.add(infoSolution);
		informations.add(infoLegales);
		this.add(informations);

		gantt.add(chartPanel);
		this.add(gantt);
	}

	private void updateInfoChauffeur() {
		String tempsRepos = new String("- Temps non travaillé: "
				+ chauffeurs.get(chauffeurActuel).getidleTimeConvert());
		if (chauffeurs.get(chauffeurActuel).getidelTimeMinutes() < config
				.getBreakTimeMinutes())
			tempsRepos += "( < durée legale)";
		tempsRepos += '\n';

		String undertime = new String("- Écart à la durée légale: ");
		if (chauffeurs.get(chauffeurActuel).getWorkerTimeSumMinutes() < config
				.getWorkTimeMinutes()) {
			undertime += "-";
		}
		undertime += chauffeurs.get(chauffeurActuel).getUnderTimeSumConvert();
		undertime += "                        ";

		String workTimeSup = new String("- Heure supplémentaire : ");
		if (chauffeurs.get(chauffeurActuel).getWorkerTimeSumMinutes() > config
				.getWorkTimeMinutes()) {
			int workTimeSupTmp = chauffeurs.get(chauffeurActuel)
					.getWorkerTimeSumMinutes() - config.getWorkTimeMinutes();
			workTimeSup += (workTimeSupTmp - workTimeSupTmp % 60) / 60 + "h "
					+ workTimeSupTmp % 60 + "m";
			;
		} else {
			workTimeSup += "0h 0m";
		}
		workTimeSup += "                       ";

		String tempsTravail = new String("- Temps de travail: "
				+ chauffeurs.get(chauffeurActuel).getWorkerTimeSumConvert());
		if (chauffeurs.get(chauffeurActuel).getWorkerTimeSumMinutes() > config
				.getWorkTimeMinutes())
			tempsTravail += "(> durée legale)";
		tempsTravail += '\n';

		infoChauffeur.setText("- Coût: "
				+ chauffeurs.get(chauffeurActuel).getgetCostconvert() + "                                                        "
				+ "- Nombre de taches: "
				+ chauffeurs.get(chauffeurActuel).getTasks().size() + "\n"
				+ undertime + tempsTravail + workTimeSup + tempsRepos);
	}




public class GlobalSolutionView extends JPanel {
	private static final long serialVersionUID = 1L;
	private DiagrammeGantt gc;
        

	public GlobalSolutionView(ArrayList<Chauffeur> chauffeurs) {
		this.setLayout(new BorderLayout());
		gc = new DiagrammeGantt(chauffeurs);
		final ChartPanel chartPanel = new ChartPanel(gc.createChart());
		this.add(chartPanel);
	}
}
}