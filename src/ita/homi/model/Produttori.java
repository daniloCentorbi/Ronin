package ita.homi.model;

public class Produttori {

	int codice;
	String produttore;
	String citta;
	String indirizzo;
	int telefono;
	String mail;
	String storia;
	String sito;
	String foto;
	
	public Produttori(){
		this(0,"","","",0,"","","","");
	}
	
	public Produttori(int codice, String produttore, String citta, String indirizzo, int telefono, 
			String mail, String storia, String sito, String foto){
		this.codice = codice;
		this.produttore = produttore;
		this.citta = citta;
		this.indirizzo = indirizzo;
		this.telefono = telefono;
		this.mail = mail;
		this.storia = storia;
		this.sito = sito;
		this.foto = foto;
	}
	
	public int getCodice() {
		return codice;
	}
	public void setCodice(int codice) {
		this.codice = codice;
	}
	public String getProduttore() {
		return produttore;
	}
	public void setProduttore(String produttore) {
		this.produttore = produttore;
	}
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getStoria() {
		return storia;
	}
	public void setStoria(String storia) {
		this.storia = storia;
	}
	public String getSito() {
		return sito;
	}
	public void setSito(String sito) {
		this.sito = sito;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
}
