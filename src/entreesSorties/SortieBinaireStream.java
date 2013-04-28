package entreesSorties;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Classe permettant d'Žcrire en binaire sur un outputstream
 * @author Lucas
 *
 */
public class SortieBinaireStream extends SortieBinaire {

	private OutputStream sortie;
	
	public SortieBinaireStream(OutputStream sortie) {
		super();
		this.sortie = sortie;
	}
	
	public void vider() throws IOException {	
		sortie.flush();
	}
	public OutputStream sortieOctal(){
		return sortie;
	}
	
	public void fermer() throws IOException {
		sortie.close();
	}
	
	public void ecrireChar(int octet) throws IOException {
		sortie.write(octet);
	}
	
}
