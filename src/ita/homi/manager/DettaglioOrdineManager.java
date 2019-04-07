package ita.homi.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import ita.homi.DAO.DettaglioOrdineDAO;
import ita.homi.model.DettaglioOrdine;
import ita.homi.utils.DBConnector;
import ita.homi.utils.GestoreException;


public class DettaglioOrdineManager {

	private static DettaglioOrdineDAO dao = new DettaglioOrdineDAO();
	
	public static List<DettaglioOrdine> getDettaglioProdottiOrdine(int ordine) throws GestoreException{
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			List<DettaglioOrdine> elenco = dao.getDettaglioProdottiOrdine(ordine,connection);
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
