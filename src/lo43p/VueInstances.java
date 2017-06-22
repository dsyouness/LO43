package lo43p;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;


public class VueInstances extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	final private JComboBox<String> serviceComboBox = new JComboBox<String>();
	final private JComboBox<String> departComboBox = new JComboBox<String>();
	final private JComboBox<String> arriveeComboBox = new JComboBox<String>();

	private TableRowSorter<TacheTableModel> sorter;
	private List<Tache> tasks;

	public VueInstances(List<Tache> taskTableModel) {
		this.setLayout(new BorderLayout());
		this.tasks = taskTableModel;
		createUI();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.serviceComboBox
				|| event.getSource() == this.departComboBox
				|| event.getSource() == this.arriveeComboBox) {

			final String serviceRegex = (this.serviceComboBox
					.getSelectedIndex() == 0) ? ".*" : this.serviceComboBox
					.getSelectedItem().toString();

			final String departureRegex = (this.departComboBox
					.getSelectedIndex() == 0) ? ".*" : this.departComboBox
					.getSelectedItem().toString();

			final String arrivalRegex = (this.arriveeComboBox
					.getSelectedIndex() == 0) ? ".*" : this.arriveeComboBox
					.getSelectedItem().toString();

			updateFilter(serviceRegex, departureRegex, arrivalRegex);
		}
	}

	private void createUI() {
		final TacheTableModel taskTableModel = new TacheTableModel(this.tasks);
		
                this.sorter = new TableRowSorter<TacheTableModel>(taskTableModel);

		final JPanel filterPanel = new JPanel();
		filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.X_AXIS));

		final JLabel filterLabel = new JLabel("Filtres: ", JLabel.CENTER);

		this.serviceComboBox.addItem("Tous les services");
		this.serviceComboBox.addItem("matin");
		this.serviceComboBox.addItem("jour");
		this.serviceComboBox.addItem("soir");
		this.serviceComboBox.addItem("nuit");

		// Recuperation du set des lieux de depart
		final TreeSet<String> departSet = new TreeSet<String>();
		final TreeSet<String> arriveeSet = new TreeSet<String>();
		for (Tache task : this.tasks) {
			departSet.add(task.getLieuDepart());
			arriveeSet.add(task.getLieuArrivee());
		}

		this.departComboBox.addItem("Tous les départs");
		for (String lieuDepart : departSet)
			departComboBox.addItem(lieuDepart);

		this.arriveeComboBox.addItem("Toutes les arrivées");
		for (String arrival : arriveeSet)
			arriveeComboBox.addItem(arrival);

		this.serviceComboBox.addActionListener(this);
		this.departComboBox.addActionListener(this);
		this.arriveeComboBox.addActionListener(this);

		filterPanel.add(filterLabel);
		filterPanel.add(this.serviceComboBox);
		filterPanel.add(this.departComboBox);
		filterPanel.add(this.arriveeComboBox);
		this.add(filterPanel, BorderLayout.NORTH);

		final JTable tasksTable = new JTable(taskTableModel);
		tasksTable.setRowSorter(this.sorter);

		final JScrollPane jsp = new JScrollPane(tasksTable);
		this.add(jsp, BorderLayout.CENTER);
	}

	private void updateFilter(String serviceRegex, String departureRegex,
			String arrivalRegex) {
		final RowFilter<TacheTableModel, Object> serviceFilter = RowFilter
				.regexFilter(serviceRegex, 0);
		final RowFilter<TacheTableModel, Object> departureFilter = RowFilter
				.regexFilter(departureRegex, 1);
		final RowFilter<TacheTableModel, Object> arrivalFilter = RowFilter
				.regexFilter(arrivalRegex, 3);

		final List<RowFilter<TacheTableModel, Object>> filters = new ArrayList<RowFilter<TacheTableModel, Object>>();

		filters.add(serviceFilter);
		filters.add(departureFilter);
		filters.add(arrivalFilter);

		final RowFilter<TacheTableModel, Object> compoundRowFilter = RowFilter
				.andFilter(filters);
		;
		this.sorter.setRowFilter(compoundRowFilter);
	}
}
class TacheTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	final private String[] titles = { "Service", "Lieu départ", "Heure départ",
			"Lieu arrivée", "Heure arrivée", "Temps de trajet" };
	final private List<Tache> tasks;

	public TacheTableModel(List<Tache> tasks) {
		this.tasks = tasks;
	}

	public int getColumnCount() {
		return this.titles.length;
	}

	public String getColumnName(int columnIndex) {
		return this.titles[columnIndex];
	}

	public int getRowCount() {
		return this.tasks.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return this.tasks.get(rowIndex).getService();
		case 1:
			return this.tasks.get(rowIndex).getLieuDepart();
		case 2:
			return this.tasks.get(rowIndex).getHeureDepart();
		case 3:
			return this.tasks.get(rowIndex).getLieuArrivee();
                case 4:
			return this.tasks.get(rowIndex).getHeureArrivee();
                default:
                        return this.tasks.get(rowIndex).getTempsTrajetconvertit();
		}
	}
}

