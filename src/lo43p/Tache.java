package lo43p;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.jfree.data.time.Hour;

public class Tache {
	private final int id;
	private final Date heureDepart, heureArrivee;
	private final String lieuDepart, lieuArrivee;
	private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

	public Tache(int id, int heureDepart, int heureArrivee, String lieuDepart,
			String lieuArrivee) {
		this.id = id;
		this.heureDepart = new Date(1000L * 60L * (heureDepart-60));
		this.heureArrivee = new Date(1000L * 60L * (heureArrivee-60));
		this.lieuDepart = translate(lieuDepart);
		this.lieuArrivee = translate(lieuArrivee);
                
               
	}

	public String getHeureArrivee() {
		return sdf.format(this.heureArrivee);
	}

	public Date getHeureArriveeMinutes() {
		return this.heureArrivee;
	}

	public String getHeureDepart() {
		return sdf.format(this.heureDepart);
	}

	public Date getHeureDepartMinutes() {
		return this.heureDepart;
	}

	public int getId() {
		return id;
	}

	public String getLieuArrivee() {
		return this.lieuArrivee;
	}

	public String getLieuDepart() {
		return this.lieuDepart;
	}

	public String getService() {
		final Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(this.getHeureDepartMinutes());
		final int heureDepart = calendar.get(Calendar.HOUR_OF_DAY);

		if (heureDepart < 7)
			return "matin";
		else if (heureDepart < 17)
			return "jour";
		else if (heureDepart < 20)
			return "soir";
		else
			return "nuit";
	}

	public Date getTempsTrajet() {
		return new Date(heureArrivee.getTime() - heureDepart.getTime());
	}
        
        public String getTempsTrajetconvertit(){
            Date xd=new Date(heureArrivee.getTime() - heureDepart.getTime()-(3600*1000));
            return  sdf.format(xd);
        }

	public String toString() {
		return String.valueOf(this.id) + ": " + String.valueOf(heureArrivee)
				+ " " + String.valueOf(heureDepart) + " " + lieuDepart + " "
				+ lieuArrivee;
	}

	private String translate(String lieu) {
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
}
