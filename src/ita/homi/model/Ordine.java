package ita.homi.model;


public class Ordine {

	int numero;
	int codicecliente;
	int codiceprodotto;
	int quantita;
	Double prezzo;
	double sconto1;
	double sconto2;
	double sconto3;
	double scontopagamento;
	double scontoazienda;
	
	public Ordine(){
	  this(0,0,0,0,0.0,0.0,0.0,0,0,0.0);	
	}

	public Ordine(int numero, int codicecliente, int codiceprodotto, int quantita, Double prezzo, double sconto1,double sconto2, double sconto3,double scontopagamento,double scontoazienda) {
		this.numero = numero;
		this.codicecliente = codicecliente; 
		this.codiceprodotto = codiceprodotto;
		this.quantita= quantita;
		this.prezzo = prezzo;
		this.sconto1 = sconto1;
		this.sconto1 = sconto2;
		this.sconto1 = sconto3;
		this.sconto1 = scontopagamento;
		this.sconto1 = scontoazienda;
	}
	
	
	//mi serve nel edit/delete ordine
	public Ordine(int numero,int codicecliente, int codiceprodotto,int quantita,double prezzo) {
		this.numero = numero;
		this.codicecliente = codicecliente;
		this.codiceprodotto = codiceprodotto;
		this.prezzo = prezzo;
		this.quantita = quantita;
	}

	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public int getCodiceprodotto() {
		return codiceprodotto;
	}
	public void setCodiceprodotto(int codiceprodotto) {
		this.codiceprodotto = codiceprodotto;
	}
	public Double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}
	public int getQuantita() {
		return quantita;
	}
	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public int getCodicecliente() {
		return codicecliente;
	}

	public void setCodicecliente(int codicecliente) {
		this.codicecliente = codicecliente;
	}

	public double getSconto1() {
		return sconto1;
	}

	public void setSconto1(double sconto1) {
		this.sconto1 = sconto1;
	}

	public double getSconto2() {
		return sconto2;
	}

	public void setSconto2(double sconto2) {
		this.sconto2 = sconto2;
	}

	public double getSconto3() {
		return sconto3;
	}

	public void setSconto3(double sconto3) {
		this.sconto3 = sconto3;
	}

	public double getScontopagamento() {
		return scontopagamento;
	}

	public void setScontopagamento(double scontopagamento) {
		this.scontopagamento = scontopagamento;
	}

	public double getScontoazienda() {
		return scontoazienda;
	}

	public void setScontoazienda(double scontoazienda) {
		this.scontoazienda = scontoazienda;
	}
	
}
