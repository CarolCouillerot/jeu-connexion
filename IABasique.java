import java.util.ArrayList;

class IABasique extends IA
{

	public IABasique(char c, Plateau p) 
	{
		col_ = c;
		colEnnemi_ = (c == 'R') ? 'B' : 'R';
		etoile_ = new ArrayList<Case>();
		for(int i = 0; i < p.getTaille(); ++i )
		{
			for(int j = 0; j < p.getTaille(); ++j) 
			{
				if(p.getCase(i,j).getCouleur() == col_ && p.getCase(i,j).getTypeCase() == '*')
					etoile_.add(p.getCase(i,j));
			}
		}

		chemin_ = new boolean[etoile_.size()][etoile_.size()];
		for(int i = 0; i < etoile_.size(); ++i) 
		{
			for(int j = 0; j < etoile_.size(); ++j)
				chemin_[i][j] = true;
		}
	}

	public Case evaluer(Plateau p) 
	{
		return null;
	}

	// Supprime de la liste des étoiles celles qui sont inaccessibles
	private void cheminvalide(Plateau p) {
		boolean cheminPossible = false;
		for(int i = 0; i < etoile_.size(); ++i) 
		{
			ArrayList<Case> voisins = p.voisins(etoile_.get(i).getX(), etoile_.get(i).getY());
			int j = 0;
			while(j < voisins.size() && !cheminPossible)
			{
				if(voisins.get(j).getCouleur() == 'V' )
					cheminPossible = true;
				++j;
			}

			if(!cheminPossible) {
				for(int k = 0; k < etoile_.size(); ++k) 
				{
					chemin_[i][k] = false;
					chemin_[k][i] = false;
				}
			}
		}

	}

	// Retourne dans un tableau, les coordonnées des deux étoiles à relier
	private int[] cheminEnCours(Plateau p, Dijsktra dijsktra) 
	{
		int coordChemin[] = new int[4];
		int distanceMin = Integer.MAX_VALUE;
		int dCourante;

		for(int i = 0; i < etoile_.size(); ++i) {
			for(int j = i+1; j < etoile_.size(); ++j) {
				dCourante = dijsktra.run(p, etoile_.get(i).getX(), etoile_.get(i).getY(), etoile_.get(j).getX(), etoile_.get(j).getY());
				if (distanceMin > dCourante)
				{	
					distanceMin = dCourante;
					coordChemin[0] = etoile_.get(i).getX();
					coordChemin[1] = etoile_.get(i).getY();
					coordChemin[2] = etoile_.get(j).getX();
					coordChemin[3] = etoile_.get(j).getY();
				}
			}
		}

		return coordChemin;
	}
	

	public int[] mettrePion(Plateau p, Dijsktra dijsktra) 
	{
		Case pion = evaluer(p);
		// Recherche du plus court chemin actuel
		// coordExtremiteChemin récupère les coordonnées des deux étoiles à relier
		int coordExtremiteChemin[] = cheminEnCours(p,dijsktra);
		int i = coordExtremiteChemin[2];
		int j = coordExtremiteChemin[3];
		int tmp;

		while(p.getCase(i,j).getCouleur() == col_) {
			tmp = i;
			i = dijsktra.predecesseur()[i][j].getX();
			j = dijsktra.predecesseur()[tmp][j].getY();
		}

		//p.ajoutePion(col_, i, j);
		int res[] = new int[2];
		res[0] = i;
		res[1] = j;
		
		return res;
	} 

}