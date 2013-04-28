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
 * Classe impl�mentant un codage standard,
 * construisant l'arbre binaire de Huffman � partir de l'analyse frequentielle
 * effectu�e sur le texte
 * Ici, je me suis trouv� face � un probl�me algorithmique qui m'a forc� � cr�er deux listes, l'une ordonn�e,
 * l'autre non.
 * @author Lucas
 *
 */
public class CodageStandard {


	/**
	 * Les caract�res pr�sents dans le texte, ordonn�s
	 */
	private TreeSet<Arbre> freqOrd;

	/**
	 * Les caract�res pr�sents dans le texte et leur fr�quence
	 */
	private HashMap<Character, Integer> freq;

	/**
	 * La table de conversion caract�re -> Code binaire sous forme de String le mod�lisant
	 */
	private HashMap<Character, String> tab;

	/**
	 * Le resultat encod� sous forme de String
	 */
	private String res;

	/**
	 * Le texte de d�part
	 */
	private String txt;

	/**
	 * Mod�le du tableau de conversion sous forme de String
	 */
	private String tabConv;

	public CodageStandard() {
		this.freqOrd=new TreeSet<Arbre>();
		this.freq=new HashMap<Character,Integer>();
		this.tab=new HashMap<Character,String>();
		this.res="";
	}

	/**
	 * Constructeur permettant de lancer le codage d'une String et de le transcirre sur l'Outputstream pass� en param�tre
	 * @param s la String � encoder
	 * @param out le Stream sur lequel �crire en sortie
	 * @throws IOException si il y a une erreur d'�criture sur le stream fourni
	 * @throws ExceptionCodageImpossible si le texte encod� est plus long que le texte source
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
		sortie.compl�ter();
		out.flush();
		out.close();
	}

	/**
	 * Constructeur permettant de lancer le codage d'une source lue via un InutStream et de le transcrire sur un OutputStream donn�
	 * @param in l'InputStream � lire
	 * @param out l'OutputStream sur lequel encoder
	 * @throws IOException  si il y a une erreur de lecture ou d'�criture sur le stream fourni
	 * @throws ExceptionCodageImpossible si le texte encod� est plus long que le texte source
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
		sortie.compl�ter();
		out.flush();
		out.close();
		in.close();
	}

	/**
	 * Constructeur permettant de lancer le codage d'une source lue via un InutStream et de le transcrire dans une String retourn�e
	 * @param in l'InputStream � lire
	 * @throws IOException  si il y a une erreur de lecture ou d'�criture sur le stream fourni
	 * @throws ExceptionCodageImpossible si le texte encod� est plus long que le texte source
	 */
	public CodageStandard(InputStream in) throws IOException,ExceptionCodageImpossible {
		this();
		this.txt="";
		this.analyseFrequentielleParStream(in);
		this.coder();
		in.close();
	}

	/**
	 * Constructeur permettant de lancer le codage d'une source lue dans une String et de le transcrire dans une String retourn�e
	 * @param s la String � encoder
	 * @throws ExceptionCodageImpossible si le texte encod� est plus long que le texte source
	 */
	public CodageStandard(String s) throws ExceptionCodageImpossible {
		this();
		this.txt=s;
		this.analyseFrequentielleParString(s);
		this.coder();
	}

	/**
	 * Fonction permettant d'encoder, une fois la lecture r�alis�e
	 * @throws ExceptionCodageImpossible si le codage ne fait pas gagner de place
	 */
	private Boolean[] coder() throws ExceptionCodageImpossible {
		if(this.txt.equals("")) {
			throw new ExceptionCodageImpossible("Cha�ne vide !");
		}
		this.ordonnerListe();
		this.construireArbre();
		if(!freqOrd.first().getArbreBinaire().estFeuille()) {
			this.parcourirArbreEtEtablirTableDeConversion(this.freqOrd.first().getArbreBinaire(), "");
		}
		/*
		 * Si il n'y a qu'un seul caract�re dans le texte, on l'encode par '0'
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
		 * Si les adresses font plus de 16 bits, l�ve une exception
		 */
		for(Character c:this.tab.keySet()) {
			if(this.tab.get(c).length()>16) {
				throw new ExceptionCodageImpossible("Le charset est trop grand pour utiliser la m�thode de Huffman");
			}
		}

		this.ecrireTableau();
		Boolean[] tab=this.compresserEtEcrireDansRes();

		/*
		 * Si on a pas gagn� de place, l�ve une exception
		 */
		if(this.txt.length()<=this.res.length()) {
			throw new ExceptionCodageImpossible("Le codage ne fait pas gagner de place !");
		}

		return tab;
	}

