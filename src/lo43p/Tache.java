package lo43p;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.jfree.data.time.Hour;

import java.util.List;

import javax.swing.table.AbstractTableModel;


public class Tache {
    //Définition des paramétres des taches
	private final int id_tache;
	private final Date StartTime, FinishTime;
	private final String StationDepart, StationArrivee;
	private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
           
	public Tache(int id_tache, int StartTime, int FinishTime, String StationDepart,
			String StationArrivee) {
		this.id_tache = id_tache;
                //Calcul de l'heure de début en minutes
		this.StartTime = new Date(1000L * 60L * (StartTime-60));
                //Calcul de l'heure de fin en minutes
		this.FinishTime = new Date(1000L * 60L * (FinishTime-60));
                //définition de la station de départ
		this.StationDepart = NomStation(StationDepart);
                //définition de la station d'arrivée
		this.StationArrivee = NomStation(StationArrivee);
                
               
	}
    //Permettent de récupérer les différentes heures
	public String getHeureArriveeConvert() {
		return sdf.format(this.FinishTime);
	}

	public Date getHeureArriveeMinutes() {
		return this.FinishTime;
	}

	public String getHeureDepartConvert() {
		return sdf.format(this.StartTime);
	}

	public Date getHeureDepartMinutes() {
		return this.StartTime;
	}

	public int getId() {
		return id_tache;
	}

	public String getLieuArrivee() {
		return this.StationArrivee;
	}

	public String getLieuDepart() {
		return this.StationDepart;
	}
        //Permet de récupérer le service associé à la tâche
	public String getService() {
		final Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(this.getHeureDepartMinutes());
		final int StartTime = calendar.get(Calendar.HOUR_OF_DAY);

		if (StartTime < 7)
			return "matin";
		else if (StartTime < 17)
			return "jour";
		else if (StartTime < 20)
			return "soir";
		else
			return "nuit";
	}
        //Permet de récupérer la durée de la tâche
	public Date getTempsTrajet() {
		return new Date(FinishTime.getTime() - StartTime.getTime());
	}
        
        public String getTempsTrajetconvertit(){
            Date tmptrajet=new Date(FinishTime.getTime() - StartTime.getTime()-(3600*1000));
            return  sdf.format(tmptrajet);
        }

	
        //Définition des noms de stations selon la lettre indiquée
	private String NomStation(String lieu) {
		switch (lieu) {
		case "A":
			return "Valdoie Mairie";
		case "B":
			return "La Douce";
		case "C":
			return "Madrid";
		case "D":
			return "Techn'hom4";
		case "E":
			return "Gare";
		case "F":
			return "1er RA";
		case "M":
			return "Pierre Engel";
		case "N":
			return "Essert";
		case "O":
			return "Moulin";
		case "P":
			return "La DAME";
		case "U":
			return "Laurencie";
		default:
			return lieu;
		}
	}
        
        public class TacheTableModel extends AbstractTableModel {
            //Défintion des variables pour le tableau
	private static final long serialVersionUID = 1L;
	final private String[] titles = { "Service", "Lieu départ", "Heure départ",
			"Lieu arrivée", "Heure arrivée", "Temps de trajet" };
	final private List<Tache> tasks;

	public TacheTableModel(List<Tache> tasks) {
		this.tasks = tasks;
	}
        
        //Permet de récupérer le nombre de colonne
	public int getColumnCount() {
		return this.titles.length;
	}
        
        //Permet de récupérer les noms de colonne
	public String getColumnName(int columnIndex) {
		return this.titles[columnIndex];
	}
        //Permet de récupérer le nombre de tâches
	public int getRowCount() {
		return this.tasks.size();
	}

        //Récupérer la valeur selon l'index du tableau choisi
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return this.tasks.get(rowIndex).getService();
		case 1:
			return this.tasks.get(rowIndex).getLieuDepart();
		case 2:
			return this.tasks.get(rowIndex).getHeureDepartConvert();
		case 3:
			return this.tasks.get(rowIndex).getLieuArrivee();
                case 4:
			return this.tasks.get(rowIndex).getHeureArriveeConvert();
                default:
                        return this.tasks.get(rowIndex).getTempsTrajetconvertit();
		}
	}
}
}

 