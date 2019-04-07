package ita.homi.model;

import java.util.Date;

public class DettaglioOrdine {

	int numeroordine;
	int codicecliente;
	int codiceprodotto;
	String descrizioneprodotto;
	double prezzoprodotto;
	int unitaprodotto;
	int quantita;
	double sconto1;
	double sconto2;
	double sconto3;
	double scontopagamento;
	double scontoazienda;
	Date data;

	public DettaglioOrdine() {
		this(0, 0, 0, "", 0.0, 0, 0, 0.0, 0.0, 0, 0, 0.0, null);
	}

	public DettaglioOrdine(int numeroordine, int codicecliente,
			int codiceprodotto, String descrizioneprodotto,
			double prezzoprodotto, int unitaprodotto, int quantita,
			double sconto1, double sconto2, double sconto3,
			double scontopagamento, double scontoazienda, Date data) {
		this.numeroordine = numeroordine;
		this.codicecliente = codicecliente;
		this.codiceprodotto = codiceprodotto;
		this.descrizioneprodotto = descrizioneprodotto;
		this.prezzoprodotto = prezzoprodotto;
		this.unitaprodotto = unitaprodotto;
		this.quantita = quantita;
		this.sconto1 = sconto1;
		this.sconto1 = sconto2;
		this.sconto1 = sconto3;
		this.sconto1 = scontopagamento;
		this.sconto1 = scontoazienda;
		this.data = data;
	}

	public int getNumeroordine() {
		return numeroordine;
	}

	public void setNumeroordine(int numeroordine) {
		this.numeroordine = numeroordine;
	}

	public int getCodicecliente() {
		return codicecliente;
	}

	public void setCodicecliente(int codicecliente) {
		this.codicecliente = codicecliente;
	}

	public int getCodiceprodotto() {
		return codiceprodotto;
	}

	public void setCodiceprodotto(int codiceprodotto) {
		this.codiceprodotto = codiceprodotto;
	}

	public String getDescrizioneprodotto() {
		return descrizioneprodotto;
	}

	public void setDescrizioneprodotto(String descrizioneprodotto) {
		this.descrizioneprodotto = descrizioneprodotto;
	}

	public double getPrezzoprodotto() {
		return prezzoprodotto;
	}

	public void setPrezzoprodotto(double prezzoprodotto) {
		this.prezzoprodotto = prezzoprodotto;
	}

	public int getUnitaprodotto() {
		return unitaprodotto;
	}

	public void setUnitaprodotto(int unitaprodotto) {
		this.unitaprodotto = unitaprodotto;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
