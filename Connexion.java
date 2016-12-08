import java.util.ArrayList;
import java.util.Scanner;

class Connexion {

	private int k_;
	private Plateau plat_;
	private int taille_;
	private Dijsktra dijkstra_;
	private IA ia_;
	private Case[] joueur_;

	public Connexion(int taillePlateau, int k) 
	{
		k_ = k;
		plat_ = new Plateau(taillePlateau, k_);
		taille_ = taillePlateau;
		dijkstra_ = new Dijsktra(taillePlateau);
		ia_ = new IABasique('R', plat_);
		joueur_ = plat_.getEtoile();
	}

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
		int numJoueur = 0;

		game.afficherPlateau();

		for(int i =0; i < 20; ++i) {
			//if(numJoueur == 0) {
				game.menu(reader, joueur[numJoueur]);
			//}
			//else {
				//game.tourIA();
			//}
			game.afficherPlateau();
			numJoueur = (numJoueur+1)%2;
		}
	}

	public void tourIA() {
		int res[] = new int[2];

		res = ia_.mettrePion(plat_, dijkstra_);
		plat_.ajoutePion(ia_.color(), res[0], res[1]);
	}

	public Plateau getPlateau() { return plat_; }

	public void afficherPlateau() { plat_.afficher(); }

	public void menu(Scanner reader, char joueur) {
		System.out.println("1. Ajouter un pion");
		System.out.println("2. Afficher composante");
		System.out.println("3. Tester l'existence de chemin");
		System.out.println("4. Distance minimale entre a et b");
		System.out.println("5. nb etoiles");
		System.out.println("6. Score des joueurs");
		System.out.println("7. Relie composante ?");
		System.out.println("8. EvaluerCase1 ?");
		System.out.println("Votre choix : ");

		int choix = reader.nextInt();

		switch(choix) {
			case 1: ajouterPion(reader, joueur);
				break;
			case 2: afficherComposante(reader, joueur);
				break;
			case 3: existeChemin(reader, joueur);
				break;
			case 4: distance(reader,joueur);
				break;
			case 5: nbEtoiles(reader,joueur);
				break;
			case 6: afficherScores();
				break;
			case 7: relieComposante(reader,joueur);
				break;
			case 8: evaluerCase1(reader,joueur);
				break;
			default: System.out.println("erreur.");
							menu(reader, joueur);
		}
	}

	public void ajouterPion(Scanner reader, char joueur) {
		System.out.print("Entrez coord du pion à mettre : ");
		int x = reader.nextInt();
		int y = reader.nextInt();
		plat_.ajoutePion(joueur,x,y);
	}

	public void afficherComposante(Scanner reader, char joueur) {
		System.out.println("Afficher composante de : ");
		int x = reader.nextInt();
		int y = reader.nextInt();
		plat_.afficheComposante(x,y);
	}

	public void existeChemin(Scanner reader, char joueur) {
		System.out.println("Existe chemin : entrer coord case dep puis case d'arrivee");
		int xdep = reader.nextInt();
		int ydep = reader.nextInt();
		int xbut = reader.nextInt();
		int ybut = reader.nextInt();

		if(plat_.existeCheminCases(plat_.getCase(xdep,ydep),plat_.getCase(xbut,ybut))) {
			System.out.println("Chemin existe");
		}

	}

	public void distance(Scanner reader, char joueur) {
		System.out.println("Existe chemin : entrer coord case dep puis case d'arrivee");
		int x1 = reader.nextInt();
		int y1 = reader.nextInt();
		int x2 = reader.nextInt();
		int y2 = reader.nextInt();
		//plat_.calculeDistance(x1,y1,x2,y2);
		dijkstra_.run(plat_, x1, y1, x2, y2);
		//int min[] = new int[3];
		//min = plat_.dijkstra(x1, y1, x2, y2);
		//System.out.println("Nombres de coups min pour aller de ("+x1+","+y1+") en ("+x2+","+y2+") : " + min[0]);
	}

	public void nbEtoiles(Scanner reader, char joueur) {
		System.out.println("Entrer coord case ");
		int x1 = reader.nextInt();
		int y1 = reader.nextInt();
		System.out.println("Nb étoiles : " + plat_.nombreEtoiles(plat_.getCase(x1,y1)));
	}

	public void afficherScores() {
		plat_.afficherScore();
	}

	public void relieComposante(Scanner reader, char joueur) {
		System.out.println("Entrer coord case ");
		int x = reader.nextInt();
		int y = reader.nextInt();
		if(plat_.relieComposantes(joueur, x, y))
			System.out.println("L'ajout d'un pion en ("+x+","+y+") relie bien des composantes");
		else
			System.out.println("L'ajout d'un pion en ("+x+","+y+") ne relie pas de composantes");
	}

	public void evaluerCase1(Scanner reader, char joueur)
	{
		System.out.println("Entrer coord case ");
		int x = reader.nextInt();
		int y = reader.nextInt();
		plat_.colorerCase(x,y,joueur);
		
		System.out.println("joueuer[5] " + joueur_[5].getX());

		int i = (joueur == 'B') ? k_ : 0;

		int u = 0;
		int v = 0;

		System.out.println("i+k : " + (i+k_));

		for (int j = i; j < i + k_; ++j)
		{	
			System.out.println(j);
			u += joueur_[j].getX();
			v += joueur_[j].getY();		
			System.out.println("u: "+u+ ", v: "+v);
		}	
		u /= k_;
		v /= k_;
	
		System.out.println("centre : " + u + "," + v);


		int interet = dijkstra_.run(plat_, x, y, u, v);
		plat_.decoloration(x,y);
		
		System.out.println("Plus le nombre est grand, plus l'interêt est faible.\n Voici l'interêt de placer un pion en (" + x + "," + y + ") : " + interet);
	}

}
