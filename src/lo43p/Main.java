package lo43p;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;

import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class Main extends ApplicationFrame implements ActionListener {
	private static final long serialVersionUID = 1;

	public static void main(String[] argv) {
		final Main main = new Main(argv);
		main.pack();
		RefineryUtilities.centerFrameOnScreen(main);
		main.setVisible(true);
	}
      //Définition des variables pour e "Main
	private Configuration config;
	private ArrayList<Tache> taches;
	private ArrayList<Chauffeur> chauffeurs;
        //PErmet la lecture de fichier
	private Rfile parser;
	private VueInstances instTabView;
	private VueSolution solView;
	
	private VueGlobale globSolView;

	private String num = "1";

	public Main(String[] argv) {
		super("LO43P BUS");
		this.createmenu();
		this.parseFiles();
		this.createGUI();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		this.num = ae.getActionCommand();
		this.parseFiles();
		this.createGUI();
                
	}
          
	public void createGUI() {
		
		final JTabbedPane tabbedpane = new JTabbedPane(JTabbedPane.LEFT);
                tabbedpane.setBackground(new java.awt.Color(112, 111, 111));
		instTabView = new VueInstances(taches);
		solView = new VueSolution(chauffeurs, config);
                globSolView = new VueGlobale(chauffeurs);
    
                
                
		tabbedpane.addTab("Instance",createImageIcon("img/instance-img.png", "xd"), instTabView);
		tabbedpane.addTab("Solution",createImageIcon("img/solution-img.png", "xd"), solView);
		tabbedpane.addTab("Gantt",createImageIcon("img/gantt-img.png", "xd"), globSolView);
                
                
		setContentPane(tabbedpane);
	}
            protected ImageIcon createImageIcon(String path,
                                               String description) {
            java.net.URL imgURL = getClass().getResource(path);
            if (imgURL != null) {
            return new ImageIcon(imgURL, description);
            } else {
            System.err.println("Couldn't find file: " + path);
            return null;
            }
            }
//Création du menu  d'accueil
	private void createmenu() {
		final JMenuBar menuBar = new JMenuBar();
                final JMenu menui = new JMenu("Acceuil");
		final JMenu menu = new JMenu("Instances");
                JMenuItem menuMenu = new JMenuItem("Menu");
                JMenuItem menuQuiter = new JMenuItem("Quiter");
		final ButtonGroup bg = new ButtonGroup(); 
                File file = new File("instances/");
                File[] files = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory();
                }
                 });
           
		for (int i = 1; i < files.length+1; i++) {
			final String si = Integer.toString(i);
			JRadioButtonMenuItem item = new JRadioButtonMenuItem("Instance "
					+ si, i < 2);

			item.addActionListener(this);
			item.setActionCommand(si);

			bg.add(item);
			menu.add(item);
		}
                menuBar.add(menui);
		menuBar.add(menu);
                menui.add(menuMenu);
                menui.add(menuQuiter);
                menuMenu.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //Menu.main(new String[0]);//To change body of generated methods, choose Tools | Templates.
                    }
                });

		this.setJMenuBar(menuBar);
	}

        //Lire les fichiers
	private void parseFiles() {
		parser = new Rfile();

		try {
			config = parser.Rfile_config("instances/config");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to parse the config file");
			System.exit(1);
		}

		try {
			taches = parser.Rfile_instance("instances/Instance_" + num
					+ "/Instance_" + num + ".txt");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to parse the instance file");
			System.exit(1);
		}

		try {
			chauffeurs = parser.Rfile_solution("instances/Instance_" + num
					+ "/Solution_" + num + ".txt");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to parse the solution file");
			System.exit(1);
		}
	}
}




//classe qui permet la lecture de fichiers
class Rfile {
	public Configuration Rfile_config(String inputFile)
			throws FileNotFoundException, IOException {
		final Properties p = new Properties();
		p.load(new FileInputStream(inputFile));

		return new Configuration(new Integer(p.getProperty("workTime")),
				new Integer(p.getProperty("extraWorkTime")), new Integer(
						p.getProperty("breakTime")));
	}

	@SuppressWarnings("resource")
	public ArrayList<Tache> Rfile_instance(String inputFile) throws IOException {
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

	final public ArrayList<Chauffeur> Rfile_solution(String inputFile)
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
