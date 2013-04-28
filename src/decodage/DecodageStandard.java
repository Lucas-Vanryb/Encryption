package decodage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import entreesSorties.EntreeBinaire;
import entreesSorties.EntreeBinaireStream;
import entreesSorties.EntreeBinaireString;

/**
 * Classe permettant de décoder en utlisant l'algorithme de Huffman.
 * @author Lucas
 *
 */
public class DecodageStandard {

	/**
	 * La table de conversion caractère -> Code binaire sous forme de String le modélisant
	 */
	private HashMap<Character, String> tab;

	/**
	 * Modèle du tableau de conversion sous forme de String
	 */
	private String tabConv;

	/**
	 * Texte source
	 */
	private String txt;

	/**
	 * Le texte décodé
	 */
	private String res=null;

	public DecodageStandard(String source, OutputStream out) throws IOException, ExceptionDecodageImpossible {
		try {
			this.tab=new HashMap<Character, String>();
			this.initString(source);
			this.decoder(this.txt, out);
		}
		catch(Exception e) {
			throw new ExceptionDecodageImpossible("Format de la chaîne incorrecte. Ceci n'a pas été encodé par cet algortihme.");
		}
	}

	public DecodageStandard(String source) throws ExceptionDecodageImpossible {
		try {
			this.tab=new HashMap<Character, String>();
			this.res="";
			this.initString(source);
			this.decoder(this.txt);
		}
		catch(Exception e) {
			throw new ExceptionDecodageImpossible("Format de la chaîne incorrecte. Ceci n'a pas été encodé par cet algortihme.");
		}
	}

	public DecodageStandard(InputStream in, OutputStream out) throws IOException, ExceptionDecodageImpossible {
		try {
			this.tab=new HashMap<Character, String>();
			this.initStream(in);
			this.decoder(in, out);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDecodageImpossible("Format de la chaîne incorrecte. Ceci n'a pas été encodé par cet algortihme.");
		}
		in.close();
	}

	public DecodageStandard(InputStream in) throws IOException, ExceptionDecodageImpossible {
		try {
			this.tab=new HashMap<Character, String>();
			this.res="";
			this.initStream(in);
			this.decoder(in);
		}
		catch(Exception e) {
			throw new ExceptionDecodageImpossible("Format de la chaîne incorrecte. Ceci n'a pas été encodé par cet algortihme.");
		}
		in.close();
	}

	/**
	 * Méthodes utilisées à l'initialisation si la source est une String
	 * @param source la String source
	 * @throws ExceptionDecodageImpossible 
	 */
	private void initString(String source) throws ExceptionDecodageImpossible {
		if(source.equals("")) {
			throw new ExceptionDecodageImpossible("Chaîne vide");
		}
		/*
		 * On récupère la taille de la partie du texte permettant de coder le tableau des correspondances
		 */
		int tailleTab=source.charAt(0);
		/*
		 * On découpe ensuite la String
		 */
		this.tabConv=source.substring(1, tailleTab);
		this.txt=source.substring(tailleTab);
		this.recupererTab();
	}

	/**
	 * Méthode utilisée à l'initialisation si la source est un Stream
	 * @param in le stream à lire
	 * @throws IOException en cas d'erreur de lecture
	 */
	private void initStream(InputStream in) throws IOException {
		/*
		 * On récupère avant toute chose la taille de la partie du texte permettant de coder le tableau des correspondances
		 */
		int tailleTab=in.read();
		this.tabConv="";
		for(int i=0;i<tailleTab-1;i++) {
			this.tabConv+=(char)in.read();
		}
		this.recupererTab();
	}

	/**
	 * Méthode permettant de décoder un Stream et de l'écrire sur un autre Stream
	 * @param in le Stream d'entrée
	 * @param out le Stream de sortie
	 * @throws IOException en cas d'erreur de lecture ou d'écriture
	 */
	private void decoder(InputStream in,OutputStream out) throws IOException {
		String buffer="";
		EntreeBinaire entree=new EntreeBinaireStream(in);
		int t=entree.lire();
		buffer+=t;
		/*
		 * Tant qu'il reste des bits à lire
		 */
		while(t!=-1) {
			/*
			 * Si le buffer correspond à un caractère
			 */
			if(this.tab.containsValue(buffer)) {
				/*
				 * On recherche ce caractère
				 */
				Character[] set=this.tab.keySet().toArray(new Character[0]);
				Character c=set[0];
				int i=0;
				while(!this.tab.get(c).equals(buffer)) {
					i++;
					c=set[i];
				}
				/*
				 * On reset le buffer
				 */
				buffer="";
				/*
				 * Puis on l'écrit
				 */
				out.write(c);
			}
			t=entree.lire();
			buffer+=t;
		}
		/*
		 * On ferme le Stream
		 */
		out.flush();
		out.close();
	}

