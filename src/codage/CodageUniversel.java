package codage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import typesAbstraits.Arbre;
import typesAbstraits.ArbreBinaire;
import typesAbstraits.Couple;
import typesAbstraits.ExceptionTypeAbstrait;
import entreesSorties.SortieBinaire;
import entreesSorties.SortieBinaireStream;
import entreesSorties.SortieBinaireString;


/**
 * Classe implémentant un codage universel,
 * construisant l'arbre binaire de Huffman à partir de l'analyse frequentielle
 * effectuée sur la langue francaise en general.
 * @author Lucas
 *
 */
public class CodageUniversel {

	/**
	 * La table de conversion caractère -> Code binaire sous forme de String le modélisant
	 */
	public static HashMap<Character, String> tab;

	/**
	 * Les caractères présents dans le texte, ordonnés
	 */
	private TreeSet<Arbre> freqOrd;

	/**
	 * Le resultat encodé sous forme de String
	 */
	private String res;

	/**
	 * Le texte de départ
	 */
	private String txt;

	/**
	 * Les caractères présents dans le texte et leur fréquence
	 */
	private static HashMap<Character, Integer> freq;

	/**
	 * Constructeur permettant de lancer le codage d'une String et de le transcirre sur l'Outputstream passé en paramètre
	 * @param s la String à encoder
	 * @param out le Stream sur lequel écrire en sortie
	 * @throws IOException si il y a une erreur d'écriture sur le stream fourni
	 * @throws ExceptionCodageImpossible si le texte encodé est plus long que le texte source
	 */
	public CodageUniversel(String s,OutputStream out) throws IOException,ExceptionCodageImpossible {
		this.txt=s;
		Boolean[] aEcrire = this.coder();
		SortieBinaire sortie = new SortieBinaireStream(out);
		sortie.ecrire(aEcrire);
		sortie.complÈter();
		out.flush();
		out.close();
	}

	/**
	 * Constructeur permettant de lancer le codage d'une source lue via un InutStream et de le transcrire sur un OutputStream donné
	 * @param in l'InputStream à lire
	 * @param out l'OutputStream sur lequel encoder
	 * @throws IOException  si il y a une erreur de lecture ou d'écriture sur le stream fourni
	 * @throws ExceptionCodageImpossible si le texte encodé est plus long que le texte source
	 */
	public CodageUniversel(InputStream in, OutputStream out) throws IOException,ExceptionCodageImpossible {
		this.txt="";
		Boolean[] aEcrire = this.coder();
		SortieBinaire sortie = new SortieBinaireStream(out);
		sortie.ecrire(aEcrire);
		sortie.complÈter();
		out.flush();
		out.close();
		in.close();
	}

	/**
	 * Constructeur permettant de lancer le codage d'une source lue via un InutStream et de le transcrire dans une String retournée
	 * @param in l'InputStream à lire
	 * @throws IOException  si il y a une erreur de lecture ou d'écriture sur le stream fourni
	 * @throws ExceptionCodageImpossible si le texte encodé est plus long que le texte source
	 */
	public CodageUniversel(InputStream in) throws IOException,ExceptionCodageImpossible {
		this.txt="";
		this.coder();
		in.close();
	}

	/**
	 * Constructeur permettant de lancer le codage d'une source lue dans une String et de le transcrire dans une String retournée
	 * @param s la String à encoder
	 * @throws ExceptionCodageImpossible si le texte encodé est plus long que le texte source
	 */
	public CodageUniversel(String s) throws ExceptionCodageImpossible {
		this.txt=s;
		this.coder();
	}

	/**
	 * Fonction permettant d'encoder, une fois la lecture réalisée
	 * @throws ExceptionCodageImpossible si le codage ne fait pas gagner de place
	 */
	private Boolean[] coder() throws ExceptionCodageImpossible {
		this.res="";
		this.init();
		if(this.txt.equals("")) {
			throw new ExceptionCodageImpossible("Chaîne vide !");
		}
		this.ordonnerListe();
		this.construireArbre();
		tab=new HashMap<Character,String>();
		if(!freqOrd.first().getArbreBinaire().estFeuille()) {
			this.parcourirArbreEtEtablirTableDeConversion(this.freqOrd.first().getArbreBinaire(), "");
		}
		/*
		 * Si il n'y a qu'un seul caractère dans le texte, on l'encode par '0'
		 */
		else {
			try {
				tab.put(this.freqOrd.first().getArbreBinaire().feuille().x, "0");
			}
			catch (ExceptionTypeAbstrait e) {
				e.printStackTrace();
			}
		}
		Boolean[] tab=this.compresserEtEcrireDansRes();

		/*
		 * Si on a pas gagné de place, lève une exception
		 */
		if(this.txt.length()<=this.res.length()) {
			throw new ExceptionCodageImpossible("Le codage ne fait pas gagner de place !");
		}

		return tab;
	}



	private void init() {

	}

