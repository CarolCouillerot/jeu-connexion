import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class Grille extends JFrame
{
	// Dimensions de la grille : 0..N lignes et 0..M colonnes
	private int taille_;


	public Grille(int taille) 
	{
		this.taille_ = taille;
	}

	public void dessiner(Graphics g2d) 
	{
		// affichage du quadrillage
		for(int i = 0; i < taille_; ++i) {
			for(int j = 0; j < taille_; ++j) {
				g2d.drawLine(10*i, 0, 10*i, 100);
				g2d.drawLine(0, 10*i, 100, 10*i);
			}
		}
	}
}
