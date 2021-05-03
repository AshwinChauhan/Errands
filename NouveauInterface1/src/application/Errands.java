package application;

public class Errands 
{
	private String Nom;
	private String Prix;
	private String Util;
	private Double Dist;
	
	
	//constructeur vide
	public Errands()
	{
		this(null,null);
	}
	
	//Constructeur avec 2 parametre
	public Errands(String prenom, String nom)
	{
		this.Nom=Nom;
		this.Prix=Prix;
		this.Util="";
		this.Dist=0.0;
	}
	
	//Setters et Getters
	public String getNom() {
		return Nom;
	}
	public void setNom(String Nom) {
		this.Nom = Nom;
	}
	public String getPrix() {
		return Prix;
	}
	public void setPrix(String Prix) {
		this.Prix = Prix;
	}
	public String getUtil() {
		return Util;
	}
	public void setUtil(String Util) {
		this.Util = Util;
	}
	public Double getDist() {
		return Dist;
	}
	public void setDist(Double Dist) {
		this.Dist = Dist;
	}

}
