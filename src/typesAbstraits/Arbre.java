package typesAbstraits;

/**
 * Classe permettant de mod�liser un arbre, c'est � dire un arbre binaire muni de diff�rents poids.
 * Elle ajoute �galement une fonction de comparaison aux arbres binaires.
 * @author Lucas
 *
 */
public class Arbre implements Comparable<Arbre> {

	/**
	 * L'arbre binaire contenu dans arbre
	 */
	private ArbreBinaire<Integer,Couple<Character,Integer>> arbre;
	
	/**
	 * Le poids total de l'arbre
	 */
	int poids;

	/**
	 * Constructeur permettant de construire un arbre de type 'feuille unique'
	 * @param freq la frequence d'apparition du caract�re de la feuille
	 * @param ch le caract�re de la feuille
	 */
	public Arbre(int freq, char ch) {
		this.arbre=new Feuille<Integer,Couple<Character,Integer>>(new Couple<Character,Integer>(ch,freq));
		this.poids=freq;
	}
	
	/**
	 * Constructeur permettant de construire un arbre � partir de deux sous-arbres binaire
	 * Ce constructeur n'est pas accessible � l'exterieur de la classe
	 * @param idNoeud l'identifiant du noeud
	 * @param gauche l'arbre binaire fils � gauche
	 * @param droite l'arbre binaire fils � droite
	 */
	private Arbre(Integer idNoeud, ArbreBinaire<Integer,Couple<Character,Integer>> gauche, ArbreBinaire<Integer,Couple<Character,Integer>> droite) {
		this.arbre=new Cons<Integer,Couple<Character,Integer>>(idNoeud, gauche, droite);
	}
	
	/**
	 * Constructeur permettant de construire un arbre � partir de deux sous-arbres
	 * @param idNoeud l'identifiant du noeud
	 * @param gauche l'arbre fils � gauche
	 * @param droite l'arbre fils � droite
	 */
	public Arbre(Integer  idNoeud, Arbre gauche, Arbre droite) {
		this(idNoeud,gauche.getArbreBinaire(),droite.getArbreBinaire());
		this.poids=gauche.getPoids()+droite.getPoids();
	}
	
	/**
	 * @return le poids totla de l'arbre
	 */
	public int getPoids() {
		return this.poids;
	}
	
	/**
	 * @return l'arbre binaire associ� � cet arbre
	 */
	public ArbreBinaire<Integer,Couple<Character,Integer>> getArbreBinaire() {
		return this.arbre;
	}

	/**
	 * M�thode comparant deux Arbres
	 * Deux arbres sont consid�r�s �gaux si :
	 * -> Les deux sont des feuilles de poids identiques et de caract�re identique, ou si deux noeud ont le m�me
	 * poids et le m�me identifient
	 * Un arbre est consider� sup�rieur � l'autre si :
	 * -> Dans la comparaiosn entre deux arbres si l'un des deux est nettement plus lourd, quelque soit le type des arbres
	 * -> Dans la comparaison entre un noeud et une feuille, si les deux ont un poids identiques, la feuille sera consider� plus l�g�re
	 * -> Dans la comparaison entre deux feuilles, le caract�re le plus avanc� dans la table ASCII sera consider� plus lourd, en cas d'�galit�
	 * -> Dans la comparaison entre deux noeuds, celui � l'identifiant le plus petit sera consider� le plus lourd en cas d'�galit� des poids
	 */
	public int compareTo(Arbre arg0) {
		/*
		 * Si diffrence de poids, comparaison triviale
		 */
		if(this.getPoids()>arg0.getPoids()) {
			return 1;
		}
		if(arg0.getPoids()>this.getPoids()) {
			return -1;
		}
		/*
		 * Sinon, �tablir un ordre tel que deux objets ne soient jamais �gaux si ils ne sont pas identiques
		 * Cet ordre est expliqu� plus haut
		 */
		if(this.getArbreBinaire().estFeuille() && arg0.getArbreBinaire().estFeuille()) {
			/*
			 * Le deux sont des feuilles, on les compare � partir de la table ASCII
			 */
			try {
				return this.getArbreBinaire().feuille().x.compareTo(arg0.getArbreBinaire().feuille().x);
			}
			/*
			 * Ici le block catch est formellemnt obligatoire pour la compilation mais le code d'ArbreBinaire
			 * empeche toute exception, �tant donn�s les v�rifications effectu�es au pr�alable
			 */
			catch (ExceptionTypeAbstrait e) {
				e.printStackTrace();
			}
		}
		if(!this.getArbreBinaire().estFeuille() && !arg0.getArbreBinaire().estFeuille()) {
			/*
			 * Si aucun des deux ne sont des feuilles (et donc les deux sont des noeuds), on les
			 * compare par leur identifiant unique
			 */
			try {
				return this.getArbreBinaire().noeud().compareTo(arg0.getArbreBinaire().noeud());
			}
			/*
			 * Ici le block catch est formellemnt obligatoire pour la compilation mais le code d'ArbreBinaire
			 * empeche toute exception, �tant donn�s les v�rifications effectu�es au pr�alable
			 */
			catch (ExceptionTypeAbstrait e) {
				e.printStackTrace();
			}
		}
		/*
		 * Si la fonction n'a toujours pas retourn� de valeur, l'Exception �tant a priori impossible, c'est
		 * que l'on doit comparer une feuille et un noeud.
		 * Dans ce cas la feuille ets consid�r�e plus l�g�re
		 */
		if(!this.getArbreBinaire().estFeuille()) {
			return 1;
		}
		else {
			return -1;
		}
	}

}
