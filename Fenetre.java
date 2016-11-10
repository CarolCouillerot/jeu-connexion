import javax.swing.JFrame;
import javax.swing.JPanel;


public class Fenetre extends JFrame {
	

	public Fenetre(String titre, JPanel panel) {
		// instanciation de l'instance de JFrame et de son contenu
		super(titre);
		getContentPane().add(panel);

		// paramétrage de la fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		setResizable(false);

		// affichage
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}
}
