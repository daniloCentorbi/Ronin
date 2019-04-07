package ita.homi.DAO;

import ita.homi.model.Ordine;
import ita.homi.model.Prodotto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAO {

	private static final String GETALLPRODOTTI = "SELECT * FROM prodotto WHERE codiceproduttore = 1";
	private static final String GETALLPRODOTTINONORDINE = "SELECT * FROM prodotto WHERE codiceproduttore = 1 AND prodotto.codice NOT IN (SELECT codiceprodotto FROM ordine WHERE numero = ? ) ";	
	private static final String GETORDINEATTUALE = "SELECT * FROM ordine,prodotto WHERE ordine.codiceprodotto = prodotto.codice AND ordine.numero = ?";	
	
	public List<Prodotto> getResults(ResultSet rs) throws SQLException {
		List<Prodotto> elenco = new ArrayList<Prodotto>();
		while (rs.next()) {
			Prodotto model = new Prodotto();
			model.setCodice(rs.getInt("codice"));
			model.setCodiceproduttore(rs.getInt("codiceproduttore"));			
			model.setDescrizione(rs.getString("descrizione"));
			model.setConfezione(rs.getString("confezione"));
			model.setUnita(rs.getInt("unita"));
			model.setPrezzo(rs.getDouble("prezzo"));
			
			elenco.add(model);
		}
		return elenco;
	}	

	
	public List<Prodotto> getAllProdotti(Connection connection) throws SQLException {
		
		PreparedStatement stmt = connection.prepareStatement(GETALLPRODOTTI);
		ResultSet rs = stmt.executeQuery();
		List<Prodotto> elenco = getResults(rs);

		rs.close();
		stmt.close();

		return elenco;
	}

	
	public List<Prodotto> getAllProdottiNonOrdine(int numeroordine, Connection connection) throws SQLException {
		
		PreparedStatement stmt = connection.prepareStatement(GETALLPRODOTTINONORDINE);
		int i = 1;
		stmt.setInt(i++, numeroordine);
		
		ResultSet rs = stmt.executeQuery();
		List<Prodotto> elenco = getResults(rs);

		rs.close();
		stmt.close();

		return elenco;
	}
}