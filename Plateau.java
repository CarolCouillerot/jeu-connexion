import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

class Plateau extends JPanel
{

	public static final int Pix = 30;

	private Case plateau_[][];
	private Case predecesseur_[][];
	private Grille grille_;
	private int taille_;
	private int largeur_;
	private int hauteur_;

	public static final String FG_DEFAULT = "\u001B[0m";
	public static final String BG_DEFAULT = "\u001B[49m";
	public static final String BG_LIGHTBLUE = "\u001B[104m";
	public static final String BG_LIGHTGREY = "\u001B[47m";
	public static final String BG_RED = "\u001B[41m";

	// Nombres de cases par joueur au départ
	public static final int K = 2;

	public Plateau(int taille) {
		
		taille_ = taille;

		// Dimensions de la fenêtre
		setPreferredSize(new Dimension(Pix*(taille_+1), Pix*(taille_+1)));
		
		// Grille
		grille_ = new Grille(taille_);

		plateau_ = new Case[taille_][taille_];
		for(int i = 0; i < taille_; ++i) {
			for(int j = 0; j < taille_; ++j) {

				plateau_[i][j] = new Case(i,j,'V',' ', 0);
			}
		}

		genererPositionDebut('B');
		genererPositionDebut('R');
	}


	private void genererPositionDebut(char col) {
		Random rand = new Random();
		int x[] = new int[K];
		int y[] = new int[K];
		for(int i = 0; i < K; ++i) {

			x[i] = rand.nextInt(taille_);
			y[i] = rand.nextInt(taille_);
		}
		boolean coordDistincte = false;
		while(!coordDistincte) {
			coordDistincte = true;
			for(int i = 0; i < K-1; ++i)
				for(int j = i+1; j < K; ++j) {
					if(x[i]==x[j] && y[i]==y[j]) {
						coordDistincte = false;
						x[j] = rand.nextInt(taille_);
						y[j] = rand.nextInt(taille_);
					}
				}
		}

		for(int i = 0; i < K; ++i) {
			plateau_[x[i]][y[i]].setCouleur(col);
			plateau_[x[i]][y[i]].setTypeCase('*');
			plateau_[x[i]][y[i]].addEtoiles(1);
		}
	}

	public void colorerCase(int x, int y, char col) {
		plateau_[x][y].setCouleur(col);
	}

	public boolean ajoutePion(char p, int i, int j) {
		boolean possible = false;
		if ( plateau_[i][j].getCouleur() == 'V' ) {
			possible = true;
			plateau_[i][j].setCouleur(p);
			for( Case v : voisins(i, j) ) {
				if ( v.getCouleur() == p )
					union(plateau_[i][j], v);
			}
		}
		return possible;
	}

	public void afficheComposante(int i, int j) {
		ArrayList<Case> aAfficher = new ArrayList<Case>();

		afficheComposanteRec(plateau_[i][j]	, aAfficher);

		for(int k=0; k<aAfficher.size(); ++k) {
			System.out.print("("+aAfficher.get(k).getX()+","+aAfficher.get(k).getY()+") ");
		}
		System.out.println();
	}

	public void afficheComposanteRec(Case c, ArrayList<Case> dejaListe) {
		dejaListe.add(c);

		for(Case v : voisins(c.getX(), c.getY()) ) {
			if( v.getCouleur() == c.getCouleur() && !dejaListe.contains(v)) {
				afficheComposanteRec(v, dejaListe);
			}
		}

	}

	// Retourne le nombre d'élément de la classe qui contient c
	public int taille(Case c) {
		if( c.getX() == c.getXParent() && c.getY() == c.getYParent())
			return 0;
		else
			return 1 + taille(plateau_[c.getXParent()][c.getYParent()]);
	}


	public Case classe(int i, int j) {
		int a = plateau_[i][j].getXParent();
		int b = plateau_[i][j].getYParent();
		if(i == a && j == b)
			return plateau_[i][j];
		else {
			plateau_[i][j].setParent(classe(a,b));
			return plateau_[a][b];
		}
	}


	public void union(Case c1, Case c2) {
		int taille1 = taille(c1);
		int taille2 = taille(c2);
		c1 = classe(c1.getX(), c1.getY());
		c2 = classe(c2.getX(), c2.getY());

		if( taille1 < taille2) {
			c1.setParent(c2);
			c2.addEtoiles(c1.getNbEtoiles());
		}
		else {
			c2.setParent(c1);
			c1.addEtoiles(c2.getNbEtoiles());
		}
	}

