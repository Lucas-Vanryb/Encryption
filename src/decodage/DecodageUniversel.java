package decodage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import compression.CompressionUniverselle;

import codage.CodageUniversel;
import codage.ExceptionCodageImpossible;
import entreesSorties.EntreeBinaire;
import entreesSorties.EntreeBinaireStream;
import entreesSorties.EntreeBinaireString;

/**
 * Méthode de décodage à l'aide de l'algorithme de Huffman universelle pour la langue francaise.
 * @author Lucas
 *
 */
public class DecodageUniversel {

	/**
	 * La TABle de conversion caractère -> Code binaire sous forme de String le modélisant
	 */
	private HashMap<Character, String> TAB;

	/**
	 * Texte source
	 */
	private String txt;

	/**
	 * Le texte décodé
	 */
	private String res=null;

	public DecodageUniversel(String source, OutputStream out) throws IOException, ExceptionDecodageImpossible {
		try {
			this.init();
			this.txt=source;
			this.decoder(source, out);
		}
		catch(Exception e) {
			throw new ExceptionDecodageImpossible("Format de la chaîne incorrecte. Ceci n'a pas été encodé par cet algortihme.");
		}
	}

	public DecodageUniversel(String source) throws ExceptionDecodageImpossible {
		try {
			this.init();
			this.res="";
			this.txt=source;
			this.decoder(this.txt);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDecodageImpossible("Format de la chaîne incorrecte. Ceci n'a pas été encodé par cet algortihme.");
		}
	}

	public DecodageUniversel(InputStream in, OutputStream out) throws IOException, ExceptionDecodageImpossible {
		try {
			this.init();
			this.decoder(in, out);
		}
		catch(Exception e) {
			throw new ExceptionDecodageImpossible("Format de la chaîne incorrecte. Ceci n'a pas été encodé par cet algortihme.");
		}
		in.close();
	}

	public DecodageUniversel(InputStream in) throws IOException, ExceptionDecodageImpossible {
		try {
			this.init();
			this.res="";
			this.decoder(in);
		}
		catch(Exception e) {
			throw new ExceptionDecodageImpossible("Format de la chaîne incorrecte. Ceci n'a pas été encodé par cet algortihme.");
		}
		in.close();
	}

	/**
	 * Méthode initialisant le tableau TAB
	 * @throws ExceptionDecodageImpossible 
	 */
	private void init() throws ExceptionDecodageImpossible {
		this.TAB=CodageUniversel.tab;
		if(this.TAB==null) {
			try {
				CompressionUniverselle.encoderUniversellementStringToString("void");
			} catch (ExceptionCodageImpossible e) {}
			this.TAB=CodageUniversel.tab;
			if(this.TAB==null) {
				throw new ExceptionDecodageImpossible("Pas assez de textes fournis !");
			}
		}
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
			if(this.TAB.containsValue(buffer)) {
				/*
				 * On recherche ce caractère
				 */
				Character[] set=this.TAB.keySet().toArray(new Character[0]);
				Character c=set[0];
				int i=0;
				while(!this.TAB.get(c).equals(buffer)) {
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
			if(this.TAB.containsValue(buffer)) {
				/*
				 * On recherche ce caractère
				 */
				Character[] set=this.TAB.keySet().toArray(new Character[0]);
				Character c=set[0];
				int i=0;
				while(!this.TAB.get(c).equals(buffer)) {
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
			if(this.TAB.containsValue(buffer)) {
				/*
				 * On recherche ce caractère
				 */
				Character[] set=this.TAB.keySet().toArray(new Character[0]);
				Character c=set[0];
				int i=0;
				while(!this.TAB.get(c).equals(buffer)) {
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
			if(this.TAB.containsValue(buffer)) {
				/*
				 * On recherche ce caractère
				 */
				Character[] set=this.TAB.keySet().toArray(new Character[0]);
				Character c=set[0];
				int i=0;
				while(!this.TAB.get(c).equals(buffer)) {
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
	 * @return la String de résultat du décodage si la méthode de décodage vers String a été utilisée, null sinon
	 */
	public String getResultat() {
		return this.res;
	}
}
