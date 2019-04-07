package ita.homi.manager;

import ita.homi.DAO.OrdineDAO;
import ita.homi.model.Gruppo;
import ita.homi.model.Ordine;
import ita.homi.model.Prodotto;
import ita.homi.utils.DBConnector;
import ita.homi.utils.GestoreException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

public class OrdineManager {
	
	private static OrdineDAO dao = new OrdineDAO();

	public static List<Ordine> getAllOrdini() throws GestoreException{
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			List<Ordine> elenco = dao.getAllOrdini(connection);
			connection.close();

			return elenco;
		} catch (NamingException e) {
			//_log.fatal(e);
			throw new GestoreException(e);
		} catch (SQLException e) {
			//_log.error(e);
			throw new GestoreException(e);
		}
	}

	public static List<Ordine> getOrdine(int numero) throws GestoreException{
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			List<Ordine> elenco = dao.getOrdine(numero, connection);
			connection.close();

			return elenco;
		} catch (NamingException e) {
			//_log.fatal(e);
			throw new GestoreException(e);
		} catch (SQLException e) {
			//_log.error(e);
			throw new GestoreException(e);
		}
	}
	
	public static int countAllOrdini() throws GestoreException {
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			int count = dao.countAllOrdini(connection);
			connection.close();
			return count;
		} catch (NamingException e) {
			throw new GestoreException(e);
		} catch (SQLException e) {
			throw new GestoreException(e);
		}
	}
	
	public static List<Ordine> getProdottiOrdine(int ordine) throws GestoreException{
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			List<Ordine> elenco = dao.getProdottiOrdine(ordine,connection);
			connection.close();

			return elenco;
		} catch (NamingException e) {
			//_log.fatal(e);
			throw new GestoreException(e);
		} catch (SQLException e) {
			//_log.error(e);
			throw new GestoreException(e);
		}
	}
	
	public static Ordine insert(Ordine ordine) throws GestoreException {
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			ordine = dao.insert(ordine, connection);
			connection.close();
			return ordine;
		} catch (NamingException e) {
			throw new GestoreException(e);
		} catch (SQLException e) {
			throw new GestoreException(e);
		}
	}
	
	
	public static Ordine update(Ordine ordine) throws GestoreException {
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			ordine = dao.update(ordine, connection);
			connection.close();
			return ordine;
		} catch (NamingException e) {
			throw new GestoreException(e);
		} catch (SQLException e) {
			throw new GestoreException(e);
		}
	}
	

	public static int deleteProdottoOrdinato(int ordineattuale,int ordine) throws GestoreException {
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			int deleted = dao.deleteProdottoOrdinato(ordineattuale,ordine, connection);
			connection.close();
			return deleted;
		} catch (NamingException e) {
			throw new GestoreException(e);
		} catch (SQLException e) {
			throw new GestoreException(e);
		}
	}
	

	
	public static int deleteOrdine(int ordineattuale) throws GestoreException {
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			int deleted = dao.deleteOrdine(ordineattuale, connection);
			connection.close();
			return deleted;
		} catch (NamingException e) {
			throw new GestoreException(e);
		} catch (SQLException e) {
			throw new GestoreException(e);
		}
	}
}
