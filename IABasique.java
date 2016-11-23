
class IABasique implements IA
{

	public IABasique(char c, Plateau p) 
	{
		col_ = c;
		colEnnemi_ = (c == 'R') ? 'B' : 'R';
		for(int i = 0; i < p.getTaille(); ++i )
		{
			for(int j = 0; j < p.getTaille(); ++j) 
			{
				if(p.getCase(i,j).getCouleur() == col_ && p.getCase(i,j).getTypeCase() == '*')
					casesEtoiles.add(p.getCase(i,j));
			}
		}

		chemin = new int[casesEtoiles.size()][casesEtoiles.size()];
		for(int i = 0; i < casesEtoiles.size(); ++i) 
		{
			for(int j = 0; j < casesEtoiles.size(); ++j)
				chemin[i][j] = true;
		}
	}

	public Case evaluer(Plateau p) 
	{

	}

	private void cheminvalide(Plateau p) {
		boolean cheminPossible = false;
		for(int i = 0; i < casesEtoiles.size(); ++i) 
		{
			ArrayList<Case> voisins = p.voisins(casesEtoiles[i].getX(), casesEtoiles[i].getY());
			int j = 0;
			while(j < voisins.size() && !cheminPossible)
			{
				if(voisins[j].getCouleur() == 'V' )
					cheminPossible = true;
				++j;
			}

			if(!cheminPossible) {
				for(int k = 0; k < casesEtoiles.size(); ++k) 
				{
					chemin[i][k] = false;
					chemin[k][i] = false;
				}
			}
		}

	}

	private int[] cheminEnCours(Plateau p) 
	{
		int coordChemin[] = new int[4];
		int distanceMin = Integer.MAX_VALUE;
		int dCourante[] = new int[3];
		//int k, l;
		for(int i = 0; i < casesEtoiles.size(); ++i) {
			for(int j = i+1; j < casesEtoiles.size(); ++j) {

				if(chemin[i][j]) 
				{
					dCourante = p.dijkstra(casesEtoiles[i].getX(), casesEtoiles[i].getY(), casesEtoiles[j].getX(), casesEtoiles[j].getY());
					if (distanceMin > dCourante[0])
					{	
						//k = i;
						//l = j;
						distanceMin = dCourante[0];
						coordChemin[0] = casesEtoiles[i].getX();
						coordChemin[1] = casesEtoiles[i].getY();
						coordChemin[2] = casesEtoiles[j].getX();
						coordChemin[3] = casesEtoiles[j].getY();
					}
				}
			}
		}
		//chemin[k][l] = false;
		//chemin[l][k] = false;

		return coordChemin;
	}

	public void mettrePion(Plateau p) 
	{
		Case pion = evaluer(p);
		ajoutePion(col_, p.getX(), p.getY());
	} 

}