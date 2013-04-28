package entreesSorties;

import java.io.*;

/**
 * Classe permettant de lire en bit ˆ bit
 * @author Lucas
 *
 */
public abstract class EntreeBinaire {

	private int nbreBitsALire;
	private int octet;

	public EntreeBinaire() {
		this.nbreBitsALire = 0;
		this.octet = 0;
	}

	/*
	 * méthode renvoyant 1 si le bit courant est à 1
	 * 0 sinon
	 * -1 si le fichier est vide
	 * Amélioration : renvoyer un boolean (en fait : problème lorsque le fichier est vide)
	 */
	public int lire() throws IOException {
		if(nbreBitsALire == 0){
			octet = this.lireChar();
			if(octet == -1)
				return -1;
			nbreBitsALire = 8;
		}
		nbreBitsALire--;
		return 1 & (octet >> nbreBitsALire);
	}

	public abstract int lireChar() throws IOException;


} 
