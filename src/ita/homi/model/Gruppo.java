package ita.homi.model;

public class Gruppo {
	int codice;
	String titolo;
	int numero;

	public Gruppo() {
		this(0, "" , 0 );
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Gruppo(Gruppo gruppo) {
		this.codice = gruppo.getCodice();
		this.titolo = gruppo.getTitolo();
	}

	public Gruppo(int codice, String titolo, int numero) {
		this.codice = codice;
		this.titolo = titolo;
		this.numero=numero;
	}

	public Gruppo(String titolo) {
		this.titolo = titolo;
	}

	public Gruppo(int codice) {
		this.codice = codice;
	}

	public int getCodice() {
		return codice;
	}

	public void setCodice(int codice) {
		this.codice = codice;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

}
