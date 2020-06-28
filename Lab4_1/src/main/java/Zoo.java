import java.io.Serializable;

public class Zoo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String zwierze;
	private int nr_klatki;

	public Zoo(String zwierze, int nr_klatki) {
		this.zwierze = zwierze;
		this.nr_klatki = nr_klatki;
	}

	public String getZwierze() {
		return zwierze;
	}
	
	public void setZwierze(String zwierze) {
		this.zwierze = zwierze;
	}

	public int getNr_klatki() {
		return nr_klatki;
	}
	
	public void setNr_klatki(int nr_klatki) {
		this.nr_klatki = nr_klatki;
	}
	
	@Override
	public String toString(){
		return "Zwierze: " + zwierze + " Nr klatki: " + nr_klatki;
	}
}
