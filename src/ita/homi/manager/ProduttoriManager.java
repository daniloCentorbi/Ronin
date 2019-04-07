package ita.homi.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import ita.homi.DAO.ProduttoriDAO;
import ita.homi.model.Gruppo;
import ita.homi.model.Produttori;
import ita.homi.utils.DBConnector;
import ita.homi.utils.GestoreException;

public class ProduttoriManager {

	private static ProduttoriDAO dao = new ProduttoriDAO();
	
	public static List<Produttori> getAllProduttori() throws GestoreException{
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			List<Produttori> elenco = dao.getAllProduttori(connection);
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

	
}
