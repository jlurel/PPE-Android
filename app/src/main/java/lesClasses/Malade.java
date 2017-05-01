package lesClasses;

public class Malade {
	//attributs
	private int id;
	private String nom;
	private String prenom;
	private double glycemie;

	//constructeur
	public Malade(String unNom, String unPrenom, double uneGlycemie){
		this.nom = unNom;
		this.prenom = unPrenom;
		this.glycemie = uneGlycemie;
	}

	//retourne l'id du malade
	public int getId() {
		return this.id;
	}

	//permet de modifier l'id du malade
	public void setId(int unId){
		this.id = unId;
	}

	//retourne le nom du malade
	public String getNom() {
		return this.nom;
	}

	//retourne le prenom du malade
	public String getPrenom() {
		return this.prenom;
	}

	//retourne la glycemie du malade
	public double getGlycemie() {
		return this.glycemie;
	}
}
