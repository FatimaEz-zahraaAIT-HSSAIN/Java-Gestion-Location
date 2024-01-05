package models;

import java.sql.Date;

public class Locataire {
	int id;
	String nomPrenom;
	Date dateDeNaissance;
	String tele;
	String cin;
	
	
	public Locataire() {
		super();
	}
	
	public Locataire(int id, String np, Date d, String t, String c) {
		this.id = id;
		this.nomPrenom = np;
		this.dateDeNaissance = d;
		this.tele = t;
		this.cin = c;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomPrenom() {
		return nomPrenom;
	}

	public void setNomPrenom(String nomPrenom) {
		this.nomPrenom = nomPrenom;
	}

	public Date getDateDeNaissance() {
		return dateDeNaissance;
	}

	public void setDateDeNaissance(Date dateDeNaissance) {
		this.dateDeNaissance = dateDeNaissance;
	}

	public String getTele() {
		return tele;
	}

	public void setTele(String tele) {
		this.tele = tele;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}
	
	
}
