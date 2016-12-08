import java.util.ArrayList;

abstract class IA 
{
	protected char col_;
	protected char colEnnemi_;
	protected ArrayList<Case> etoile_;
	protected boolean chemin_[][];

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public abstract Case evaluer(Plateau p);

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  	
	public abstract int[] mettrePion(Plateau p, Dijsktra dijsktra);
	
	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public char color() { return col_; }
}