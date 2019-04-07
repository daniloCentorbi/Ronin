package ita.homi.delegate;



import java.util.List;

import ita.homi.manager.ClienteManager;
import ita.homi.manager.ComunicazioneManager;
import ita.homi.manager.DettaglioOrdineManager;
import ita.homi.manager.EventManager;
import ita.homi.manager.GruppoManager;
import ita.homi.manager.OrdineManager;
import ita.homi.manager.ProdottoManager;
import ita.homi.manager.ProduttoriManager;
import ita.homi.model.CalendarTestEvent;
import ita.homi.model.Cliente;
import ita.homi.model.Comunicazione;
import ita.homi.model.DettaglioOrdine;
import ita.homi.model.Gruppo;
import ita.homi.model.Ordine;
import ita.homi.model.Prodotto;
import ita.homi.model.Produttori;
import ita.homi.model.Relati;
import ita.homi.utils.GestoreException;

public class BusinessDelegate {

	//CLIENTI
	public synchronized List<Cliente> getAllClienti() throws GestoreException {
		return ClienteManager.getAllClienti();
	}
	public synchronized List<Cliente> getGruppoClienti(Gruppo gruppo) throws GestoreException {
		return ClienteManager.getGruppoClienti(gruppo);
	}	
	public synchronized List<Cliente> getNonGruppoClienti(Gruppo gruppo) throws GestoreException {
		return ClienteManager.getNonGruppoClienti(gruppo);
	}	
	public synchronized int CountClientiGruppo(Gruppo gruppo) throws GestoreException {
		return ClienteManager.CountClientiGruppo(gruppo);
	}
	public synchronized int CountClienti() throws GestoreException {
		return ClienteManager.CountClienti();
	}
	public synchronized int update(Relati relati) throws GestoreException {
		return ClienteManager.update(relati);
	}	
	public synchronized int delete(Relati relati) throws GestoreException {
		return ClienteManager.delete(relati);
	}	
	
	//GRUPPI
	public synchronized List<Gruppo> getAllGruppi() throws GestoreException {
		return GruppoManager.getAll();
	}
	public synchronized Gruppo insert(Gruppo gruppo) throws GestoreException {
		return GruppoManager.insert(gruppo);
	}	
	
	//PRODUTTORI
	public synchronized List<Produttori> getAllProduttori() throws GestoreException {
		return ProduttoriManager.getAllProduttori();
	}
	
	//COMUNICAZIONI
	public synchronized List<Comunicazione> getAllComunicazioni() throws GestoreException {
		return ComunicazioneManager.getAllComunicazioni();
	}
	public synchronized List<Comunicazione> getClienteComunicazioni(String destinatario) throws GestoreException {
		return ComunicazioneManager.getClienteComunicazioni(destinatario);
	}	
	public synchronized List<Comunicazione> getClientiPerComunicazioni() throws GestoreException {
		return ComunicazioneManager.getClientiPerComunicazioni();
	}	
	public synchronized Comunicazione insert(Comunicazione comunicazione) throws GestoreException {
		return ComunicazioneManager.insert(comunicazione);
	}	
	public synchronized int delete(Comunicazione comunicazione) throws GestoreException {
		return ComunicazioneManager.delete(comunicazione);
	}	
	
	
	//PRODOTTI
	public synchronized List<Prodotto> getAllProdotti() throws GestoreException {
		return ProdottoManager.getAllProdotti();
	}
	public synchronized List<Prodotto> getAllProdottiNonOrdine(int numeroordine) throws GestoreException {
		return ProdottoManager.getAllProdottiNonOrdine(numeroordine);
	}	
	
	//DETTAGLIOORDINE
	
	public synchronized List<DettaglioOrdine> getDettaglioProdottiOrdine(int ordine) throws GestoreException {
		return DettaglioOrdineManager.getDettaglioProdottiOrdine(ordine);
	}
	
	
	
	public synchronized int deleteProdottoOrdinato(int ordineattuale, int ordine) throws GestoreException {
		return OrdineManager.deleteProdottoOrdinato(ordineattuale, ordine);
	}	
	public synchronized int deleteOrdine(int ordineattuale) throws GestoreException {
		return OrdineManager.deleteOrdine(ordineattuale);
	}	
	
	//ORDINI
	public synchronized List<Ordine> getProdottiOrdine(int ordine) throws GestoreException {
		return OrdineManager.getProdottiOrdine(ordine);
	}
	
	public synchronized List<Ordine> getAllOrdini() throws GestoreException {
		return OrdineManager.getAllOrdini();
	}
	public synchronized List<Ordine> getOrdine(int numero) throws GestoreException {
		return OrdineManager.getOrdine(numero);
	}
	public synchronized int countAllOrdini() throws GestoreException {
		return OrdineManager.countAllOrdini();
	}
	public synchronized Ordine insert(Ordine ordine) throws GestoreException {
		return OrdineManager.insert(ordine);
	}
	
	public synchronized Ordine update(Ordine ordine) throws GestoreException {
		return OrdineManager.update(ordine);
	}
	

	
	
	//EVENT
	public synchronized List<CalendarTestEvent> getEvent(int numero) throws GestoreException {
		return EventManager.getEvent(numero);
	}
	public synchronized List<CalendarTestEvent> getEventFromOrdine(Ordine ordine) throws GestoreException {
		return EventManager.getEventFromOrdine(ordine);
	}
	public synchronized List<CalendarTestEvent> getSelectedEvent(CalendarTestEvent event) throws GestoreException {
		return EventManager.getSelectedEvent(event);
	}
	public synchronized List<CalendarTestEvent> getDeleteEvent(String username) throws GestoreException {
		return EventManager.getDeleteEvent(username);
	}
	public synchronized List<CalendarTestEvent> getEventFromUser(String tousername) throws GestoreException{
		return EventManager.getEventFromUser(tousername);
	}
	
	
	/*
	public synchronized CalendarTestEvent insert(CalendarTestEvent event) throws AdviceException {
		return EventManager.insert(event);
	}
	public synchronized int delete(CalendarTestEvent event) throws AdviceException {
		return EventManager.delete(event);
	}
	public synchronized CalendarTestEvent update(CalendarTestEvent event) throws AdviceException {
		return EventManager.update(event);
	}
	public synchronized CalendarTestEvent predelete(CalendarTestEvent event) throws AdviceException {
		return EventManager.predelete(event);
	}
	public synchronized CalendarTestEvent confirm(CalendarTestEvent event) throws AdviceException {
		return EventManager.confirm(event);
	}
	public synchronized int countAll() throws AdviceException {
		return EventManager.countAll();
	}
	public synchronized int countUser(String username) throws AdviceException {
		return EventManager.countUser(username);
	}
	public synchronized int countModifiedUser(String username) throws AdviceException {
		return EventManager.countModifiedUser(username);
	}
	public synchronized int countFromUser(String username) throws AdviceException {
		return EventManager.countFromUser(username);
	}
	public synchronized int countNewEvent(String username) throws AdviceException {
		return EventManager.countNewEvent(username);
	}
	public synchronized int countForOrdine(Advice advice) throws AdviceException {
		return EventManager.countForAdvice(advice);
	}	
	
	*/
	
}
