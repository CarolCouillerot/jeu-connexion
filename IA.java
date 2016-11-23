abstract class IA 
{

	public Case evaluer(Plateau p);
	public void mettrePion(Plateau p);

	private char col_;
	private char colEnnemi_;
	private ArrayList<Case> casesEtoiles_;
	private boolean chemin_[][];
}