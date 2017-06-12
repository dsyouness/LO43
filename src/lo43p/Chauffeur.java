package lo43p;
import java.util.ArrayList;
import java.util.Date;

public class Chauffeur {
	private final int id, workerTimeSum, underTime, idleTime, cost;
	private final ArrayList<Tache> tasks;

	public Chauffeur(int id, int workerTime, int underTime, int idleTime,
			int cost, ArrayList<Tache> tasks) {
		this.id = id;
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
		return this.id;
	}

	public String getIdleTime() {
		return new String((this.idleTime) / 60 + "h "
				+ this.idleTime % 60 + "m");
	}

	public int getIdleTimeMinutes() {
		return this.idleTime;
	}

	public ArrayList<Tache> getTasks() {
		return this.tasks;
	}

	public String getUnderTimeSum() {
		return new String((this.underTime - this.underTime % 60) / 60 + "h "
				+ this.underTime % 60 + "m");
	}

	public int getUnderTimeSumMinutes() {
		return this.underTime;
	}

	public String getWorkerTimeSum() {
		return new String((this.workerTimeSum - this.workerTimeSum % 60) / 60
				+ "h " + this.workerTimeSum % 60 + "m");
	}

	public int getWorkerTimeSumMinutes() {
		return this.workerTimeSum;
	}

	public String toString() {
		return String.valueOf(this.id) + ":\n\tworkersumtime: "
				+ String.valueOf(workerTimeSum) + "\tundertime: "
				+ String.valueOf(underTime) + "\tidletime: "
				+ String.valueOf(idleTime) + "\tcost:" + String.valueOf(cost)
				+ "\ttaches" + String.valueOf(tasks);
	}
}
