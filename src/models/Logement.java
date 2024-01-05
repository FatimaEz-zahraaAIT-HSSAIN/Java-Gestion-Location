package models;

public class Logement {
	
	int id;
	String adr;
	int superficie;
	int loyer;
	String type;
	String region;
	
	public Logement() {
		super();
	}
	
	public Logement(int id, String adr, int sup, int l, String t, String r) {
		this.id = id;
		this.adr = adr;
		this.superficie = sup;
		this.loyer = l;
		this.type = t;
		this.region = r;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdr() {
		return adr;
	}

	public void setAdr(String adr) {
		this.adr = adr;
	}

	public int getSuperficie() {
		return superficie;
	}

	public void setSuperficie(int superficie) {
		this.superficie = superficie;
	}

	public int getLoyer() {
		return loyer;
	}

	public void setLoyer(int loyer) {
		this.loyer = loyer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	

}
