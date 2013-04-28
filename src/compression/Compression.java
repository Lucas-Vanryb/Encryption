package compression;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import codage.CodageStandard;
import codage.ExceptionCodageImpossible;
import decodage.DecodageStandard;
import decodage.ExceptionDecodageImpossible;

/**
 * Classe module � appeler de l'ext�rieur pour r�aliser les op�ration de compression.
 * @author Lucas
 *
 */
public class Compression {

	/**
	 * Permet d'encoder une cha�ne de caract�re � l'aide de l'algorithme de Huffman 
	 * et de l'�crire sur un Stream pass� en param�tre.
	 * @param s la String � encoder
	 * @param out le Stream sur lequel encoder
	 * @throws IOException si il y a une erreur d'�criture sur le stream fourni
	 * @throws ExceptionCodageImpossible si le texte encod� est plus long que le texte source
	 */
	public static void encoderStringToStream(String s, OutputStream out) throws IOException,ExceptionCodageImpossible {
		new CodageStandard(s,out);
	}

	/**
	 * Permet d'encoder un objet � lire via un InputStream � l'aide de l'algorithme de Huffman 
	 * et de l'�crire sur un Stream pass� en param�tre.
	 * @param in le Stream � lire, puis � encoder
	 * @param out le Stream sur lequel encoder
	 * @throws IOException si il y a une erreur de lecture ou d'ecriture sur les stream fournis
	 * @throws ExceptionCodageImpossible si le texte encod� est plus long que le texte source
	 */
	public static void encoderStreamToStream(InputStream in, OutputStream out) throws IOException,ExceptionCodageImpossible {
		new CodageStandard(in,out);
	}
	
	/**
	 * Permet d'encoder une cha�ne de caract�re � l'aide de l'algorithme de Huffman
	 * et de retourner une String correspondant � ce texte encod�
	 * @param s la String � encoder
	 * @throws ExceptionCodageImpossible si le texte encod� est plus long que le texte source
	 * @return le texte encod� sous forme de String
	 */
	public static String encoderStringToString(String s) throws ExceptionCodageImpossible {
		CodageStandard c=new CodageStandard(s);
		return c.getStringResult();
	}

	/**
	 * Permet d'encoder un objet � lire via un InputStream � l'aide de l'algorithme de Huffman
	 * et de retourner une String correspondant � ce texte encod�.
	 * @param in le Stream � lire, puis � encoder
	 * @throws IOException si il y a une erreur de lecture sur le stream fournis
	 * @throws ExceptionCodageImpossible si le texte encod� est plus long que le texte source
	 * @return le texte encod� sous forme de String
	 */
	public static String encoderStreamToString(InputStream in) throws IOException,ExceptionCodageImpossible {
		CodageStandard c=new CodageStandard(in);
		return c.getStringResult();
	}
	
	/**
	 * Permet de decoder une cha�ne de caract�re � l'aide de l'algorithme de Huffman 
	 * et de l'�crire sur un Stream pass� en param�tre.
	 * @param s la String � decoder
	 * @param out le Stream sur lequel decoder
	 * @throws IOException si il y a une erreur d'�criture sur le stream fourni
	 * @throws ExceptionDecodageImpossible si la cha�ne en param�tre ne contient pas un texte encod� avec l'algorithme d'Huffman
	 */
	public static void decoderStringToStream(String s, OutputStream out) throws IOException, ExceptionDecodageImpossible {
		new DecodageStandard(s,out);
	}

	/**
	 * Permet de decoder un objet � lire via un InputStream � l'aide de l'algorithme de Huffman 
	 * et de l'�crire sur un Stream pass� en param�tre.
	 * @param in le Stream � lire, puis � decoder
	 * @param out le Stream sur lequel decoder
	 * @throws IOException si il y a une erreur de lecture ou d'ecriture sur les stream fournis
	 * @throws ExceptionDecodageImpossible si le Stream pass� en param�tre ne correspond pas � un texte encod� avec l'algorithme d'Huffman
	 */
	public static void decoderStreamToStream(InputStream in, OutputStream out) throws IOException, ExceptionDecodageImpossible {
		new DecodageStandard(in,out);
	}
	
	/**
	 * Permet de decoder une cha�ne de caract�re � l'aide de l'algorithme de Huffman
	 * et de retourner une String correspondant � ce texte decod�
	 * @param s la String � decoder
	 * @return le texte decod� sous forme de String
	 * @throws ExceptionDecodageImpossible si la cha�ne en param�tre ne contient pas un texte encod� avec l'algorithme d'Huffman
	 */
	public static String decoderStringToString(String s) throws ExceptionDecodageImpossible {
		DecodageStandard c=new DecodageStandard(s);
		return c.getResultat();
	}

	/**
	 * Permet de decoder un objet � lire via un InputStream � l'aide de l'algorithme de Huffman
	 * et de retourner une String correspondant � ce texte decod�.
	 * @param in le Stream � lire, puis � decoder
	 * @throws IOException si il y a une erreur de lecture sur le stream fourni
	 * @return le texte decod� sous forme de String
	 * @throws ExceptionDecodageImpossible si le Stream pass� en param�tre ne correspond pas � un texte encod� avec l'algorithme d'Huffman
	 */
	public static String decoderStreamToString(InputStream in) throws IOException, ExceptionDecodageImpossible{
		DecodageStandard c=new DecodageStandard(in);
		return c.getResultat();
	}
}