	/**
	 * Mod�lise le tableau de conversion des caract�res sous forme de String
	 */
	private void ecrireTableau() {
		/*
		 * On s�rialise l'objet manuellement
		 */
		String serial="";
		Character[] set=this.tab.keySet().toArray(new Character[0]);
		for(int i=0;i<set.length;i++) {
			/*
			 * D'abord on �crit le caract�re
			 */
			serial+=set[i];

			/*
			 * Ensuite, la taille de l'ecriture binaire
			 */
			serial+=(char)(this.tab.get(set[i]).length());
			/*
			 * Puis on �crit le code associ� au caract�re
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
	 * Fonction permettant d'�crire le texte cod� dans une String m�moris�e
	 * @return le tableau de booleen mod�lisant le texte, qu'on r�utilisera si besoin
	 */
	private Boolean[] compresserEtEcrireDansRes() {
		this.res+=this.tabConv;
		ArrayList<Boolean> aEcrire=new ArrayList<Boolean>();
		/*
		 * On parcourt le texte � encoder
		 */
		for(int i=0;i<this.txt.length();i++) {
			String seq=this.tab.get(this.txt.charAt(i));
			/*
			 * On rajoute les bits � encoder un par un dans l'arraylist
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
		 * On cast l'arraylist en tableau, puis on �crit
		 */
		Boolean[] aEcrireTab=aEcrire.toArray(new Boolean[0]);
		this.res+=SortieBinaireString.creerString(aEcrireTab);
		return aEcrireTab;
	}


	/**
	 * M�thode permettant de reporter les donn�es r�colt�s lors du parcours du texte dans une liste ordonn�e
	 * automatiquement.
	 * On cr�e une 'feuille' par caract�re, et on l'ajoute � la liste ordonn�e
	 */
	private void ordonnerListe() {
		Set<Character> keys=this.freq.keySet();
		for(Character c:keys) {
			int val=this.freq.get(c);
			this.freqOrd.add(new Arbre(val,c));
		}
	}

	/**
	 * M�thode permettant, � partir d'une liste ordonn�e contenant l'ensemble des feuilles, de construire
	 * un arbre de Huffman.
	 * Cette m�thode construit un noeud avec les deux premiers �l�ments de la liste syst�matiquement, jusqu'� ce qu'il n'y en ait plus qu'un.
	 * La branche de gauche est syst�matiquement la plus l�g�re.
	 * Une fois la fonction ex�cut�e, il ne restera qu'un arbre dans la liste ordonn�e en attribut.
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
	 * Parcourt l'arbre et remplit le tableau de correspondance caract�re <--> string qui
	 * donne la mod�lisation binaire d'un caract�re
	 * Cette m�thode ne DOIT PAS �tre appel�e si l'arbre 'principal' ou de Huffman est une FEUILLE,
	 * auquel cas le caract�re unique sera mod�lis� par une String vide !
	 * @param adresse l'adresse de l'arbre au sein de l'arbre principal
	 * @param arbre l'arbre ou le sous-arbre � parcourir
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
			 *N�cessit� de la compilation, encore une fois. 
			 */
			catch (ExceptionTypeAbstrait e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * Parcourt le texte en param�tre afin de compter le nombre d'it�rations de chaque caract�re
	 * pr�sent dans ce texte et de stocker ces informations
	 * @param texte le texte � parcourir
	 */
	private void analyseFrequentielleParString(String texte) {
		for(int i=0;i<texte.length();i++) {
			Character c=texte.charAt(i);
			this.analyseFrequentielleParChar(c);
		}
	}

	/**
	 * Parcourt l'input stream en param�tre afin de compter le nombre d'it�rations de chaque caract�re
	 * pr�sent dans ce texte et de stocker ces informations
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
	 * Ajoute un caract�re de plus pour l'analyse fr�quentielle
	 * @param c le caract�re supl�mentaire  consid�rer
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
	 * @return le r�sultat de l'encodage sous forme de String
	 */
	public String getStringResult() {
		return this.res;
	}

}
