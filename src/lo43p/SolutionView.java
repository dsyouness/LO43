package lo43p;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import org.jfree.chart.ChartPanel;

public class SolutionView extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final ArrayList<Chauffeur> chauffeurs;
	private final Configuration config;
	private ChartPanel chartPanel;
	private JTextPane infoChauffeur;
	private int chauffeurActuel = 0; // index du chauffeur actuel
	private GanttChart gc;

	public SolutionView(ArrayList<Chauffeur> chauffeurs, Configuration config) {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

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
		gc = new GanttChart(this.chauffeurs.get(chauffeurActuel));
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
		informations.setLayout(new BoxLayout(informations, BoxLayout.Y_AXIS));

		final JComboBox<String> combobox = new JComboBox<String>();
		combobox.addActionListener(this);

		infoChauffeur = new JTextPane();
		infoChauffeur.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(0, 0, 0), 2),
				"Informations sur le chauffeur", 0, 0));

		final JTextPane infoSolution = new JTextPane();
		infoSolution.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(0, 0, 0), 2),
				"Informations sur la solution", 0, 0));
		infoSolution.setText("- Nombre de chauffeurs: " + chauffeurs.size()
				+ '\n' + "- Coût total de la solution: "
				+ this.calculCoutTotal() + '\n' + "- Nombre total de tâches: "
				+ this.calculNbTaches() + '\n');

		final JTextPane infoLegales = new JTextPane();
		infoLegales.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(0, 0, 0), 2),
				"Informations légales", 0, 0));
		infoLegales.setText("- Durée légale d'une pause: "
				+ config.getBreakTime() + '\n'
				+ "- Durée légale d'une journée: " + config.getWorkTime()
				+ '\n' + "- Durée de travail supplémetaire maximale: "
				+ config.getExtraWorkTime());

		gc = new GanttChart(this.chauffeurs.get(0)); // On affiche le premier
														// chauffeur
		chartPanel = new ChartPanel(gc.createChart());

		for (Chauffeur chauffeur : chauffeurs)
			combobox.addItem("Chauffeur " + chauffeur.getId());

		updateInfoChauffeur();

		informations.add(combobox);
		informations.add(infoChauffeur);
		informations.add(infoSolution);
		informations.add(infoLegales);
		this.add(informations);

		gantt.add(chartPanel);
		this.add(gantt);
	}

	private void updateInfoChauffeur() {
		String tempsRepos = new String("- Temps non travaillé: "
				+ chauffeurs.get(chauffeurActuel).getIdleTime());
		if (chauffeurs.get(chauffeurActuel).getIdleTimeMinutes() < config
				.getBreakTimeMinutes())
			tempsRepos += "( < durée legale)";
		tempsRepos += '\n';

		String undertime = new String("- Écart à la durée légale: ");
		if (chauffeurs.get(chauffeurActuel).getWorkerTimeSumMinutes() < config
				.getWorkTimeMinutes()) {
			undertime += "-";
		}
		undertime += chauffeurs.get(chauffeurActuel).getUnderTimeSum();
		undertime += '\n';

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
		workTimeSup += "\n";

		String tempsTravail = new String("- Temps de travail: "
				+ chauffeurs.get(chauffeurActuel).getWorkerTimeSum());
		if (chauffeurs.get(chauffeurActuel).getWorkerTimeSumMinutes() > config
				.getWorkTimeMinutes())
			tempsTravail += "(> durée legale)";
		tempsTravail += '\n';

		infoChauffeur.setText("- Coût: "
				+ chauffeurs.get(chauffeurActuel).getCost() + "\n"
				+ "- Nombre de taches: "
				+ chauffeurs.get(chauffeurActuel).getTasks().size() + "\n"
				+ undertime + tempsTravail + workTimeSup + tempsRepos);
	}
}
