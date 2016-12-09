import java.util.ArrayList;
import java.util.Scanner;

class Connexion 
{
	private int k_;
	private Plateau plat_;
	private int taille_;
	private Dijsktra dijkstra_;
	private IA ia_;
	private Case[] joueur_;
	private int dernierX_;
	private int dernierY_;
	private int max_;
	private char gagnant_;
	private int nbCasesVides_;

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public Connexion(int taillePlateau, int k) 
	{
		k_ = k;
		plat_ = new Plateau(taillePlateau, k_);
		taille_ = taillePlateau;
		dijkstra_ = new Dijsktra(taillePlateau);
		ia_ = new IABasique('R', plat_);
		joueur_ = plat_.getEtoile();
		max_ = 1;
		nbCasesVides_ = taille_*taille_ - 2*k_;
		gagnant_ = 'E'; // egalité
	}

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public static void main(String[] args) 
	{
		Scanner reader = new Scanner(System.in);
		System.out.println("Taille du plateau : ");
		int taille = reader.nextInt();

		System.out.println("Nombre d'étoiles souhaité : ");

		Connexion game = new Connexion(taille, reader.nextInt());

		char joueur[] = new char[2];
		joueur[0] = 'B';
		joueur[1] = 'R';

		game.menuMode(reader, joueur);
	}

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public void tourIA() 
	{
		int res[] = new int[2];

		res = ia_.mettrePion(plat_, dijkstra_);
		plat_.ajoutePion(ia_.color(), res[0], res[1]);
	}

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public Plateau getPlateau() { return plat_; }

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public void afficherPlateau() { plat_.afficher(); }

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/ 
	public void menuMode(Scanner reader, char [] j)
	{
		System.out.println("Quel type de partie souhaitez vous jouer ?");		
		System.out.println("1. Jouer conter un joueur");
		System.out.println("2. Jouer contre une IA");
		System.out.println("Votre choix : ");

		int choix = reader.nextInt();

		switch(choix) 
		{
			case 1: joueDeuxHumains(reader, j);
				break;
			case 2: joueOrdiHumain(reader, j);
				break;
			default: System.out.println("erreur.");
				menuMode(reader, j);
		}
	}

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public int menu(Scanner reader, char joueur) 
	{
		System.out.println("1. Ajouter un pion");
		System.out.println("2. Afficher composante");
		System.out.println("3. Tester l'existence de chemin");
		System.out.println("4. Distance minimale entre a et b");
		System.out.println("5. nb etoiles");
		System.out.println("6. Score des joueurs");
		System.out.println("7. Relie composante ?");
		System.out.println("8. EvaluerCase1 ?");
		System.out.println("9. Abandonner");
		System.out.println("Votre choix : ");

		int choix = reader.nextInt();

		switch(choix) 
		{
			case 1: if (!ajouterPion(reader, joueur)) choix = -1;
					else --nbCasesVides_;
				break;
			case 2: afficherComposante(reader, joueur);
				break;
			case 3: existeChemin(reader, joueur);
				break;
			case 4: distance(reader,joueur);
				break;
			case 5: nbEtoiles(reader,joueur);
				break;
			case 6: afficheScores();
				break;
			case 7: relieComposante(reader,joueur);
				break;
			case 8: evaluerCase1(reader,joueur);
				break;
			case 9: abandonner(joueur);
			default: System.out.println("erreur.");
				menu(reader, joueur);
		}

		return choix;
	}

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public boolean ajouterPion(Scanner reader, char joueur) 
	{
		System.out.print("La première saisie correspond aux lignes, la seconde aux colonnes.\n");		
		System.out.print("Entrez la coordonnée du pion à mettre : ");
		int x = reader.nextInt();
		int y = reader.nextInt();
		
		if (plat_.ajoutePion(joueur, x, y))
		{
			dernierX_ = x;
			dernierY_ = y;

			return true;
		}

		return false;
	}

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public void afficherComposante(Scanner reader, char joueur) 
	{
		System.out.print("La première saisie correspond aux lignes, la seconde aux colonnes.\n");				
		System.out.println("Afficher la composante de : ");
		int x = reader.nextInt();
		int y = reader.nextInt();
		plat_.afficheComposante(x,y);
	}

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public void existeChemin(Scanner reader, char joueur) 
	{
		System.out.print("La première saisie correspond aux lignes, la seconde aux colonnes.\n");		
		System.out.println("Existe chemin : entrer la coordonnées de la case de départ puis de la case d'arrivée.");
		int xdep = reader.nextInt();
		int ydep = reader.nextInt();
		int xbut = reader.nextInt();
		int ybut = reader.nextInt();

		if (plat_.existeCheminCases(plat_.getCase(xdep,ydep),plat_.getCase(xbut,ybut))) 
			System.out.println("Un chemin existe.");
		else
			System.out.println("Aucun chemin n'existe.");
	}

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public void distance(Scanner reader, char joueur) 
	{
		System.out.print("La première saisie correspond aux lignes, la seconde aux colonnes.\n");				
		System.out.println("Existe chemin : entrer la coordonnées de la case de départ puis de la case d'arrivée.");
		
		int x1 = reader.nextInt();
		int y1 = reader.nextInt();
		int x2 = reader.nextInt();
		int y2 = reader.nextInt();

		int min = dijkstra_.run(plat_, x1, y1, x2, y2);

		System.out.println("Nombres de coups min pour aller de ("+x1+","+y1+") en ("+x2+","+y2+") : " + min);
	}

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public void nbEtoiles(Scanner reader, char joueur) 
	{
		System.out.print("La première saisie correspond aux lignes, la seconde aux colonnes.\n");				
		System.out.println("Entrez la coordonnées de la case.");
		
		int x1 = reader.nextInt();
		int y1 = reader.nextInt();
		
		System.out.println("Nb étoiles : " + plat_.nombreEtoiles(plat_.getCase(x1,y1)));
	}

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public void afficheScores() { plat_.afficheScores(); }

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public void relieComposante(Scanner reader, char joueur) 
	{
		System.out.print("La première saisie correspond aux lignes, la seconde aux colonnes.\n");				
		System.out.println("Entrez la coordonnées de la case.");
		
		int x = reader.nextInt();
		int y = reader.nextInt();
		
		if (plat_.relieComposantes(joueur, x, y))
			System.out.println("L'ajout d'un pion en ("+x+","+y+") relie bien des composantes");
		else
			System.out.println("L'ajout d'un pion en ("+x+","+y+") ne relie pas de composantes");
	}

