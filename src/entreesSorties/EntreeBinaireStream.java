package entreesSorties;

import java.io.IOException;
import java.io.InputStream;

/**
 * Classe permettant de lire sur un stream en bit ˆ bit
 * @author Lucas
 *
 */
public class EntreeBinaireStream extends EntreeBinaire {

	private InputStream entrée;

	public EntreeBinaireStream (InputStream entrée){
		super();
		this.entrée = entrée;
	}

	public void fermer()throws IOException {
		entrée.close();
	}
	
	public InputStream entréeOctal(){
		return entrée;
	}

	public int lireChar() throws IOException {
		return this.entrée.read();
	}
	
}
