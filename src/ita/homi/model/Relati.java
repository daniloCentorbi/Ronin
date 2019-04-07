package ita.homi.model;

public class Relati {

	int codicegruppo;
	int codicepersona;
	
	public Relati() {
		this(0,0);

	}

	public Relati(int codicegruppo, int codicepersona) {
		// TODO Auto-generated constructor stub
		this.codicegruppo = codicegruppo;
		this.codicepersona = codicepersona;

	}

	public int getCodicegruppo() {
		return codicegruppo;
	}

	public void setCodicegruppo(int codicegruppo) {
		this.codicegruppo = codicegruppo;
	}

	public int getCodicepersona() {
		return codicepersona;
	}

	public void setCodicepersona(int codicepersona) {
		this.codicepersona = codicepersona;
	}
	
}
