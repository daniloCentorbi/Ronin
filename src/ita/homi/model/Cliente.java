package ita.homi.model;

public class Cliente {

	int codice;
	String ragione;
	String indirizzo;
	int cap;
	String citta;
	String provincia;
	int piva;
	String codicefiscale;
	String telefono;
	String fax;
	String mail;
	String pec;
	String sito;
	String categoria;
	String username;
	String password;
	
	//solamente per la tabella di ClientiTable(non è una row di 'db1')
	Boolean selezionato = true;


	public Cliente() {
		this(0,"","",0,"","",0,"","","","","","","","","");

	}

	public Cliente(int codice, String ragione, String indirizzo, int cap, String citta,String provincia,int piva,String codicefiscale, String telefono, 
			String fax, String mail, String pec, String sito, String categoria,String username, String password) {
		// TODO Auto-generated constructor stub
		this.codice = codice;
		this.ragione = ragione;
		this.indirizzo = indirizzo;
		this.cap=cap;
		this.citta=citta;
		this.provincia=provincia;
		this.piva=piva;
		this.codicefiscale=codicefiscale;
		this.telefono = telefono;
		this.fax=fax;
		this.mail = mail;
		this.pec=pec;
		this.sito=sito;
		this.categoria=categoria;
		this.username=username;
		this.password=password;
	}
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Cliente(int codice, String ragione, int piva, String telefono, 
			String mail, String categoria) {
		// TODO Auto-generated constructor stub
		this.codice = codice;
		this.ragione = ragione;
		this.piva=piva;
		this.telefono = telefono;
		this.mail = mail;
		this.categoria=categoria;
	}

	public Cliente(int codice, String ragione,String mail) {
		// TODO Auto-generated constructor stub
		this.codice = codice;
		this.ragione = ragione;
		this.mail = mail;
	}
	
	public Cliente(Cliente o) {
		this.codice = o.getCodice();
		this.ragione = o.getRagione();
		this.indirizzo = o.getIndirizzo();
		this.cap=o.getCap();
		this.citta=o.getCitta();
		this.provincia=o.getProvincia();
		this.piva=o.getPiva();
		this.codicefiscale=o.getCodicefiscale();
		this.telefono = o.getTelefono();
		this.fax=o.getFax();
		this.mail = o.getMail();
		this.pec=o.getPec();
		this.sito=o.getSito();
		this.categoria=o.getCategoria();
	}

	public int getCodice() {
		return codice;
	}

	public void setCodice(int codice) {
		this.codice = codice;
	}

	public String getRagione() {
		return ragione;
	}

	public void setRagione(String ragione) {
		this.ragione = ragione;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public int getCap() {
		return cap;
	}

	public void setCap(int cap) {
		this.cap = cap;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public int getPiva() {
		return piva;
	}

	public void setPiva(int piva) {
		this.piva = piva;
	}

	public String getCodicefiscale() {
		return codicefiscale;
	}

	public void setCodicefiscale(String codicefiscale) {
		this.codicefiscale = codicefiscale;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getSito() {
		return sito;
	}

	public void setSito(String sito) {
		this.sito = sito;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


	public Boolean getSelezionato() {
		return selezionato;
	}

	public void setSelezionato(Boolean selezionato) {
		this.selezionato = selezionato;
	}
	
	public Boolean isSelezionato() {
		return selezionato;
	}
}
