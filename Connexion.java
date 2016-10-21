import java.util.ArrayList;
import java.util.Scanner;

class Connexion {

	private Plateau plat_;
	private int taille_;

	public Connexion(int taillePlateau) {
		plat_ = new Plateau(taillePlateau,2);
		taille_ = taillePlateau;
	}

	public static void main(String[] args) {
		
		Scanner reader = new Scanner(System.in);
		System.out.println("Taille du plateau : ");

		Connexion game = new Connexion(reader.nextInt());

		game.afficherPlateau();
		for(int i =0; i < 4; ++i) {
			menu();
			game.afficherPlateau();
		}

		
		/*
		for(int i=0; i<6; ++i) {
			game.getPlateau().ajoutePion('x',i,3);
		}
		game.getPlateau().afficher();
		game.getPlateau().afficheComposante(0,3);
		
		System.out.println("chemin cote ?"+game.getPlateau().existeCheminCotes('x'));

		System.out.println("Pion (2,4) relie composante ?"+game.getPlateau().relieComposantes('x',2,4));
		System.out.println("nb de pions entre (2,1) et (1,4): "+game.getPlateau().calculeDistance(1,1,5,2));
		*/
		
	}

	public Plateau getPlateau() { return plat_; }

	public void afficherPlateau() { plat_.afficher(); }

	public void menu(Scanner reader) {
		System.out.println("1. Ajouter un pion");
		System.out.println("2. Afficher composante");
		System.out.println("1. Tester l'existence de chemin");
		System.out.println("Votre choix : ");

		int choix = reader.nextInt();

		switch(choix) {
			case 1: ajouterPion();
				break;
			case 2: afficherComposante();
				break;
			case 3: existeChemin();
				break;
			case default: System.out.println("erreur.")
							menu(); 
		}
	}

	public void ajouterPion() {
		System.out.print("Entrez coord du pion à mettre : ");
		int x = reader.nextInt();
		int y = reader.nextInt();
		game.plat_.ajoutePion('0',x,y);
	}

	public void afficherComposante() {
		System.out.println("Afficher composante de : ");
		int x = reader.nextInt();
		int y = reader.nextInt();
		game.plat_.afficheComposante(x,y);
	}

	public void existeChemin() {
		System.out.println("Existe chemin : entrer coord case dep puis case d'arrivee");
		int xdep = reader.nextInt();
		int ydep = reader.nextInt();
		int xbut = reader.nextInt();
		int ybut = reader.nextInt();
		game.plat_.existeChemin(game.plat_[xdep][ydep],game.plat_[xbut][ybut]);
	}
}