	/**
	 * @brief      
	 * @entrées   
	 * @sorties   
	**/  
	public void evaluerCase1(Scanner reader, char joueur)
	{
		System.out.print("La première saisie correspond aux lignes, la seconde aux colonnes.\n");				
		System.out.println("Entrez la coordonnées de la case.");
		
		int x = reader.nextInt();
		int y = reader.nextInt();
		plat_.colorerCase(x,y,joueur);
		
		int i = (joueur == 'B') ? k_ : 0;

		int u = 0;
		int v = 0;

		for (int j = i; j < i + k_; ++j)
		{	
			u += joueur_[j].getX();
			v += joueur_[j].getY();		
		}	

		u /= k_;
		v /= k_;
	
		int interet = dijkstra_.run(plat_, x, y, u, v);
		plat_.decoloration(x,y);
		
		System.out.println("Plus le nombre est grand, plus l'interêt est faible.\n Voici l'interêt de placer un pion en (" + x + "," + y + ") : " + interet);
	}


	public void joueDeuxHumains(Scanner reader, char [] j)
	{
		int numJoueur = 0;
		int menu;
		String couleur;
		boolean gameOver = false;

		while (!gameOver)
		{
			afficherPlateau();

			if (numJoueur == 0)
				couleur = "bleu";
			else
				couleur = "rouge";

			System.out.println("*********************************************************************");
			System.out.println("Vous êtes en " + couleur + ", c'est votre tour de jouer. \nQue souhaitez vous faire ?");			
			System.out.println("*********************************************************************");
			menu = menu(reader, j[numJoueur]);

			if (menu == 1)
			{
				if (isGameOver(j[numJoueur]))
					gameOver = true;
				else
					numJoueur = (numJoueur + 1) % 2;
			}	
		}
		afficherPlateau();
	}
	
	public void joueOrdiHumain(Scanner reader, char [] j)
	{
		int numJoueur = 0;
		int menu;
		boolean gameOver = false;

		while (!gameOver)
		{
			afficherPlateau();

			if (numJoueur == 0) 
			{

				System.out.println("*********************************************************************");
				System.out.println("Vous êtes en bleu, c'est votre tour de jouer. \nQue souhaitez vous faire ?");	
				System.out.println("*********************************************************************");
				
				menu = menu(reader, j[numJoueur]);
				
				if (menu == 1)
				{
					if (isGameOver(j[numJoueur]))
						gameOver = true;
					else
						numJoueur = (numJoueur + 1) % 2;
				}				
			}
			else 
			{
				tourIA();

				if (isGameOver(j[numJoueur]))
					gameOver = true;
				else
					numJoueur = (numJoueur + 1) % 2;
			}
		}
		afficherPlateau();
	}

	public void abandonner(char joueur) 
	{
		String perdant, gagnant;
		
		if (joueur == 'B')
		{
			perdant = "Bleu";
			gagnant = "Rouge";
		}
		else
		{
			perdant = "Rouge";
			gagnant = "Bleu";
		}

		System.out.println("\n\n*****************************************************");
		System.out.println("Le joueur " + perdant + " abandonne. Le joueur " + gagnant + " gagne");
		System.out.println("******************************************************");
	}

	public boolean isGameOver(char joueur) 
	{
		boolean over = false;

		if (plat_.relieComposantes(joueur, dernierX_, dernierY_))
		{
			if (plat_.nombreEtoiles(plat_.getCase(dernierX_, dernierY_)) > max_) 
			{
				max_ = plat_.nombreEtoiles(plat_.getCase(dernierX_, dernierY_));
				gagnant_ = joueur;
			}

			if (plat_.nombreEtoiles(plat_.getCase(dernierX_, dernierY_)) == k_)
			{
				gagner(joueur);
				over = true;
			}
		}
		if (nbCasesVides_ <= 0) 
		{
			gagner(gagnant_);
			over = true;
		}

		return over;
	}

	public void gagner(char joueur) 
	{
		String perdant, gagnant;

		if (joueur == 'R')
		{
			perdant = "Bleu";
			gagnant = "Rouge";
		}
		else
		{
			perdant = "Rouge";
			gagnant = "Bleu";
		}
			
		if (joueur == 'E') 
		{
			System.out.println("\n\n*****************************************************");
			System.out.println("Le joueur " + gagnant + " gagne la partie en reliant toutes ses étoiles.");
			System.out.println("******************************************************");
		}
		else
		{
			System.out.println("\n\n*****************************************************");
			System.out.println("Les joueur sont à égalité.");
			System.out.println("******************************************************");
		}
	}
}