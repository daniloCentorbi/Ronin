package ita.homi.manager;


import ita.homi.DAO.ClienteDAO;
import ita.homi.model.Cliente;
import ita.homi.model.Gruppo;
import ita.homi.model.Relati;
import ita.homi.utils.DBConnector;
import ita.homi.utils.GestoreException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;



public class ClienteManager {

	private static ClienteDAO dao = new ClienteDAO();
	
	
	public static List<Cliente> getAllClienti() throws GestoreException{
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			List<Cliente> elenco = dao.getAllClienti(connection);
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

	
	public static List<Cliente> getGruppoClienti(Gruppo gruppo) throws GestoreException{
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			List<Cliente> elenco = dao.getGruppoClienti(connection, gruppo);
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
	
	
	public static List<Cliente> getNonGruppoClienti(Gruppo gruppo) throws GestoreException{
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			List<Cliente> elenco = dao.getNonGruppoClienti(connection, gruppo);
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

	
	
	public static int CountClienti() throws GestoreException{
		int campi;
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			campi = dao.countClienti(connection);
			connection.close();

		} catch (NamingException e) {
			//_log.fatal(e);
			throw new GestoreException(e);
		} catch (SQLException e) {
			//_log.error(e);
			throw new GestoreException(e);
		}
		
		return campi;
	}
	
	
	public static int CountClientiGruppo(Gruppo gruppo) throws GestoreException{
		int campi;
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			campi = dao.countClientiGruppo(connection,gruppo);
			connection.close();

		} catch (NamingException e) {
			//_log.fatal(e);
			throw new GestoreException(e);
		} catch (SQLException e) {
			//_log.error(e);
			throw new GestoreException(e);
		}
		
		return campi;
	}
	
	public static int update(Relati relati) throws GestoreException{
		int campi;
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			campi = dao.update(connection,relati);
			connection.close();

		} catch (NamingException e) {
			//_log.fatal(e);
			throw new GestoreException(e);
		} catch (SQLException e) {
			//_log.error(e);
			throw new GestoreException(e);
		}
		
		return campi;
	}
	
	
	public static int delete(Relati relati) throws GestoreException{
		int campi;
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			campi = dao.delete(connection,relati);
			connection.close();

		} catch (NamingException e) {
			//_log.fatal(e);
			throw new GestoreException(e);
		} catch (SQLException e) {
			//_log.error(e);
			throw new GestoreException(e);
		}
		
		return campi;
	}

	
	
}
