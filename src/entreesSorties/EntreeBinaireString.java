package entreesSorties;

import java.io.IOException;

/**
 * Classe permettant la lecture binaire dans des Strings et non dans des OutputStream
 * @author Lucas
 *
 */
public class EntreeBinaireString extends EntreeBinaire {

	/**
	 * La string dans laquelle on lit si on lit une String et non un InputStream
	 */
	private String txt;
	private int iterator;

	public EntreeBinaireString(String s){
		super();
		this.txt=s;
		this.iterator=-1;
	}

	@Override
	public int lireChar() throws IOException {
		this.iterator++;
		try {
			return this.txt.charAt(this.iterator);
		}
		catch(Exception e) {
			return -1;
		}
	}

}
