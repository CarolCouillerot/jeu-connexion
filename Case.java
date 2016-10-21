
class Case {

	// Coordonnées de la case 
	private int i_;
	private int j_;

	// Coordonnées de la case parente
	private int ip_;
	private int jp_;

	/* " " : pas de pion
	   "o" : couleur 1
	   "x" : couleur 2
	 */
	private char couleur_;

	public Case(int i, int j, char c) {
		i_ = i;
		j_ = j;
		ip_ = i;
		jp_ = j;
		couleur_ = c;
	}


	public int getX() { return i_; }
	public int getY() { return j_; }

	public int getXParent() { return ip_; }
	public int getYParent() { return jp_; }

	public void setParent(int i, int j) {
		ip_ = i;
		jp_ = j;
	}

	public void setParent(Case c) {
		ip_ = c.getXParent();
		jp_ = c.getYParent();
	}

	public char getCouleur() { return couleur_; }
	public void setCouleur(char c) { couleur_ = c; }

}