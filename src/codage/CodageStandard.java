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
 * Classe implémentant un codage standard,
 * construisant l'arbre binaire de Huffman à partir de l'analyse frequentielle
 * effectuée sur le texte
 * Ici, je me suis trouvé face à un problème algorithmique qui m'a forcé à créer deux listes, l'une ordonnée,
 * l'autre non.
 * @author Lucas
 *
 */
public class CodageStandard {


	/**
	 * Les caractères présents dans le texte, ordonnés
	 */
	private TreeSet<Arbre> freqOrd;

	/**
	 * Les caractères présents dans le texte et leur fréquence
	 */
	private HashMap<Character, Integer> freq;

	/**
	 * La table de conversion caractère -> Code binaire sous forme de String le modélisant
	 */
	private HashMap<Character, String> tab;

	/**
	 * Le resultat encodé sous forme de String
	 */
	private String res;

	/**
	 * Le texte de départ
	 */
	private String txt;

	/**
	 * Modèle du tableau de conversion sous forme de String
	 */
	private String tabConv;

	public CodageStandard() {
		this.freqOrd=new TreeSet<Arbre>();
		this.freq=new HashMap<Character,Integer>();
		this.tab=new HashMap<Character,String>();
		this.res="";
	}

	/**
	 * Constructeur permettant de lancer le codage d'une String et de le transcirre sur l'Outputstream passé en paramètre
	 * @param s la String à encoder
	 * @param out le Stream sur lequel écrire en sortie
	 * @throws IOException si il y a une erreur d'écriture sur le stream fourni
	 * @throws ExceptionCodageImpossible si le texte encodé est plus long que le texte source
	 */
	public CodageStandard(String s,OutputStream out) throws IOException,ExceptionCodageImpossible {
		this();
		this.txt=s;
		this.analyseFrequentielleParString(s);
		Boolean[] aEcrire = this.coder();
		for(int i=0;i<this.tabConv.length();i++) {
			out.write(this.tabConv.charAt(i));
		}	
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
	public CodageStandard(InputStream in, OutputStream out) throws IOException,ExceptionCodageImpossible {
		this();
		this.txt="";
		this.analyseFrequentielleParStream(in);
		Boolean[] aEcrire = this.coder();
		for(int i=0;i<this.tabConv.length();i++) {
			out.write(this.tabConv.charAt(i));
		}	
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
	public CodageStandard(InputStream in) throws IOException,ExceptionCodageImpossible {
		this();
		this.txt="";
		this.analyseFrequentielleParStream(in);
		this.coder();
		in.close();
	}

	/**
	 * Constructeur permettant de lancer le codage d'une source lue dans une String et de le transcrire dans une String retournée
	 * @param s la String à encoder
	 * @throws ExceptionCodageImpossible si le texte encodé est plus long que le texte source
	 */
	public CodageStandard(String s) throws ExceptionCodageImpossible {
		this();
		this.txt=s;
		this.analyseFrequentielleParString(s);
		this.coder();
	}

	/**
	 * Fonction permettant d'encoder, une fois la lecture réalisée
	 * @throws ExceptionCodageImpossible si le codage ne fait pas gagner de place
	 */
	private Boolean[] coder() throws ExceptionCodageImpossible {
		if(this.txt.equals("")) {
			throw new ExceptionCodageImpossible("Chaîne vide !");
		}
		this.ordonnerListe();
		this.construireArbre();
		if(!freqOrd.first().getArbreBinaire().estFeuille()) {
			this.parcourirArbreEtEtablirTableDeConversion(this.freqOrd.first().getArbreBinaire(), "");
		}
		/*
		 * Si il n'y a qu'un seul caractère dans le texte, on l'encode par '0'
		 */
		else {
			try {
				this.tab.put(this.freqOrd.first().getArbreBinaire().feuille().x, "0");
			}
			catch (ExceptionTypeAbstrait e) {
				e.printStackTrace();
			}
		}

		/*
		 * Si les adresses font plus de 16 bits, lève une exception
		 */
		for(Character c:this.tab.keySet()) {
			if(this.tab.get(c).length()>16) {
				throw new ExceptionCodageImpossible("Le charset est trop grand pour utiliser la méthode de Huffman");
			}
		}

		this.ecrireTableau();
		Boolean[] tab=this.compresserEtEcrireDansRes();

		/*
		 * Si on a pas gagné de place, lève une exception
		 */
		if(this.txt.length()<=this.res.length()) {
			throw new ExceptionCodageImpossible("Le codage ne fait pas gagner de place !");
		}

		return tab;
	}

	/**
	 * Modélise le tableau de conversion des caractères sous forme de String
	 */
	private void ecrireTableau() {
		/*
		 * On sérialise l'objet manuellement
		 */
		String serial="";
		Character[] set=this.tab.keySet().toArray(new Character[0]);
		for(int i=0;i<set.length;i++) {
			/*
			 * D'abord on écrit le caractère
			 */
			serial+=set[i];

			/*
			 * Ensuite, la taille de l'ecriture binaire
			 */
			serial+=(char)(this.tab.get(set[i]).length());
			/*
			 * Puis on écrit le code associé au caractère
			 */
			String code=this.tab.get(set[i]);
			Boolean[] bits=new Boolean[code.length()];
			for(int j=0;j<code.length();j++) {
				if(code.charAt(j)=='1') {
					bits[j]=true;
				}
				else {
					bits[j]=false;
				}	
			}
			String temp=SortieBinaireString.creerString(bits);	
			serial+=temp;
		}
		this.tabConv="";
		this.tabConv+=(char)(serial.length()+1);
		this.tabConv+=serial;
	}

	/**
	 * Fonction permettant d'écrire le texte codé dans une String mémorisée
	 * @return le tableau de booleen modélisant le texte, qu'on réutilisera si besoin
	 */
	private Boolean[] compresserEtEcrireDansRes() {
		this.res+=this.tabConv;
		ArrayList<Boolean> aEcrire=new ArrayList<Boolean>();
		/*
		 * On parcourt le texte à encoder
		 */
		for(int i=0;i<this.txt.length();i++) {
			String seq=this.tab.get(this.txt.charAt(i));
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
		Set<Character> keys=this.freq.keySet();
		for(Character c:keys) {
			int val=this.freq.get(c);
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
				this.tab.put(arbre.feuille().x, adresse);
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
	 * Parcourt le texte en paramètre afin de compter le nombre d'itérations de chaque caractère
	 * présent dans ce texte et de stocker ces informations
	 * @param texte le texte à parcourir
	 */
	private void analyseFrequentielleParString(String texte) {
		for(int i=0;i<texte.length();i++) {
			Character c=texte.charAt(i);
			this.analyseFrequentielleParChar(c);
		}
	}

	/**
	 * Parcourt l'input stream en paramètre afin de compter le nombre d'itérations de chaque caractère
	 * présent dans ce texte et de stocker ces informations
	 * @throws IOException si il y a une erreur de lecture sur l'inputstream fourni
	 */
	private void analyseFrequentielleParStream(InputStream s) throws IOException {
		Character c = null;
		int i=-1;
		i=s.read();
		c = (char)i;
		while(i!=-1) {
			this.txt+=c;
			this.analyseFrequentielleParChar(c);
			i=s.read();
			c = (char)i;
		}
	}

	/**
	 * Ajoute un caractère de plus pour l'analyse fréquentielle
	 * @param c le caractère suplémentaire  considérer
	 */
	private void analyseFrequentielleParChar(Character c) {
		if(this.freq.containsKey(c)) {
			this.freq.put(c, this.freq.get(c)+1);
		}
		else {
			this.freq.put(c, 1);
		}
	}

	/**
	 * @return le résultat de l'encodage sous forme de String
	 */
	public String getStringResult() {
		return this.res;
	}

}
