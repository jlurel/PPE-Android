package lesClasses;

import java.util.HashMap;

public class MapProtocoles {
	private HashMap<Integer, Protocole> lesProtocoles;
	
	public MapProtocoles()
	{
		this.lesProtocoles = new HashMap<Integer, Protocole>();
	}
	
	public void initialiser()
	{
		Protocole leProtocole = new Protocole(1);
		GlycemieInsuline uneGlycemieInsuline;
		uneGlycemieInsuline = new GlycemieInsuline(1.0, 1.5, 0);
		leProtocole.ajouter(uneGlycemieInsuline);
		uneGlycemieInsuline = new GlycemieInsuline(1.5, 2.0, 5);
		leProtocole.ajouter(uneGlycemieInsuline);
		uneGlycemieInsuline = new GlycemieInsuline(2.0, 2.5, 10);
		leProtocole.ajouter(uneGlycemieInsuline);
		uneGlycemieInsuline = new GlycemieInsuline(2.5, 10, 15);
		leProtocole.ajouter(uneGlycemieInsuline);
		this.lesProtocoles.put(leProtocole.getNumeroProtocole(), leProtocole);
		leProtocole = new Protocole(2);
		uneGlycemieInsuline = new GlycemieInsuline(1.0, 1.2, 0);
		leProtocole.ajouter(uneGlycemieInsuline);
		uneGlycemieInsuline = new GlycemieInsuline(1.2, 1.5, 4);
		leProtocole.ajouter(uneGlycemieInsuline);
		uneGlycemieInsuline = new GlycemieInsuline(1.5, 1.8, 8);
		leProtocole.ajouter(uneGlycemieInsuline);
		uneGlycemieInsuline = new GlycemieInsuline(1.8, 2.1, 12);
		leProtocole.ajouter(uneGlycemieInsuline);
		uneGlycemieInsuline = new GlycemieInsuline(2.1, 2.4, 16);
		leProtocole.ajouter(uneGlycemieInsuline);
		uneGlycemieInsuline = new GlycemieInsuline(2.4, 2.7, 20);
		leProtocole.ajouter(uneGlycemieInsuline);
		uneGlycemieInsuline = new GlycemieInsuline(2.7, 3.0, 24);
		leProtocole.ajouter(uneGlycemieInsuline);
		uneGlycemieInsuline = new GlycemieInsuline(3.0, 10, 30);
		leProtocole.ajouter(uneGlycemieInsuline);
		this.lesProtocoles.put(leProtocole.getNumeroProtocole(), leProtocole);		
	}
	
	public Protocole getProtocole(int unNumero)
	{
		return this.lesProtocoles.get(unNumero);
	}

}
