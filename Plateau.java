import java.util.ArrayList;
import java.util.Random;

class Plateau {

	Case plateau_[][];
	int taille_;

	public static final String FG_DEFAULT = "\u001B[0m";
	public static final String BG_DEFAULT = "\u001B[49m";
	public static final String BG_LIGHTBLUE = "\u001B[104m";
	public static final String BG_LIGHTGREY = "\u001B[47m";

	public static final String BG_RED = "\u001B[41m";

	// Nombres de cases par joueur au départ
	public static final int K = 2;


	public Plateau(int taille) {


		taille_ = taille;
		plateau_ = new Case[taille][taille];
		for(int i = 0; i < taille; ++i) {
			for(int j = 0; j < taille; ++j) {

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


	public int nombreEtoiles(Case c) {
		Case racine = classe(c.getX(), c.getY());

		return racine.getNbEtoiles();
	}

	public void union(Case c1, Case c2) {
		c1 = classe(c1.getX(), c1.getY());
		c2 = classe(c2.getX(), c2.getY());

		if( taille(c1) < taille(c2)) {
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

	public boolean existeCheminCases(Case c1, Case c2) {
		boolean res = false;
		if( c1.getCouleur() == c2.getCouleur() ) {
			if( c1.getXParent() == c2.getXParent() && c1.getYParent() == c2.getYParent())
				res = true;
		}
		return res;
	}

/*
=======

>>>>>>> 4121d8201faef7893a5f3daaed080775e0ae82c7
	public boolean existeCheminCotes(char couleur) {
		ArrayList<Case> cote1 = new ArrayList<Case>();
		ArrayList<Case> cote2 = new ArrayList<Case>();

		if( couleur == 'x') {
			for(int j=0; j<taille_; ++j) {
				if(plateau_[0][j].getCouleur() == 'x')
					cote1.add(plateau_[0][j]);
				if(plateau_[taille_-1][j].getCouleur() == 'x')
					cote2.add(plateau_[taille_-1][j]);
			}
		}
		else {
			for(int i=0; i<taille_; ++i) {
				System.out.println(plateau_[i][0].getCouleur());
				if(plateau_[i][0].getCouleur() == 'o')
					cote1.add(plateau_[i][0]);
				if(plateau_[i][taille_-1].getCouleur() == 'o')
					cote2.add(plateau_[i][taille_-1]);
			}
		}

		boolean existeChemin = false;
		int i=0;
		if( cote1.size() > 0 && cote2.size() > 0) {
			while( i < cote1.size() && !existeChemin) {
				int j = 0;
				while( j < cote2.size() && !existeChemin) {
					existeChemin = existeCheminCases(cote1.get(i), cote2.get(j));
					++j;
				}
				++i;
			}
		}
		return existeChemin;
<<<<<<< HEAD
	}*/


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



	public int getTaille() { return taille_; }



	public void dijkstra(int xdep, int ydep, int xbut, int ybut) 
	{
		int poids[][] = new int[taille_][taille_];
		boolean dejaVisite[][] = new boolean[taille_][taille_];
		Case predecesseur[][] = new Case[taille_][taille_];

		initDijkstra(poids, dejaVisite, predecesseur, xdep, ydep);

		afficheTab(poids);

		Case courante;
		ArrayList<Case> voisins = new ArrayList<Case>();

		int j = 0;

		for (int k = 0; k < taille_; ++k)
		{
			for (int l = 0; l < taille_; ++l)
			{
				if(poids[k][l] == j)
				{
					voisins = voisins(k, l);
					courante = plateau_[k][l];
					for(Case v : voisins) 
					{
						setPoids(poids, dejaVisite, predecesseur, courante, v);
					}
				}
				++j;
			}
		}
		afficheTab(poids);
	}

	private void initDijkstra(int poids[][], boolean dejaVisite[][], Case predecesseur[][], int xdep, int ydep)
	{
		for (int i = 0; i < taille_; ++i)
		{
			for (int j = 0; j < taille_; ++j)
			{
				poids[i][j] = -1;	// distance a dep
				dejaVisite[i][j] = false;	// deja passé par cette case
				predecesseur[i][j] = null;	// tableau des prédécesseurs
			}
		}
		poids[xdep][ydep] = 0;
	}

	private void setPoids(int poids[][], boolean dejaVisite[][], Case predecesseur[][], Case courante, Case voisine)
	{	
		if ((poids[courante.getX()][courante.getY()] < poids[voisine.getX()][voisine.getY()] + 1) || !dejaVisite[voisine.getX()][voisine.getY()])
		{
			poids[voisine.getX()][voisine.getY()] = poids[courante.getX()][courante.getY()] + 1;
			predecesseur[voisine.getX()][voisine.getY()] = courante;
		}
	}
}