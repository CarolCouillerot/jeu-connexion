import java.util.ArrayList;

abstract class IA 
{

	public abstract Case evaluer(Plateau p);
	public abstract int[] mettrePion(Plateau p, Dijsktra dijsktra);
	public char color() { return col_; }

	protected char col_;
	protected char colEnnemi_;
	protected ArrayList<Case> etoile_;
	protected boolean chemin_[][];
}