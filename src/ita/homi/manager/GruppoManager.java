package ita.homi.manager;


import ita.homi.DAO.GruppoDAO;
import ita.homi.model.Gruppo;
import ita.homi.utils.DBConnector;
import ita.homi.utils.GestoreException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;


public class GruppoManager {

	private static GruppoDAO dao = new GruppoDAO();
	
	
	public static List<Gruppo> getAll() throws GestoreException{
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			List<Gruppo> elenco = dao.getAll(connection);
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

	
	
	public static Gruppo insert(Gruppo gruppo) throws GestoreException {
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			gruppo = dao.insert(gruppo, connection);
			connection.close();
			return gruppo;
		} catch (NamingException e) {
			throw new GestoreException(e);
		} catch (SQLException e) {
			throw new GestoreException(e);
		}
	}

	
	
}
