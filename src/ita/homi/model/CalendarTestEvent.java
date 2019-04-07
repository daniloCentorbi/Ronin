package ita.homi.model;

import java.util.Date;

import com.vaadin.ui.components.calendar.event.BasicEvent;

/**
 * Test CalendarEvent implementation.
 * 
 * @see com.vaadin.addon.calendar.Calendarr.ui.Calendar.Event
 */
public class CalendarTestEvent extends BasicEvent {

	private static final long serialVersionUID = 2820133201983036866L;
	private String where;
	private Object data;
	private int idevent;
	private int codicecliente;
	private String ragione;
	private Date startdate;
	private Date enddate;
	private String caption;	
	private String description;
	private String state;
	private String toragione;
	private int codiceordine;
	private int codicefattura;
	private String tipo;
	private String flag;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	
	public int getIdevent() {
		return idevent;
	}

	public void setIdevent(int idevent) {
		this.idevent = idevent;
	}


	public int getCodicecliente() {
		return codicecliente;
	}

	public void setCodicecliente(int codicecliente) {
		this.codicecliente = codicecliente;
	}

	public String getRagione() {
		return ragione;
	}

	public void setRagione(String ragione) {
		this.ragione = ragione;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getToragione() {
		return toragione;
	}

	public void setToragione(String toragione) {
		this.toragione = toragione;
	}

	public int getCodiceordine() {
		return codiceordine;
	}

	public void setCodiceordine(int codiceordine) {
		this.codiceordine = codiceordine;
	}

	public int getCodicefattura() {
		return codicefattura;
	}

	public void setCodicefattura(int codicefattura) {
		this.codicefattura = codicefattura;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
		fireEventChange();
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
		fireEventChange();
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date date) {
		this.startdate = date;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

}