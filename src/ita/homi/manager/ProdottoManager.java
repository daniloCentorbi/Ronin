package ita.homi.manager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import ita.homi.DAO.ProdottoDAO;
import ita.homi.model.Gruppo;
import ita.homi.model.Ordine;
import ita.homi.model.Prodotto;
import ita.homi.utils.DBConnector;
import ita.homi.utils.GestoreException;

public class ProdottoManager {

	private static ProdottoDAO dao = new ProdottoDAO();
	
	public static List<Prodotto> getAllProdotti() throws GestoreException{
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			List<Prodotto> elenco = dao.getAllProdotti(connection);
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

	public static List<Prodotto> getAllProdottiNonOrdine(int numeroordine) throws GestoreException{
		try {
			Connection connection = DBConnector.getInstance().getConnection();
			List<Prodotto> elenco = dao.getAllProdottiNonOrdine(numeroordine, connection);
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