	/**
	 * Fonction permettant d'écrire le texte codé dans une String mémorisée
	 * @return le tableau de booleen modélisant le texte, qu'on réutilisera si besoin
	 * @throws ExceptionCodageImpossible 
	 */
	private Boolean[] compresserEtEcrireDansRes() throws ExceptionCodageImpossible {
		ArrayList<Boolean> aEcrire=new ArrayList<Boolean>();
		/*
		 * On parcourt le texte à encoder
		 */
		for(int i=0;i<this.txt.length();i++) {
			String seq=tab.get(this.txt.charAt(i));
			if(seq==null) {
				throw new ExceptionCodageImpossible("Caractère inconnu/non présent dans les textes de la lanque francaise fournis : "+this.txt.charAt(i));
			}
			/*
			 * On rajoute les bits à encoder un par un dans l'arraylist
			 */
			for(int j=0;j<seq.length();j++) {
				int temp=Integer.parseInt(""+seq.charAt(j));
				if(temp==1) {
					aEcrire.add(true);
				}
				else {
					aEcrire.add(false);
				}
			}
		}
		/*
		 * On cast l'arraylist en tableau, puis on écrit
		 */
		Boolean[] aEcrireTab=aEcrire.toArray(new Boolean[0]);
		this.res+=SortieBinaireString.creerString(aEcrireTab);
		return aEcrireTab;
	}

	/**
	 * Méthode permettant de reporter les données récoltés lors du parcours du texte dans une liste ordonnée
	 * automatiquement.
	 * On crée une 'feuille' par caractère, et on l'ajoute à la liste ordonnée
	 */
	private void ordonnerListe() {
		this.freqOrd=new TreeSet<Arbre>();
		Set<Character> keys=freq.keySet();
		for(Character c:keys) {
			int val=freq.get(c);
			this.freqOrd.add(new Arbre(val,c));
		}
	}

	/**
	 * Méthode permettant, à partir d'une liste ordonnée contenant l'ensemble des feuilles, de construire
	 * un arbre de Huffman.
	 * Cette méthode construit un noeud avec les deux premiers éléments de la liste systématiquement, jusqu'à ce qu'il n'y en ait plus qu'un.
	 * La branche de gauche est systématiquement la plus légère.
	 * Une fois la fonction exécutée, il ne restera qu'un arbre dans la liste ordonnée en attribut.
	 * Cette arbre sera l'arbre de Huffman
	 * 
	 */
	private void construireArbre() {
		Arbre g;
		Arbre d;
		int i=0;
		while(this.freqOrd.size()!=1) {
			g=this.freqOrd.pollFirst();
			d=this.freqOrd.pollFirst();
			this.freqOrd.add(new Arbre(i,g,d));
			i++;
		}
	}

	/**
	 * Parcourt l'arbre et remplit le tableau de correspondance caractère <--> string qui
	 * donne la modélisation binaire d'un caractère
	 * Cette méthode ne DOIT PAS être appelée si l'arbre 'principal' ou de Huffman est une FEUILLE,
	 * auquel cas le caractère unique sera modélisé par une String vide !
	 * @param adresse l'adresse de l'arbre au sein de l'arbre principal
	 * @param arbre l'arbre ou le sous-arbre à parcourir
	 */
	private void parcourirArbreEtEtablirTableDeConversion(ArbreBinaire<Integer,Couple<Character,Integer>> arbre, String adresse) {
		/*
		 * Si on a atteint une feuille, on note son adresse.
		 */
		if(arbre.estFeuille()) {
			try {
				tab.put(arbre.feuille().x, adresse);
			}
			/*
			 * Ici, aucun risque d'exception, mais try/catch necessaire pour la compilation...
			 */
			catch (ExceptionTypeAbstrait e) {
				e.printStackTrace();
			}
		}
		/*
		 * Sinon on est sur un noeud, donc on descend d'un niveau de profondeur
		 */
		else {
			try {
				this.parcourirArbreEtEtablirTableDeConversion(arbre.gauche(), adresse+"0");
				this.parcourirArbreEtEtablirTableDeConversion(arbre.droit(), adresse+"1");
			}
			/*
			 *Nécessité de la compilation, encore une fois. 
			 */
			catch (ExceptionTypeAbstrait e) {
				e.printStackTrace();
			}

		}
	}
	
	/**
	 * Fonction permettant d'ajouter un long texte au corpus de texte étudié pour l'analyse frequentielle
	 * @param texte un texte de la langue francaise
	 */
	public static void ajouterTexteString(String texte) {
		if(freq==null) {
			freq=new HashMap<Character,Integer>();
		}
		for(int i=0;i<texte.length();i++) {
			Character c=texte.charAt(i);
			analyseFrequentielleParChar(c);
		}
	}
	
	/**
	 * Fonction permettant d'ajouter un long texte au corpus de texte étudié pour l'analyse frequentielle sous forme de Stream
	 * @param s le Stream à utiliser pour la lecture
	 * @throws IOException si il y a une erreur de lecture sur l'inputstream fourni
	 */
	public static void ajouterTexteStream(InputStream s) throws IOException {
		if(freq==null) {
			freq=new HashMap<Character,Integer>();
		}
		Character c = null;
		int i=-1;
		i=s.read();
		c = (char)i;
		while(i!=-1) {
			analyseFrequentielleParChar(c);
			i=s.read();
			c = (char)i;
		}
	}
	
	/**
	 * Ajoute un caractère de plus pour l'analyse fréquentielle
	 * @param c le caractère suplémentaire  considérer
	 */
	private static void analyseFrequentielleParChar(Character c) {
		if(freq.containsKey(c)) {
			freq.put(c, freq.get(c)+1);
		}
		else {
			freq.put(c, 1);
		}
	}


	/**
	 * @return le résultat de l'encodage sous forme de String
	 */
	public String getStringResult() {
		return this.res;
	}
}
