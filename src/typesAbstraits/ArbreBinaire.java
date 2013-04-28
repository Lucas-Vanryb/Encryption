package typesAbstraits;

public abstract class ArbreBinaire<N, F>
{
    public abstract boolean estFeuille();
    public abstract F feuille() throws ExceptionTypeAbstrait;
    public abstract N noeud() throws ExceptionTypeAbstrait;
    public abstract ArbreBinaire<N, F> gauche() throws ExceptionTypeAbstrait;
    public abstract ArbreBinaire<N, F> droit() throws ExceptionTypeAbstrait;
    
    public int hauteur() throws ExceptionTypeAbstrait
    {
	if(estFeuille()){ // ou this.estFeuille()
	    return 1;
	} else {
	    return 1+ (int)Math.max(gauche().hauteur(),droit().hauteur());
	    // ou ... this.gauche() ... this.droit()
       }
    }
    
    public static <N, F> ArbreBinaire<N, F> cons(N n, ArbreBinaire<N, F> g, ArbreBinaire<N, F> d)
    {
	return new Cons<N, F>(n,g,d);
    }
    public static <N, F> ArbreBinaire<N, F> feuille(F f)
    {
	return new Feuille<N, F>(f);    
    }
}



class Cons<N, F> extends ArbreBinaire<N, F>
{
    private N noeud;
    private ArbreBinaire<N, F> gauche;
    private ArbreBinaire<N, F> droit;
	
    protected Cons(N n, ArbreBinaire<N, F> g, ArbreBinaire<N, F> d)
    {
	noeud = n;
	gauche = g;
	droit = d;
    }
	
    public boolean estFeuille()
    {
	return false;
    }
    public F feuille() throws ExceptionTypeAbstrait 
    {
	throw new ExceptionTypeAbstrait("ArbreBianire.feuille() : arbre construit");
    }
    public N noeud() throws ExceptionTypeAbstrait 
    {
	return noeud;
    }
    public ArbreBinaire<N, F> gauche() throws ExceptionTypeAbstrait 
    {
	return gauche;
    }
    public ArbreBinaire<N, F> droit() throws ExceptionTypeAbstrait 
    {
	return droit;
    }
}

class Feuille<N, F> extends ArbreBinaire<N, F>
{
    private F feuille;
	
    protected Feuille(F feuille)
    {
	this.feuille = feuille;
    }
	
    public boolean estFeuille()
    {
	return true;
    }
    public F feuille() throws ExceptionTypeAbstrait 
    {
	return feuille;
    }
    public N noeud() throws ExceptionTypeAbstrait 
    {
	throw new ExceptionTypeAbstrait("ArbreBinaire.noeud() : feuille");
    }
    public ArbreBinaire<N, F> gauche() throws ExceptionTypeAbstrait 
    {
	throw new ExceptionTypeAbstrait("ArbreBinaire.gauche() : feuille");
    }
    public ArbreBinaire<N, F> droit() throws ExceptionTypeAbstrait 
    {
	throw new ExceptionTypeAbstrait("ArbreBinaire.droit() : feuille");
    }
}
