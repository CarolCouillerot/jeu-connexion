import javax.swing.JPanel;

class Application 
{

	public static final int TAILLE = 19;

	public static void main(String[] args) 
	{

		Plateau plateau = new Plateau(TAILLE);
		
		Fenetre fenetre = new Fenetre("Connexion", plateau);
		//plateau.dessiner();

	}
}