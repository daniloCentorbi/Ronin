package ita.homi.DAO;

import ita.homi.model.DettaglioOrdine;
import ita.homi.model.Ordine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DettaglioOrdineDAO {

	private static final String GETORDINEATTUALE = "SELECT ordine.numero AS numeroordine , ordine.codiceprodotto AS codiceprodotto , prodotto.descrizione AS descrizioneprodotto , prodotto.prezzo AS prezzoprodotto , prodotto.unita AS unitaprodotto , ordine.quantita AS quantita FROM `ordine` , `prodotto`  WHERE ordine.numero = ? AND ordine.codiceprodotto = prodotto.codice";	
	
	
	public List<DettaglioOrdine> getResults(ResultSet rs) throws SQLException {
		List<DettaglioOrdine> elenco = new ArrayList<DettaglioOrdine>();
		while (rs.next()) {
			DettaglioOrdine model = new DettaglioOrdine();
			model.setNumeroordine(rs.getInt("numeroordine"));
			model.setCodiceprodotto(rs.getInt("codiceprodotto"));
			model.setDescrizioneprodotto(rs.getString("descrizioneprodotto"));
			model.setPrezzoprodotto(rs.getDouble("prezzoprodotto"));
			model.setUnitaprodotto(rs.getInt("unitaprodotto"));
			model.setQuantita(rs.getInt("quantita"));


			elenco.add(model);
		}
	
		return elenco;
	}
	
	public List<DettaglioOrdine> getDettaglioProdottiOrdine(int ordine, Connection connection) throws SQLException {
		
		PreparedStatement stmt = connection.prepareStatement(GETORDINEATTUALE);
		int i = 1;
		stmt.setInt(i++, ordine);
		ResultSet rs = stmt.executeQuery();
		List<DettaglioOrdine> elenco = getResults(rs);

		rs.close();
		stmt.close();

		return elenco;
	}
	
}
