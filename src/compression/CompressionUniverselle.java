package compression;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import codage.CodageUniversel;
import codage.ExceptionCodageImpossible;
import decodage.DecodageUniversel;
import decodage.ExceptionDecodageImpossible;

/**
 * Classe module à appeler de l'extérieur pour réaliser les opération de compression.
 * @author Lucas
 *
 */
public class CompressionUniverselle {

	/**
	 * Permet d'encoderUniversellement une chaîne de caractère à l'aide de l'algorithme de Huffman 
	 * et de l'écrire sur un Stream passé en paramètre.
	 * @param s la String à encoder universellement
	 * @param out le Stream sur lequel encoder universellement
	 * @throws IOException si il y a une erreur d'écriture sur le stream fourni
	 * @throws ExceptionCodageImpossible si le texte encodé est plus long que le texte source
	 */
	public static void encoderUniversellementStringToStream(String s, OutputStream out) throws IOException,ExceptionCodageImpossible {
		new CodageUniversel(s,out);
	}

	/**
	 * Permet d'encoderUniversellement un objet à lire via un InputStream à l'aide de l'algorithme de Huffman 
	 * et de l'écrire sur un Stream passé en paramètre.
	 * @param in le Stream à lire, puis à encoder universellement
	 * @param out le Stream sur lequel encoder universellement
	 * @throws IOException si il y a une erreur de lecture ou d'ecriture sur les stream fournis
	 * @throws ExceptionCodageImpossible si le texte encodé est plus long que le texte source
	 */
	public static void encoderUniversellementStreamToStream(InputStream in, OutputStream out) throws IOException,ExceptionCodageImpossible {
		new CodageUniversel(in,out);
	}
	
	/**
	 * Permet d'encoder universellement une chaîne de caractère à l'aide de l'algorithme de Huffman
	 * et de retourner une String correspondant à ce texte encodé
	 * @param s la String à encoder universellement
	 * @throws ExceptionCodageImpossible si le texte encodé est plus long que le texte source
	 * @return le texte encodé sous forme de String
	 */
	public static String encoderUniversellementStringToString(String s) throws ExceptionCodageImpossible {
		CodageUniversel c=new CodageUniversel(s);
		return c.getStringResult();
	}

	/**
	 * Permet d'encoder universellement un objet à lire via un InputStream à l'aide de l'algorithme de Huffman
	 * et de retourner une String correspondant à ce texte encodé.
	 * @param in le Stream à lire, puis à encoder universellement
	 * @throws IOException si il y a une erreur de lecture sur le stream fournis
	 * @throws ExceptionCodageImpossible si le texte encodé est plus long que le texte source
	 * @return le texte encodé sous forme de String
	 */
	public static String encoderUniversellementStreamToString(InputStream in) throws IOException,ExceptionCodageImpossible {
		CodageUniversel c=new CodageUniversel(in);
		return c.getStringResult();
	}
	
	/**
	 * Permet de decoder universellement une chaîne de caractère à l'aide de l'algorithme de Huffman 
	 * et de l'écrire sur un Stream passé en paramètre.
	 * @param s la String à decoder universellement
	 * @param out le Stream sur lequel decoderUniversellement
	 * @throws IOException si il y a une erreur d'écriture sur le stream fourni
	 * @throws ExceptionDecodageImpossible si la chaîne en paramètre ne contient pas un texte encodé avec l'algorithme d'Huffman
	 */
	public static void decoderUniversellementStringToStream(String s, OutputStream out) throws IOException, ExceptionDecodageImpossible {
		new DecodageUniversel(s,out);
	}

	/**
	 * Permet de decoder universellement un objet à lire via un InputStream à l'aide de l'algorithme de Huffman 
	 * et de l'écrire sur un Stream passé en paramètre.
	 * @param in le Stream à lire, puis à decoder universellement
	 * @param out le Stream sur lequel decoderUniversellement
	 * @throws IOException si il y a une erreur de lecture ou d'ecriture sur les stream fournis
	 * @throws ExceptionDecodageImpossible si le Stream passé en paramètre ne correspond pas à un texte encodé avec l'algorithme d'Huffman
	 */
	public static void decoderUniversellementStreamToStream(InputStream in, OutputStream out) throws IOException, ExceptionDecodageImpossible {
		new DecodageUniversel(in,out);
	}
	
	/**
	 * Permet de decoder universellement une chaîne de caractère à l'aide de l'algorithme de Huffman
	 * et de retourner une String correspondant à ce texte decodé
	 * @param s la String à decoder universellement
	 * @return le texte decodé sous forme de String
	 * @throws ExceptionDecodageImpossible si la chaîne en paramètre ne contient pas un texte encodé avec l'algorithme d'Huffman
	 */
	public static String decoderUniversellementStringToString(String s) throws ExceptionDecodageImpossible {
		DecodageUniversel c=new DecodageUniversel(s);
		return c.getResultat();
	}

	/**
	 * Permet de decoder universellement un objet à lire via un InputStream à l'aide de l'algorithme de Huffman
	 * et de retourner une String correspondant à ce texte decodé.
	 * @param in le Stream à lire, puis à decoder universellement
	 * @throws IOException si il y a une erreur de lecture sur le stream fourni
	 * @return le texte decodé sous forme de String
	 * @throws ExceptionDecodageImpossible si le Stream passé en paramètre ne correspond pas à un texte encodé avec l'algorithme d'Huffman
	 */
	public static String decoderUniversellementStreamToString(InputStream in) throws IOException, ExceptionDecodageImpossible{
		DecodageUniversel c=new DecodageUniversel(in);
		return c.getResultat();
	}
	
	/**
	 * Méthode permettant d'ajouter un texte au corus de texte étudié pour les frequences sous forme de String
	 * @param texte le texte à ajouter sous forme de String
	 */
	public static void ajouterTexteString(String texte){
		CodageUniversel.ajouterTexteString(texte);
	}
	
	/**
	 * Méthode permettant d'ajouter un texte au corus de texte étudié pour les frequences sous forme de Stream
	 * @param texte le Stream vers le texte à ajouter
	 * @throws IOException en cas d'erreur d electure sur le Stream fourni
	 */
	public static void ajouterTexteStream(InputStream texte) throws IOException{
		CodageUniversel.ajouterTexteStream(texte);
	}
}
