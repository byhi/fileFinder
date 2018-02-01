package founder;

public class Termek implements Comparable<Termek>{
private String bolt, nev ;
private int ar;
	
	
	public Termek(String boltnev, String termekNev, int termekAr) {
	this.bolt = boltnev;
	this.nev = termekNev;
	this.ar = termekAr;
}



	public String getBolt() {
	return bolt;
	}
	
	
	
	public void setBolt(String bolt) {
		this.bolt = bolt;
	}
	
	
	
	public String getNev() {
		return nev;
	}
	
	
	
	public void setNev(String nev) {
		this.nev = nev;
	}
	
	
	
	public int getAr() {
		return ar;
	}
	
	
	
	public void setAr(int ar) {
		this.ar = ar;
	}

	

	@Override
	public String toString() {
		return "Termek [bolt=" + bolt + ", nev=" + nev + ", ar=" + ar + "]";
	}



	/*@Override
	public int compareTo(Termek o) {
	//	String egyik = this.nev + this.ar , masik = o.getNev() +o.getAr();
		
		return  Integer.compare( o.getAr(),this.ar);
	}*/

	@Override
	public int compareTo(Termek o) {
		String egyik = this.nev + this.ar , masik = o.getNev() +o.getAr();
		return egyik.compareTo(masik);
	}

}
