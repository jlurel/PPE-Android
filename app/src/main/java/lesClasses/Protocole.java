package lesClasses;

import java.util.ArrayList;

public class Protocole {
	private int numeroProtocole;
	private ArrayList<GlycemieInsuline> lesGlycemieInsuline;
	
	public Protocole(int unNumero)
	{
		this.numeroProtocole = unNumero;
		lesGlycemieInsuline = new ArrayList<>();
	}
	
	public int getNumeroProtocole()
	{
		return this.numeroProtocole;
	}
	
	public void ajouter(GlycemieInsuline uneGlycemieInsuline)
	{
		this.lesGlycemieInsuline.add(uneGlycemieInsuline);
	}

	public ArrayList<String> getLesInsuline() {
		ArrayList<String> lesInsuline = new ArrayList<>();
		ArrayList<GlycemieInsuline> lesGI = this.lesGlycemieInsuline;
		int nb = lesGI.size();
		for (int i = 0; i < nb; i++)
		{
			String lInsuline = String.valueOf(lesGI.get(i).getInsuline());
			lesInsuline.add(lInsuline);
		}
		return lesInsuline;
	}

	public int insuline(double uneGlycemie)
	{
		//A écrire
		//Renvoie le nombre d'unités d'insuline en fonction de la glycémie
		int nbGlycemieInsuline = this.lesGlycemieInsuline.size();
		int lInsuline = 0;
		double laGlycemieInf, laGlycemieSup;
		for (int i = 0; i < nbGlycemieInsuline; i++)
		{
			GlycemieInsuline laGlycemieInsuline = this.lesGlycemieInsuline.get(i);
			laGlycemieInf = laGlycemieInsuline.getGlycemieInf();
			laGlycemieSup = laGlycemieInsuline.getGlycemieSup();
			if ((uneGlycemie >= laGlycemieInf) && (uneGlycemie <= laGlycemieSup))
			{
				lInsuline = laGlycemieInsuline.getInsuline();
				break;
			}
		}
		return lInsuline;
	}
}
