package entreesSorties;

import java.io.*;

/**
 * Classe abstraite permettant de faire de la transcription bit � bit
 * @author Lucas
 *
 */
public abstract class SortieBinaire  {
	private int nbreBitsAEcrire;
	private int octet;

	public SortieBinaire(){
		nbreBitsAEcrire = 0;
		octet = 0;
	}

	public void ecrire(boolean bit) throws IOException {
		// �criture d'un bit (1 ou 0) d�cal� � gauche 
		//   (de 7, puis de 6, ..., puis de 0)
		octet |= (bit ? 1 : 0) << (7 - nbreBitsAEcrire);
		nbreBitsAEcrire++;
		if(nbreBitsAEcrire == 8){
			this.ecrireChar(octet);
			nbreBitsAEcrire = 0;
			octet = 0;
		}
	}

	public void ecrire(Boolean[] bits) throws IOException {
		for(boolean bit : bits){
			ecrire(bit);
		}
	}

	public void compl�ter() throws IOException {
		if(nbreBitsAEcrire > 0){
			this.ecrireChar(octet);
			nbreBitsAEcrire = 0;
			octet = 0;
		}
	}
	
	public abstract void ecrireChar(int octet) throws IOException;

}
