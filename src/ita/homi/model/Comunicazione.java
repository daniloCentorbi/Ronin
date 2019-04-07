package ita.homi.model;

import java.util.Date;

import javax.mail.Multipart;
import javax.mail.internet.MimeMultipart;

public class Comunicazione {
	int codice;
	String destinatario;
	String oggetto;
	String corpo;
	Date data;
	Multipart corpoHtml;
	
	public Comunicazione(){
		this(0,"","","", null);
	}
	
	public Comunicazione(int codice, String destinatario, String oggetto, String corpo,
			Date data) {
		this.codice = codice;
		this.destinatario = destinatario;
		this.oggetto = oggetto;
		this.corpo = corpo;
		this.data = data;
	}
	
	public Comunicazione(int codice, String destinatario, String oggetto, Multipart corpoHtml,
			Date data) {
		this.codice = codice;
		this.destinatario = destinatario;
		this.oggetto = oggetto;
		this.corpoHtml = corpoHtml;
		this.data = data;
	}
	
	public Comunicazione(String destinatario, String oggetto, String corpo,
			Date data) {
		this.destinatario = destinatario;
		this.oggetto = oggetto;
		this.corpo = corpo;
		this.data = data;
	}

	public Comunicazione(String destinatario, String oggetto) {
		this.destinatario = destinatario;
		this.oggetto = oggetto;

	}
	
	public int getCodice() {
		return codice;
	}
	public void setCodice(int codice) {
		this.codice = codice;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getCorpo() {
		return corpo;
	}
	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}
	public Date getDate() {
		return data;
	}
	public void setDate(Date date) {
		this.data = date;
	}

}
