package ita.homi.model;

public class Prodotto {

	int codice;
	int codiceproduttore;
	String descrizione;
	String confezione;
	int unita;
	double prezzo;
	
	public Prodotto(){
		this(0,0,"","",0,0.0);
	}
	
	
	public Prodotto(int codice, int codiceproduttore, String descrizione, String confezione,int unita, double prezzo) {
		this.codice=codice;
		this.codiceproduttore=codiceproduttore;
		this.descrizione=descrizione;
		this.confezione=confezione;
		this.unita = unita;
		this.prezzo=prezzo;
	}



	public Prodotto(int codice, String descrizione,String confezione, int unita, double prezzo) {
		this.codice=codice;
		this.descrizione=descrizione;
		this.confezione=confezione;
		this.unita=unita;
		this.prezzo=prezzo;
	}


	
	
	public int getCodice() {
		return codice;
	}
	public void setCodice(int codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getConfezione() {
		return confezione;
	}
	public void setConfezione(String confezione) {
		this.confezione = confezione;
	}
	public int getUnita() {
		return unita;
	}
	public void setUnita(int unita) {
		this.unita = unita;
	}
	public int getCodiceproduttore() {
		return codiceproduttore;
	}
	public void setCodiceproduttore(int codiceproduttore) {
		this.codiceproduttore = codiceproduttore;
	}
	public double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	
}
