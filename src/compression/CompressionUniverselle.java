package compression;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import codage.CodageUniversel;
import codage.ExceptionCodageImpossible;
import decodage.DecodageUniversel;
import decodage.ExceptionDecodageImpossible;

/**
 * Classe module � appeler de l'ext�rieur pour r�aliser les op�ration de compression.
 * @author Lucas
 *
 */
public class CompressionUniverselle {

	/**
	 * Permet d'encoderUniversellement une cha�ne de caract�re � l'aide de l'algorithme de Huffman 
	 * et de l'�crire sur un Stream pass� en param�tre.
	 * @param s la String � encoder universellement
	 * @param out le Stream sur lequel encoder universellement
	 * @throws IOException si il y a une erreur d'�criture sur le stream fourni
	 * @throws ExceptionCodageImpossible si le texte encod� est plus long que le texte source
	 */
	public static void encoderUniversellementStringToStream(String s, OutputStream out) throws IOException,ExceptionCodageImpossible {
		new CodageUniversel(s,out);
	}

	/**
	 * Permet d'encoderUniversellement un objet � lire via un InputStream � l'aide de l'algorithme de Huffman 
	 * et de l'�crire sur un Stream pass� en param�tre.
	 * @param in le Stream � lire, puis � encoder universellement
	 * @param out le Stream sur lequel encoder universellement
	 * @throws IOException si il y a une erreur de lecture ou d'ecriture sur les stream fournis
	 * @throws ExceptionCodageImpossible si le texte encod� est plus long que le texte source
	 */
	public static void encoderUniversellementStreamToStream(InputStream in, OutputStream out) throws IOException,ExceptionCodageImpossible {
		new CodageUniversel(in,out);
	}
	
	/**
	 * Permet d'encoder universellement une cha�ne de caract�re � l'aide de l'algorithme de Huffman
	 * et de retourner une String correspondant � ce texte encod�
	 * @param s la String � encoder universellement
	 * @throws ExceptionCodageImpossible si le texte encod� est plus long que le texte source
	 * @return le texte encod� sous forme de String
	 */
	public static String encoderUniversellementStringToString(String s) throws ExceptionCodageImpossible {
		CodageUniversel c=new CodageUniversel(s);
		return c.getStringResult();
	}

	/**
	 * Permet d'encoder universellement un objet � lire via un InputStream � l'aide de l'algorithme de Huffman
	 * et de retourner une String correspondant � ce texte encod�.
	 * @param in le Stream � lire, puis � encoder universellement
	 * @throws IOException si il y a une erreur de lecture sur le stream fournis
	 * @throws ExceptionCodageImpossible si le texte encod� est plus long que le texte source
	 * @return le texte encod� sous forme de String
	 */
	public static String encoderUniversellementStreamToString(InputStream in) throws IOException,ExceptionCodageImpossible {
		CodageUniversel c=new CodageUniversel(in);
		return c.getStringResult();
	}
	
	/**
	 * Permet de decoder universellement une cha�ne de caract�re � l'aide de l'algorithme de Huffman 
	 * et de l'�crire sur un Stream pass� en param�tre.
	 * @param s la String � decoder universellement
	 * @param out le Stream sur lequel decoderUniversellement
	 * @throws IOException si il y a une erreur d'�criture sur le stream fourni
	 * @throws ExceptionDecodageImpossible si la cha�ne en param�tre ne contient pas un texte encod� avec l'algorithme d'Huffman
	 */
	public static void decoderUniversellementStringToStream(String s, OutputStream out) throws IOException, ExceptionDecodageImpossible {
		new DecodageUniversel(s,out);
	}

	/**
	 * Permet de decoder universellement un objet � lire via un InputStream � l'aide de l'algorithme de Huffman 
	 * et de l'�crire sur un Stream pass� en param�tre.
	 * @param in le Stream � lire, puis � decoder universellement
	 * @param out le Stream sur lequel decoderUniversellement
	 * @throws IOException si il y a une erreur de lecture ou d'ecriture sur les stream fournis
	 * @throws ExceptionDecodageImpossible si le Stream pass� en param�tre ne correspond pas � un texte encod� avec l'algorithme d'Huffman
	 */
	public static void decoderUniversellementStreamToStream(InputStream in, OutputStream out) throws IOException, ExceptionDecodageImpossible {
		new DecodageUniversel(in,out);
	}
	
	/**
	 * Permet de decoder universellement une cha�ne de caract�re � l'aide de l'algorithme de Huffman
	 * et de retourner une String correspondant � ce texte decod�
	 * @param s la String � decoder universellement
	 * @return le texte decod� sous forme de String
	 * @throws ExceptionDecodageImpossible si la cha�ne en param�tre ne contient pas un texte encod� avec l'algorithme d'Huffman
	 */
	public static String decoderUniversellementStringToString(String s) throws ExceptionDecodageImpossible {
		DecodageUniversel c=new DecodageUniversel(s);
		return c.getResultat();
	}

	/**
	 * Permet de decoder universellement un objet � lire via un InputStream � l'aide de l'algorithme de Huffman
	 * et de retourner une String correspondant � ce texte decod�.
	 * @param in le Stream � lire, puis � decoder universellement
	 * @throws IOException si il y a une erreur de lecture sur le stream fourni
	 * @return le texte decod� sous forme de String
	 * @throws ExceptionDecodageImpossible si le Stream pass� en param�tre ne correspond pas � un texte encod� avec l'algorithme d'Huffman
	 */
	public static String decoderUniversellementStreamToString(InputStream in) throws IOException, ExceptionDecodageImpossible{
		DecodageUniversel c=new DecodageUniversel(in);
		return c.getResultat();
	}
	
	/**
	 * M�thode permettant d'ajouter un texte au corus de texte �tudi� pour les frequences sous forme de String
	 * @param texte le texte � ajouter sous forme de String
	 */
	public static void ajouterTexteString(String texte){
		CodageUniversel.ajouterTexteString(texte);
	}
	
	/**
	 * M�thode permettant d'ajouter un texte au corus de texte �tudi� pour les frequences sous forme de Stream
	 * @param texte le Stream vers le texte � ajouter
	 * @throws IOException en cas d'erreur d electure sur le Stream fourni
	 */
	public static void ajouterTexteStream(InputStream texte) throws IOException{
		CodageUniversel.ajouterTexteStream(texte);
	}
}
