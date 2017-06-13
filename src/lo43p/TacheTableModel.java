package lo43p;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TacheTableModel extends AbstractTableModel {

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