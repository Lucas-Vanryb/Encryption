package entreesSorties;

import java.io.IOException;

/**
 * Classe permettant l'�criture binaire dans une String et non dans un OutputStream
 * @author Lucas
 *
 */
public class SortieBinaireString extends SortieBinaire{

	/**
	 * La string sur laquelle on �crit si on cherche � �crire dans une String et non dans un OutputStream
	 */
	private String res;

	
	
	/**
	 * Constructeur par d�faut utilis� pour �crire sur une String
	 */
	public SortieBinaireString(){
		super();
		this.res="";
	}


	/**
	 * Retourne la String gard�e en m�moire
	 * @return la String en m�moire
	 */
	public String getStringResult() {
		return this.res;
	}
	
	/**
	 * M�thode �crivant un tableau de bits dans une String et la retourne
	 * @param bits le tableau de bits � �crire
	 * @return la String encod�e
	 */
	public static String creerString(Boolean[] bits){
		SortieBinaireString bin=new SortieBinaireString();
		try {
			bin.ecrire(bits);
			bin.compl�ter();
		} catch (IOException e) {}
	
		return bin.getStringResult();
	}


	@Override
	public void ecrireChar(int octet) throws IOException {
		this.res+=(char)octet;
	}
}
