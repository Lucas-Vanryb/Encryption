package codage;

/**
 * Exception lanc�e lorsque le texte encod� a une taille sup�rieure au texte source ou si le charset est trop grand.
 * @author Lucas
 *
 */
public class ExceptionCodageImpossible extends Exception {

	private static final long serialVersionUID = 1L;

	public ExceptionCodageImpossible(String s) {
		super(s);
	}
}
