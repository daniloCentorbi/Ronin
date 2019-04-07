package ita.homi.DAO;

import ita.homi.model.Gruppo;
import ita.homi.model.Produttori;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProduttoriDAO {

	private static final String GETALLPRODUTTORI = "SELECT * FROM produttori";
	
	
	public List<Produttori> getResults(ResultSet rs) throws SQLException {
		List<Produttori> elenco = new ArrayList<Produttori>();
		while (rs.next()) {
			Produttori model = new Produttori();
			model.setCodice(rs.getInt("codice"));
			model.setProduttore(rs.getString("produttore"));			
			model.setCitta(rs.getString("citta"));
			model.setIndirizzo(rs.getString("indirizzo"));
			model.setTelefono(rs.getInt("telefono"));
			model.setMail(rs.getString("mail"));
			model.setStoria(rs.getString("storia"));
			model.setSito(rs.getString("sito"));
			model.setFoto(rs.getString("foto"));
			
			elenco.add(model);
		}
		return elenco;
	}	

	
	public List<Produttori> getAllProduttori(Connection connection) throws SQLException {
		
		PreparedStatement stmt = connection.prepareStatement(GETALLPRODUTTORI);
		ResultSet rs = stmt.executeQuery();
		List<Produttori> elenco = getResults(rs);

		rs.close();
		stmt.close();

		return elenco;
	}
	
}
