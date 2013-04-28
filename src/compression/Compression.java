package compression;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import codage.CodageStandard;
import codage.ExceptionCodageImpossible;
import decodage.DecodageStandard;
import decodage.ExceptionDecodageImpossible;

/**
 * Classe module à appeler de l'extérieur pour réaliser les opération de compression.
 * @author Lucas
 *
 */
public class Compression {

	/**
	 * Permet d'encoder une chaîne de caractère à l'aide de l'algorithme de Huffman 
	 * et de l'écrire sur un Stream passé en paramètre.
	 * @param s la String à encoder
	 * @param out le Stream sur lequel encoder
	 * @throws IOException si il y a une erreur d'écriture sur le stream fourni
	 * @throws ExceptionCodageImpossible si le texte encodé est plus long que le texte source
	 */
	public static void encoderStringToStream(String s, OutputStream out) throws IOException,ExceptionCodageImpossible {
		new CodageStandard(s,out);
	}

	/**
	 * Permet d'encoder un objet à lire via un InputStream à l'aide de l'algorithme de Huffman 
	 * et de l'écrire sur un Stream passé en paramètre.
	 * @param in le Stream à lire, puis à encoder
	 * @param out le Stream sur lequel encoder
	 * @throws IOException si il y a une erreur de lecture ou d'ecriture sur les stream fournis
	 * @throws ExceptionCodageImpossible si le texte encodé est plus long que le texte source
	 */
	public static void encoderStreamToStream(InputStream in, OutputStream out) throws IOException,ExceptionCodageImpossible {
		new CodageStandard(in,out);
	}
	
	/**
	 * Permet d'encoder une chaîne de caractère à l'aide de l'algorithme de Huffman
	 * et de retourner une String correspondant à ce texte encodé
	 * @param s la String à encoder
	 * @throws ExceptionCodageImpossible si le texte encodé est plus long que le texte source
	 * @return le texte encodé sous forme de String
	 */
	public static String encoderStringToString(String s) throws ExceptionCodageImpossible {
		CodageStandard c=new CodageStandard(s);
		return c.getStringResult();
	}

	/**
	 * Permet d'encoder un objet à lire via un InputStream à l'aide de l'algorithme de Huffman
	 * et de retourner une String correspondant à ce texte encodé.
	 * @param in le Stream à lire, puis à encoder
	 * @throws IOException si il y a une erreur de lecture sur le stream fournis
	 * @throws ExceptionCodageImpossible si le texte encodé est plus long que le texte source
	 * @return le texte encodé sous forme de String
	 */
	public static String encoderStreamToString(InputStream in) throws IOException,ExceptionCodageImpossible {
		CodageStandard c=new CodageStandard(in);
		return c.getStringResult();
	}
	
	/**
	 * Permet de decoder une chaîne de caractère à l'aide de l'algorithme de Huffman 
	 * et de l'écrire sur un Stream passé en paramètre.
	 * @param s la String à decoder
	 * @param out le Stream sur lequel decoder
	 * @throws IOException si il y a une erreur d'écriture sur le stream fourni
	 * @throws ExceptionDecodageImpossible si la chaîne en paramètre ne contient pas un texte encodé avec l'algorithme d'Huffman
	 */
	public static void decoderStringToStream(String s, OutputStream out) throws IOException, ExceptionDecodageImpossible {
		new DecodageStandard(s,out);
	}

	/**
	 * Permet de decoder un objet à lire via un InputStream à l'aide de l'algorithme de Huffman 
	 * et de l'écrire sur un Stream passé en paramètre.
	 * @param in le Stream à lire, puis à decoder
	 * @param out le Stream sur lequel decoder
	 * @throws IOException si il y a une erreur de lecture ou d'ecriture sur les stream fournis
	 * @throws ExceptionDecodageImpossible si le Stream passé en paramètre ne correspond pas à un texte encodé avec l'algorithme d'Huffman
	 */
	public static void decoderStreamToStream(InputStream in, OutputStream out) throws IOException, ExceptionDecodageImpossible {
		new DecodageStandard(in,out);
	}
	
	/**
	 * Permet de decoder une chaîne de caractère à l'aide de l'algorithme de Huffman
	 * et de retourner une String correspondant à ce texte decodé
	 * @param s la String à decoder
	 * @return le texte decodé sous forme de String
	 * @throws ExceptionDecodageImpossible si la chaîne en paramètre ne contient pas un texte encodé avec l'algorithme d'Huffman
	 */
	public static String decoderStringToString(String s) throws ExceptionDecodageImpossible {
		DecodageStandard c=new DecodageStandard(s);
		return c.getResultat();
	}

	/**
	 * Permet de decoder un objet à lire via un InputStream à l'aide de l'algorithme de Huffman
	 * et de retourner une String correspondant à ce texte decodé.
	 * @param in le Stream à lire, puis à decoder
	 * @throws IOException si il y a une erreur de lecture sur le stream fourni
	 * @return le texte decodé sous forme de String
	 * @throws ExceptionDecodageImpossible si le Stream passé en paramètre ne correspond pas à un texte encodé avec l'algorithme d'Huffman
	 */
	public static String decoderStreamToString(InputStream in) throws IOException, ExceptionDecodageImpossible{
		DecodageStandard c=new DecodageStandard(in);
		return c.getResultat();
	}
}
