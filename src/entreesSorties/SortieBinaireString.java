package entreesSorties;

import java.io.IOException;

/**
 * Classe permettant l'écriture binaire dans une String et non dans un OutputStream
 * @author Lucas
 *
 */
public class SortieBinaireString extends SortieBinaire{

	/**
	 * La string sur laquelle on écrit si on cherche à écrire dans une String et non dans un OutputStream
	 */
	private String res;

	
	
	/**
	 * Constructeur par défaut utilisé pour écrire sur une String
	 */
	public SortieBinaireString(){
		super();
		this.res="";
	}


	/**
	 * Retourne la String gardée en mémoire
	 * @return la String en mémoire
	 */
	public String getStringResult() {
		return this.res;
	}
	
	/**
	 * Méthode écrivant un tableau de bits dans une String et la retourne
	 * @param bits le tableau de bits à écrire
	 * @return la String encodée
	 */
	public static String creerString(Boolean[] bits){
		SortieBinaireString bin=new SortieBinaireString();
		try {
			bin.ecrire(bits);
			bin.complÈter();
		} catch (IOException e) {}
	
		return bin.getStringResult();
	}


	@Override
	public void ecrireChar(int octet) throws IOException {
		this.res+=(char)octet;
	}
}
