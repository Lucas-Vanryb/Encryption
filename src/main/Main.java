package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import codage.ExceptionCodageImpossible;
import compression.Compression;
import compression.CompressionUniverselle;

/**
 * Classe main de test
 * @author Lucas
 *
 */
public class Main {

	/**
	 * Fonction de test
	 * J'ai moi m�me effectu� cette s�rie de test et tout fonctionne comme pr�vu.
	 * Aucune exception non pr�vue ne peut �tre lev�e par le programme � part si la cha�ne pass�e en param�tre est null
	 * @param argv
	 */
	public static void main(String[] argv) {
		try {
			System.out.println("Test de l'algorithme de Huffman par Lucas Vanryb :\n\n");
			System.out.println("1) D�but des tests basiques (StringToString, textes de taille suffisante et au jeu de caract�re limit�)\n");
			String test1="aaaaaaaaaaaaaabbbbbbbbbbbb";
			System.out.println("Encodage de "+test1+" :\n");
			String result1=Compression.encoderStringToString(test1);
			System.out.println(result1);
			System.out.println("Decodage de "+result1+" :\n");
			String testInverse1=Compression.decoderStringToString(result1);
			System.out.println(testInverse1);
			System.out.println("--------------------");
			String test2="Nous allons effectuer un test simple en langue francaise sans accent afin de s'assurer du bon fonctionnement du code.Nous allons effectuer un test simple en langue francaise sans accent afin de s'assurer du bon fonctionnement du code.Nous allons effectuer un test simple en langue francaise sans accent afin de s'assurer du bon fonctionnement du code.";
			System.out.println("Encodage de "+test2+" :\n");
			String result2=Compression.encoderStringToString(test2);
			System.out.println(result2.substring(0, 7)+".....");
			System.out.println("Decodage de "+result2.substring(0, 7)+"..... :\n");
			String testInverse2=Compression.decoderStringToString(result2);
			System.out.println(testInverse2);
			System.out.println("\n\n--------------------\n\n");
		}
		catch(Exception e) {
			System.out.println("Erreur !");
		}
		try {
			System.out.println("2) Passons aux tests de lecture/�criture de fichiers");
			Compression.encoderStreamToStream(new FileInputStream(new File("source3.txt")), new FileOutputStream(new File("result3.txt")));
			Compression.decoderStreamToStream(new FileInputStream(new File("result3.txt")), new FileOutputStream(new File("testInverse3.txt")));
			System.out.println("Veuillez v�rifier que les fichiers testInverse3.txt et result3.txt sont identiques\n");
			String test4=Compression.encoderStreamToString(new FileInputStream(new File("source4.txt")));
			Compression.decoderStringToStream(test4, new FileOutputStream(new File("testInverse4.txt")));
			System.out.println("Veuillez v�rifier que les fichiers testInverse4.txt et source4.txt sont identiques\n");
			String test5="Nous allons effectuer un test simple en langue francaise sans accent afin de s'assurer du bon fonctionnement du code.Nous allons effectuer un test simple en langue francaise sans accent afin de s'assurer du bon fonctionnement du code.Nous allons effectuer un test simple en langue francaise sans accent afin de s'assurer du bon fonctionnement du code.";
			System.out.println("Encodage puis d�codage de "+test5+" en passant par un fichier texte\n");
			Compression.encoderStringToStream(test5, new FileOutputStream(new File("result5.txt")));
			System.out.println(Compression.decoderStreamToString(new FileInputStream(new File("result5.txt"))));
			System.out.println("\n\n--------------------\n\n");
		}
		catch(IOException e) {
			System.out.println("Erreur de Lecture/Ecriture : V�rifiez que les fichiers source3.txt et source4.txt sont bien dans le buildpath !");
		}
		catch(Exception e) {
			if(! (e instanceof IOException)) {
				e.printStackTrace();
				System.out.println("Erreur !");
			}
		}
		try {
			System.out.println("3) Passage aux tests de cas de codage impossible : Cas 1, le codage ne fait pas gagner de place :");
			System.out.println("Encodage de : azertyuiopqsdfghjklmwxcvbn");
			Compression.encoderStringToString("azertyuiopqsdfghjklmwxcvbn");
		}
		catch(ExceptionCodageImpossible e) {
			System.out.println(e);
			System.out.println("Test OK !");
		}

		String paragraphe="Dans ce partie, nous allons particuli�rement nous int�resser � l�erreur sur les param�tres initiaux de la simulation, et nous allons proposer une solution pour en partie pallier cette erreur. Il convient tout d�abord pour cette �tude d�affirmer que le syst�me �tudi� n�est pas chaotique, c�est � dire qu�un l�ger changement sur les param�tres initiaux de la simulation n�engendrera qu�un l�ger ajustement sur les r�sultats. Ce r�sultat est ici �vident aux vues des m�thodes de calcul employ�es dans notre simulation. Il convient ensuite de d�terminer les param�tres sur lesquels il existe une erreur initiale. Notre simulation se base sur conq param�tres ayant une influence sur l�erreur (en effet, les dimensions du cube sont celle de son moule, qui a �t� construit avec pr�cision) : la conductivit� thermique du pl�tre, le coefficient de convection, la temp�rature impos�e � l�int�rieur de la cavit�, le nombre de mailles utilis�es pour la discr�tisation, et enfin le param�tre epsilon d�terminant la pr�cision que l�on cherche � atteindre en fin de simulation. Sur ces param�tres, seuls les trois premiers, qui sont les param�tres physiques du probl�me, vont nous int�resser pour la suite de lՎtude. En effet, les deux derniers sont propres � la simulation, et n�ont de l�incidence que sur la pr�cision du r�sultat (et pas sur sa valeur moyenne). Il nous faut ensuite un point de mesure permettant de comparer la simulation et l�exp�rience. Ce point de comparaison sera la temp�rature au milieu exact de la face inf�rieure du cube de pl�tre, correspondant  � l�emplacement d�une des deux sondes de temp�rature plac�es dans notre maquette exp�rimentale.";
		System.out.println("Etude du gain de place sur un texte simple de "+paragraphe.length()+" caract�res : "+paragraphe);
		try {
			System.out.println("Caract�res �conomis�s :"+ (paragraphe.length()-Compression.encoderStringToString(paragraphe).length()) + " Taux de compression :"+((0.0+Compression.encoderStringToString(paragraphe).length())/paragraphe.length()*100)+"%\n\n");
		} catch (ExceptionCodageImpossible e) {
			System.out.println(e);
			System.out.println("Erreur !");
		}

		System.out.println("\n\n--------------------\n\n");
		
		System.out.println("Test du codage universel :");
		System.out.println("Ajout des textes....");
		try {
			CompressionUniverselle.ajouterTexteStream(new FileInputStream(new File("textCodageUni1.txt")));
			CompressionUniverselle.ajouterTexteStream(new FileInputStream(new File("textCodageUni2.txt")));
		} catch (Exception e1) {
			System.out.println("Veuillez v�rifier que les fichier textCodageUni1.txt et textCodageUni2.txt sont dans le buildpath ! Erreur de lecture !");
		}
		
		try {
			String testUni="La po�sie est un genre litt�raire tr�s ancien aux formes vari�es, �crites g�n�ralement en vers, mais qui admet aussi la prose, et qui privil�gie l'expressivit� de la forme. Sa d�finition se r�v�le difficile, et varie selon les �poques, au point que chaque si�cle peut lui trouver une fonction et une expression diff�rente aussi d'un po�te � l'autre.";
			System.out.println("Encodage de "+testUni);
			String resultUni=CompressionUniverselle.encoderUniversellementStringToString(testUni);
			System.out.println(resultUni.substring(0,7)+".....");
			System.out.println("D�codage :");
			String testInverseUni=CompressionUniverselle.decoderUniversellementStringToString(resultUni);
			System.out.println(testInverseUni);
			System.out.println("Caract�res �conomis�s :"+ (testUni.length()-resultUni.length()) + " Taux de compression :"+((0.0+resultUni.length())/testUni.length()*100)+"%\n\n");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erreur !");
		}
	}

}
