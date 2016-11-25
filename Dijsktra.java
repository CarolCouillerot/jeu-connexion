

class Dijsktra {

	public Case predecesseur_[][];
	private int poids_[][];
	private boolean dejaVisite_[][];
	private int min_;
	private int taille_;

	public Dijsktra(int taille) {
		predecesseur_ = new Case[taille][taille];
		poids_ = new int[taille][taille];
		dejaVisite_ = new boolean[taille][taille];
		taille_ = taille;
	}

	public Case[][] predecesseur() { return predecesseur_; }

	public int run(Plateau p, int xdep, int ydep, int xbut, int ybut) {
		init(p, xdep, ydep);

		char coulObstacle = ( p.getCase(xdep,ydep).getCouleur() == 'R') ? 'B' : 'R';
		
		boolean tousVisite = false;
		Case courante = new Case();
		
		while(!tousVisite) {
			courante = trouverMin(p);
			dejaVisite_[courante.getX()][courante.getY()] = true;
			for(Case v : p.voisins(courante.getX(), courante.getY())) {
				if(v.getCouleur() != coulObstacle)
					setDistance(p, courante, v);
			}	
			tousVisite = true;
			for(int i = 0; i < taille_; ++i) {
				for(int j = 0; j < taille_; ++j) {
					if(!dejaVisite_[i][j]) tousVisite = false;
				}
			}
		}

		p.afficheTabCoord(predecesseur_);
		int i = xbut;
		int j = ybut;
		int cptCol = 0;
		char colDep = p.getCase(xdep,ydep).getCouleur();

		courante = p.getCase(i,j);

		while ((i !=xdep) || (j != ydep))
		{

			if(courante.getCouleur() == colDep)
			{
				++cptCol;
			}

			courante = predecesseur_[i][j];
			i = courante.getX();
			j = courante.getY();
			System.out.println(i+","+j);

		}
		return poids_[xbut][ybut] - cptCol;
	}


	private void init(Plateau p, int xdep, int ydep)
	{
		char col = p.getCase(xdep, ydep).getCouleur();
		for (int i = 0; i < taille_; ++i)
		{
			for (int j = 0; j < taille_; ++j) {
				poids_[i][j] = Integer.MAX_VALUE; // distance a dep
				if(p.getCase(i,j).getCouleur() != col && p.getCase(i,j).getCouleur() != 'V') {
					dejaVisite_[i][j] = true;
					poids_[i][j] = -1;
					predecesseur_[i][j] = p.getCase(i,j);
				}
				else {
					dejaVisite_[i][j] = false;
					predecesseur_[i][j] = null;
				}
			}

		}
		predecesseur_[xdep][ydep] = p.getCase(xdep, ydep);
		poids_[xdep][ydep] = 0;
	}

	private Case trouverMin(Plateau p) {
		min_ = Integer.MAX_VALUE;
		Case sommet = new Case(-1,-1,' ',' ',0);

		for(int i = 0; i < taille_; ++i) { 
			for(int j = 0; j < taille_; ++j) {
				if(!dejaVisite_[i][j] && poids_[i][j] < min_) {
					min_ = poids_[i][j];
					sommet = p.getCase(i,j);
				}
			}
		}

		return sommet;
	}

	private void setDistance(Plateau p, Case c1, Case c2) {
		int x1 = c1.getX(), y1 = c1.getY();
		int x2 = c2.getX(), y2 = c2.getY();
		if(poids_[x2][y2] > poids_[x1][y1] + 1 ) {
			poids_[x2][y2] = poids_[x1][y1] + 1;
			predecesseur_[x2][y2] = p.getCase(x1,y1);
		}
	}

}