package lo43p;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
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
	private Configuration config;
	private ArrayList<Tache> taches;
	private ArrayList<Chauffeur> chauffeurs;

	private Parser parser;
	private InstancesTable instTabView;
	private SolutionView solView;
	private ServicesView serView;

	private GlobalSolutionView globSolView;

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
		// The old tabbed pane is garbage-collected.
		// Thank you Java !
		final JTabbedPane tabbedpane = new JTabbedPane(JTabbedPane.LEFT);
		instTabView = new InstancesTable(taches);
		solView = new SolutionView(chauffeurs, config);
		serView = new ServicesView(chauffeurs);
		globSolView = new GlobalSolutionView(chauffeurs);
		tabbedpane.addTab("Instance",createImageIcon("img/tables-512.png", "xd"), instTabView);
		tabbedpane.addTab("Solution", solView);
		tabbedpane.addTab("Services", serView);
		tabbedpane.addTab("Vue globale", globSolView);
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

	private void createmenu() {
		final JMenuBar menuBar = new JMenuBar();
		final JMenu menu = new JMenu("Instances");
		final ButtonGroup bg = new ButtonGroup(); // mutual exclusion of radio
													// buttons

		for (int i = 1; i < 5; i++) {
			final String si = Integer.toString(i);
			JRadioButtonMenuItem item = new JRadioButtonMenuItem("Instance "
					+ si, i < 2);

			item.addActionListener(this);
			item.setActionCommand(si);

			bg.add(item);
			menu.add(item);
		}
		menuBar.add(menu);

		this.setJMenuBar(menuBar);
	}

	private void parseFiles() {
		parser = new Parser();

		try {
			config = parser.parse_config("instances/config");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to parse the config file");
			System.exit(1);
		}

		try {
			taches = parser.parse_instance("instances/Instance_" + num
					+ "/Instance_" + num + ".txt");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to parse the instance file");
			System.exit(1);
		}

		try {
			chauffeurs = parser.parse_solution("instances/Instance_" + num
					+ "/Solution_" + num + ".txt");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to parse the solution file");
			System.exit(1);
		}
	}
}
