package entreesSorties;

import java.io.IOException;
import java.io.InputStream;

/**
 * Classe permettant de lire sur un stream en bit � bit
 * @author Lucas
 *
 */
public class EntreeBinaireStream extends EntreeBinaire {

	private InputStream entr�e;

	public EntreeBinaireStream (InputStream entr�e){
		super();
		this.entr�e = entr�e;
	}

	public void fermer()throws IOException {
		entr�e.close();
	}
	
	public InputStream entr�eOctal(){
		return entr�e;
	}

	public int lireChar() throws IOException {
		return this.entr�e.read();
	}
	
}
