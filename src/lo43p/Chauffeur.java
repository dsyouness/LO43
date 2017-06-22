package lo43p;
import java.util.ArrayList;
import java.util.Date;

public class Chauffeur {
	private final int id_chauffeur, workerTimeSum, underTime, idleTime, cost;
	private final ArrayList<Tache> tasks;

	public Chauffeur(int id, int workerTime, int underTime, int idleTime,
			int cost, ArrayList<Tache> tasks) {
		this.id_chauffeur = id;
		this.workerTimeSum = workerTime;
		this.underTime = underTime;
		this.idleTime = idleTime;
		this.cost = cost;
		this.tasks = tasks;
	}

	public int getCost() {
		return this.cost;
	}

	public Date getDebutService() {
		Date minimum = tasks.get(0).getHeureDepartMinutes();
		for (Tache task : tasks)
			if (task.getHeureDepartMinutes().before(minimum))
				minimum = task.getHeureDepartMinutes();
		return minimum;
	}

	public Date getFinService() {
		Date maximum = tasks.get(0).getHeureDepartMinutes();
		for (Tache task : tasks)
			if (task.getHeureArriveeMinutes().after(maximum))
				maximum = task.getHeureArriveeMinutes();
		return maximum;
	}

	public int getId() {
		return this.id_chauffeur;
	}

	public String getidleTimeConvert() {
		return new String((this.idleTime) / 60 + "h "
				+ this.idleTime % 60 + "m");
	}

	public int getidelTimeMinutes() {
		return this.idleTime;
	}

	public ArrayList<Tache> getTasks() {
		return this.tasks;
	}

	public String getUnderTimeSumConvert() {
		return new String((this.underTime)  / 60 + "h "
				+ this.underTime % 60 + "m");
	}

	public int getUnderTimeSumMinutes() {
		return this.underTime;
	}

	public String getWorkerTimeSumConvert() {
		return new String((this.workerTimeSum) / 60
				+ "h " + this.workerTimeSum % 60 + "m");
	}

	public int getWorkerTimeSumMinutes() {
		return this.workerTimeSum;
	}

	
}
