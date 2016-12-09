class Case 
{

	// Coordonnées de la case
	private int i_;
	private int j_;

	// Coordonnées de la case parente
	private int ip_;
	private int jp_;

	/* "v" : pas de pion
	   "R" : couleur 1
	   "B" : couleur 2
	 */
	private char couleur_;

	/* " " : case quelconque
	   "*" : case de départ
	 */
	private char typeCase_;

	// Donne le nombre d'étoiles en local, seule la racine
	// contient le bon nombre d'étoiles dans la composante
	private int etoile_;

	// Stocke la hauteur de la composante dans laquelle est cette case
	// Seule la racine contient de la composante contient la bonne hauteur
	private int hauteur_;

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public Case(int i, int j, char col, char type, int etoiles) 
	{
		i_ = i;
		j_ = j;
		ip_ = i;
		jp_ = j;
		couleur_ = col;
		typeCase_ = type;
		etoile_ = etoiles;
		hauteur_ = 0;
	}

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  	
	public Case() {}

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public int getX() { return i_; }
	
	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public int getY() { return j_; }

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public int getXParent() { return ip_; }
	
	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public int getYParent() { return jp_; }

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public void setParent(int i, int j) 
	{
		ip_ = i;
		jp_ = j;
	}

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public void setParent(Case c) 
	{
		ip_ = c.getXParent();
		jp_ = c.getYParent();
	}

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public char getCouleur() { return couleur_; }
	
	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  	
	public void setCouleur(char c) { couleur_ = c; }

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public char getTypeCase() { return typeCase_; }
	
	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public void setTypeCase(char type) { typeCase_ = type; }

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public int getNbEtoiles() { return etoile_; }
	
	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public void addEtoiles(int n) { etoile_ += n; }

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/
	public int getHauteur() { return hauteur_; }

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/
	public void addHauteur(int h) { hauteur_ += h; }

}