	/**
	 * Méthode permettant de décoder un Stream et de l'écrire dans une String en mémoire
	 * @param in le Stream d'entrée
	 * @throws IOException en cas d'erreur de lecture
	 */
	private void decoder(InputStream in) throws IOException {
		String buffer="";
		EntreeBinaire entree=new EntreeBinaireStream(in);
		int t=entree.lire();
		buffer+=t;
		/*
		 * Tant qu'il reste des bits à lire
		 */
		while(t!=-1) {
			/*
			 * Si le buffer correspond à un caractère
			 */
			if(this.tab.containsValue(buffer)) {
				/*
				 * On recherche ce caractère
				 */
				Character[] set=this.tab.keySet().toArray(new Character[0]);
				Character c=set[0];
				int i=0;
				while(!this.tab.get(c).equals(buffer)) {
					i++;
					c=set[i];
				}
				/*
				 * On reset le buffer
				 */
				buffer="";
				/*
				 * Puis on l'écrit
				 */
				this.res+=c;
			}
			t=entree.lire();
			buffer+=t;
		}
	}

	/**
	 * Méthode permettant de décoder le texte sous forme de String stocké et de l'écrire sur un Stream
	 * @param s la String a décoder
	 * @param out le Stream sur lequel écrire
	 * @throws IOException en cas d'erreur d'écriture
	 */
	private void decoder(String s,OutputStream out) throws IOException {
		String buffer="";
		EntreeBinaireString entree=new EntreeBinaireString(s);
		int t=entree.lire();
		buffer+=t;
		/*
		 * Tant qu'il reste des bits à lire
		 */
		while(t!=-1) {
			/*
			 * Si le buffer correspond à un caractère
			 */
			if(this.tab.containsValue(buffer)) {
				/*
				 * On recherche ce caractère
				 */
				Character[] set=this.tab.keySet().toArray(new Character[0]);
				Character c=set[0];
				int i=0;
				while(!this.tab.get(c).equals(buffer)) {
					i++;
					c=set[i];
				}
				/*
				 * On reset le buffer
				 */
				buffer="";
				/*
				 * Puis on l'écrit
				 */
				out.write(c);
			}
			t=entree.lire();
			buffer+=t;
		}
		/*
		 * On ferme le Stream
		 */
		out.flush();
		out.close();
	}

	/**
	 * Méthode permettant de décoder le texte sous forme de String stocké et de l'écrire dans une String en attribut
	 * @param s la String à décoder
	 */
	private void decoder(String s) {
		String buffer="";
		EntreeBinaireString entree=new EntreeBinaireString(s);
		int t=0;
		try {
			t = entree.lire();
		}catch (IOException e) {}
		buffer+=t;
		/*
		 * Tant qu'il reste des bits à lire
		 */
		while(t!=-1) {
			/*
			 * Si le buffer correspond à un caractère
			 */
			if(this.tab.containsValue(buffer)) {
				/*
				 * On recherche ce caractère
				 */
				Character[] set=this.tab.keySet().toArray(new Character[0]);
				Character c=set[0];
				int i=0;
				while(!this.tab.get(c).equals(buffer)) {
					i++;
					c=set[i];
				}
				/*
				 * On reset le buffer
				 */
				buffer="";
				/*
				 * Puis on l'écrit
				 */
				this.res+=c;
			}
			try {
				t=entree.lire();
			} catch (IOException e) {}
			buffer+=t;
		}
	}

	/**
	 * Méthode permettant de récupérer le tableau à partir de la String source.
	 * Ensuite, la String est coupée afin de ne laisser que le texte en lui-même
	 */
	private void recupererTab() {
		/*
		 * On deserialize l'objet manuellement
		 */
		for(int i=0;i<this.tabConv.length();) {
			char c=this.tabConv.charAt(i);
			int taille=this.tabConv.charAt(i+1);
			String binaryString="";
			EntreeBinaire l=new EntreeBinaireString(this.tabConv.substring(i+2));
			for(int j=0;j<taille;j++) {
				try {binaryString+=l.lire();}catch (IOException e) {}				
			}
			/*
			 * Puis, on la mémorise
			 */
			this.tab.put(c, binaryString);
			if(taille%8==0){
				i+=2+Math.max(taille/8,1);
			}
			else {
				i+=2+Math.max(taille/8+1,1);
			}
		}
	}

	/**
	 * @return la String de résultat du décodage si la méthode de décodage vers String a été utilisée, null sinon
	 */
	public String getResultat() {
		return this.res;
	}
}
