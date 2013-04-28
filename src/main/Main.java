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
	 * J'ai moi même effectué cette série de test et tout fonctionne comme prévu.
	 * Aucune exception non prévue ne peut être levée par le programme à part si la chaîne passée en paramètre est null
	 * @param argv
	 */
	public static void main(String[] argv) {
		try {
			System.out.println("Test de l'algorithme de Huffman par Lucas Vanryb :\n\n");
			System.out.println("1) Début des tests basiques (StringToString, textes de taille suffisante et au jeu de caractère limité)\n");
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
			System.out.println("2) Passons aux tests de lecture/écriture de fichiers");
			Compression.encoderStreamToStream(new FileInputStream(new File("source3.txt")), new FileOutputStream(new File("result3.txt")));
			Compression.decoderStreamToStream(new FileInputStream(new File("result3.txt")), new FileOutputStream(new File("testInverse3.txt")));
			System.out.println("Veuillez vérifier que les fichiers testInverse3.txt et result3.txt sont identiques\n");
			String test4=Compression.encoderStreamToString(new FileInputStream(new File("source4.txt")));
			Compression.decoderStringToStream(test4, new FileOutputStream(new File("testInverse4.txt")));
			System.out.println("Veuillez vérifier que les fichiers testInverse4.txt et source4.txt sont identiques\n");
			String test5="Nous allons effectuer un test simple en langue francaise sans accent afin de s'assurer du bon fonctionnement du code.Nous allons effectuer un test simple en langue francaise sans accent afin de s'assurer du bon fonctionnement du code.Nous allons effectuer un test simple en langue francaise sans accent afin de s'assurer du bon fonctionnement du code.";
			System.out.println("Encodage puis décodage de "+test5+" en passant par un fichier texte\n");
			Compression.encoderStringToStream(test5, new FileOutputStream(new File("result5.txt")));
			System.out.println(Compression.decoderStreamToString(new FileInputStream(new File("result5.txt"))));
			System.out.println("\n\n--------------------\n\n");
		}
		catch(IOException e) {
			System.out.println("Erreur de Lecture/Ecriture : Vérifiez que les fichiers source3.txt et source4.txt sont bien dans le buildpath !");
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

		String paragraphe="Dans ce partie, nous allons particulièrement nous intéresser à l’erreur sur les paramètres initiaux de la simulation, et nous allons proposer une solution pour en partie pallier cette erreur. Il convient tout d’abord pour cette étude d’affirmer que le système étudié n’est pas chaotique, c’est à dire qu’un léger changement sur les paramètres initiaux de la simulation n’engendrera qu’un léger ajustement sur les résultats. Ce résultat est ici évident aux vues des méthodes de calcul employées dans notre simulation. Il convient ensuite de déterminer les paramètres sur lesquels il existe une erreur initiale. Notre simulation se base sur conq paramètres ayant une influence sur l’erreur (en effet, les dimensions du cube sont celle de son moule, qui a été construit avec précision) : la conductivité thermique du plâtre, le coefficient de convection, la température imposée à l’intérieur de la cavité, le nombre de mailles utilisées pour la discrétisation, et enfin le paramètre epsilon déterminant la précision que l’on cherche à atteindre en fin de simulation. Sur ces paramètres, seuls les trois premiers, qui sont les paramètres physiques du problème, vont nous intéresser pour la suite de l’étude. En effet, les deux derniers sont propres à la simulation, et n’ont de l’incidence que sur la précision du résultat (et pas sur sa valeur moyenne). Il nous faut ensuite un point de mesure permettant de comparer la simulation et l’expérience. Ce point de comparaison sera la température au milieu exact de la face inférieure du cube de plâtre, correspondant  à l’emplacement d’une des deux sondes de température placées dans notre maquette expérimentale.";
		System.out.println("Etude du gain de place sur un texte simple de "+paragraphe.length()+" caractères : "+paragraphe);
		try {
			System.out.println("Caractères économisés :"+ (paragraphe.length()-Compression.encoderStringToString(paragraphe).length()) + " Taux de compression :"+((0.0+Compression.encoderStringToString(paragraphe).length())/paragraphe.length()*100)+"%\n\n");
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
			System.out.println("Veuillez vérifier que les fichier textCodageUni1.txt et textCodageUni2.txt sont dans le buildpath ! Erreur de lecture !");
		}
		
		try {
			String testUni="La poésie est un genre littéraire très ancien aux formes variées, écrites généralement en vers, mais qui admet aussi la prose, et qui privilégie l'expressivité de la forme. Sa définition se révèle difficile, et varie selon les époques, au point que chaque siècle peut lui trouver une fonction et une expression différente aussi d'un poète à l'autre.";
			System.out.println("Encodage de "+testUni);
			String resultUni=CompressionUniverselle.encoderUniversellementStringToString(testUni);
			System.out.println(resultUni.substring(0,7)+".....");
			System.out.println("Décodage :");
			String testInverseUni=CompressionUniverselle.decoderUniversellementStringToString(resultUni);
			System.out.println(testInverseUni);
			System.out.println("Caractères économisés :"+ (testUni.length()-resultUni.length()) + " Taux de compression :"+((0.0+resultUni.length())/testUni.length()*100)+"%\n\n");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erreur !");
		}
	}

}
