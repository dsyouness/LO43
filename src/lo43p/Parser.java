package lo43p;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class Parser {
	public Configuration parse_config(String inputFile)
			throws FileNotFoundException, IOException {
		final Properties p = new Properties();
		p.load(new FileInputStream(inputFile));

		return new Configuration(new Integer(p.getProperty("workTime")),
				new Integer(p.getProperty("extraWorkTime")), new Integer(
						p.getProperty("breakTime")));
	}

	@SuppressWarnings("resource")
	public ArrayList<Tache> parse_instance(String inputFile) throws IOException {
		int counter = 1;
		final ArrayList<Tache> taches = new ArrayList<Tache>();
		final Scanner s = new Scanner(new File(inputFile)).useDelimiter("\\s+");

		while (s.hasNext()) {
			final int h1, h2;
			final String pointA, pointB;

			h1 = (s.nextInt()) * 60 + s.nextInt();
			h2 = (s.nextInt())* 60 + s.nextInt();
			pointA = Character.toString(s.next().charAt(0));
			pointB = Character.toString(s.next().charAt(0));

			taches.add(new Tache(counter, h1, h2, pointA, pointB));
			++counter;
                        //System.out.println(h1);
		}
		s.close();
		return taches;
	}

	final public ArrayList<Chauffeur> parse_solution(String inputFile)
			throws IOException {

		@SuppressWarnings("resource")
		final Scanner s = new Scanner(new File(inputFile)).useDelimiter("\\s+");

		final ArrayList<Chauffeur> chauffeurs = new ArrayList<Chauffeur>();
		int idChauffeur = 1;

		s.nextLine();// Who cares about the number of drivers ?
		s.nextLine(); // Who care about first driver's name ?

		while (true) {
			s.nextLine(); // skip empty line

			final int workerTime = Integer.parseInt(s.nextLine().split("=")[1]);
			final int underTime = Integer
					.parseInt(s.nextLine().split(" = ")[1]);
			final int idleTime = Integer.parseInt(s.nextLine().split("=")[1]
					.trim());
			final int cost = Integer.parseInt(s.nextLine().split("=")[1]);

			ArrayList<Tache> tachesConduite = new ArrayList<Tache>();
			while (true) {
				final String[] line = s.nextLine().split(":");

				final int taskid = Integer.parseInt(line[1].split("\t")[0]);
				final int heureDepart = Integer.parseInt(line[2].split(":")[0]
						.split("\t")[0]);

				final String last = line[3];
				final int finishTime = Integer.parseInt(last.substring(0,
						last.length() - 2));
				final String lieuDepart = Character.toString(last.charAt(last
						.length() - 2));
				final String lieuArrivee = Character.toString(last.charAt(last
						.length() - 1));
				tachesConduite.add(new Tache(taskid, heureDepart, finishTime,
						lieuDepart, lieuArrivee));

				// Condition de sortie
				final String nextLine = s.nextLine();
				if (nextLine.startsWith("----Worker"))
					break;
				else if (nextLine
						.startsWith("--------------------------------")) {
					s.close();
					return chauffeurs;
				}

			}
			chauffeurs.add(new Chauffeur(idChauffeur, workerTime, underTime,
					idleTime, cost, tachesConduite));
			++idChauffeur;
		}
	}
}
