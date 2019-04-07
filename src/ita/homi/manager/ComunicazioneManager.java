package ita.homi.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import ita.homi.DAO.ComunicazioneDAO;
import ita.homi.model.Cliente;
import ita.homi.model.Comunicazione;
import ita.homi.utils.DBConnector;
import ita.homi.utils.GestoreException;

public class ComunicazioneManager {

	private static ComunicazioneDAO dao = new ComunicazioneDAO();
	
	public static List<Comunicazione> getAllComunicazioni() throws GestoreException{
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			List<Comunicazione> elenco = dao.getAllComunicazioni(connection);
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

	
	public static List<Comunicazione> getClienteComunicazioni(String destinatario) throws GestoreException{
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			List<Comunicazione> elenco = dao.getClienteComunicazioni(connection, destinatario);
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

	public static List<Comunicazione> getClientiPerComunicazioni() throws GestoreException{
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			List<Comunicazione> elenco = dao.getClientiPerComunicazioni(connection);
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
	
	
	public static Comunicazione insert(Comunicazione comunicazione) throws GestoreException {
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			comunicazione = dao.insert(connection, comunicazione);
			connection.close();
			return comunicazione;
		} catch (NamingException e) {
			throw new GestoreException(e);
		} catch (SQLException e) {
			throw new GestoreException(e);
		}
	}
	
	public static int delete(Comunicazione comunicazione) throws GestoreException {
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			int i = dao.delete(connection, comunicazione);
			connection.close();
			return i;
		} catch (NamingException e) {
			throw new GestoreException(e);
		} catch (SQLException e) {
			throw new GestoreException(e);
		}
	}
	
	
}