	public ArrayList<Case> voisins(int i, int j) {
		ArrayList<Case> v = new ArrayList<Case>();

		if( i-1 >= 0 )
			v.add( plateau_[i-1][j] );
		if( i+1 < taille_ )
			v.add( plateau_[i+1][j] );
		if( j-1 >= 0 )
			v.add( plateau_[i][j-1] );
		if( j+1 < taille_ )
			v.add( plateau_[i][j+1] );
		if( i+1 < taille_ && j-1 >= 0 )
			v.add( plateau_[i+1][j-1]);
		if( i-1 >= 0 && j+1 < taille_ )
			v.add( plateau_[i-1][j+1] );
		if( i-1 >= 0 && j-1 >= 0)
			v.add( plateau_[i-1][j-1]);
		if( i+1 < taille_ && j+1 < taille_)
			v.add( plateau_[i+1][j+1]);

		return v;
	}

	public int nombreEtoiles(Case c) {
		Case racine = classe(c.getX(), c.getY());

		return racine.getNbEtoiles();
	}

	public int max(int a, int b) {
		return (a > b) ? a : b;
	}

	public void afficherScore() {
		int etoileBleue = 0;
		int etoileRouge = 0;

		for(int i = 0; i < taille_; ++i) {
			for(int j = 0; j < taille_; ++j) {
				if(plateau_[i][j].getCouleur() == 'B') 
					etoileBleue = max(etoileBleue, nombreEtoiles(plateau_[i][j]));
				else if(plateau_[i][j].getCouleur() == 'R')
					etoileRouge = max(etoileRouge, nombreEtoiles(plateau_[i][j]));
			}
		}

		if(etoileRouge > etoileBleue)
			System.out.println("Le joueur rouge est devant avec "+etoileRouge+" etoiles connectées contre "+etoileBleue+" etoiles pour bleu");
		else if(etoileBleue > etoileRouge)
			System.out.println("Le joueur bleu est devant avec "+etoileBleue+" etoiles connectées contre "+etoileRouge+" etoiles pour rouge");
		else
			System.out.println("Egalite avec "+etoileBleue+" etoiles pour chacun");
	}

	public boolean existeCheminCases(Case c1, Case c2) {
		boolean res = false;
		if( c1.getCouleur() == c2.getCouleur() ) {
			if( c1.getXParent() == c2.getXParent() && c1.getYParent() == c2.getYParent())
				res = true;
		}
		return res;
	}

	public boolean relieComposantes(char couleur, int i, int j) {
		ArrayList<Case> voisins = voisins(i,j);
		int nbVoisinsMemeCouleur = 0;

		for(Case v : voisins) {
			if( v.getCouleur() == couleur )
				++nbVoisinsMemeCouleur;
		}

		return (nbVoisinsMemeCouleur >= 2);
	}


	public Case getCase(int i, int j) { return plateau_[i][j]; }

	public void afficher() {

		System.out.println();
		for(int i = 0; i < taille_; ++i) {
			for(int j = 0; j < taille_; ++j) {
				if(plateau_[i][j].getCouleur() == 'B')
					System.out.print(BG_LIGHTBLUE);
				else if(plateau_[i][j].getCouleur() == 'R')
					System.out.print(BG_RED);
				System.out.print(plateau_[i][j].getTypeCase()+BG_DEFAULT+"|");
			}
			System.out.println();
		}
		System.out.println("\n");
	}

	public void afficheTab(int tab[][]){
		for(int i=0; i<taille_; ++i) {
			for(int j=0; j<taille_; ++j) {

				System.out.print( tab[i][j]+ "|");

			}
			System.out.println();
		}
		System.out.println("\n");
	}

	public void afficheTabCoord(Case tab[][]){
		for(int i=0; i<taille_; ++i) {
			for(int j=0; j<taille_; ++j) {

				System.out.print(tab[i][j].getX()+","+tab[i][j].getY()+ "|");

			}
			System.out.println();
		}
		System.out.println("\n");
	}

	public int getTaille() { return taille_; }


	/**
	 * Méthode publique de dessin de la grille_ dans la fenêtre graphique
	 */
	public void dessiner() {
		repaint();  // appel de paintComponent redéfinie ci-après
	}

	protected void paintComponent(Graphics g2d) {
		super.paintComponent(g2d);
		//Graphics g2d = (Graphics2D)g;
		// fond
		Color couleur = new Color(80, 80, 80);
		g2d.setColor(couleur);
		g2d.fillRect(0, 0, largeur_, hauteur_);
		// superposition des couleurs
		
		g2d.setXORMode(couleur);
		//grille_
		grille_.dessiner(g2d);
		grille_.setVisible(true);

		// parcours du tableau 2D
		for(int i = 0; i < taille_; i++){
			for(int j = 0; j < taille_; j++){
				if(plateau_[i][j] != null){
					//plateau_[i][j].dessiner(g2d);
				}
			}
		}
	}
}