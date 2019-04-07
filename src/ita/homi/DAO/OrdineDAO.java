package ita.homi.DAO;


import ita.homi.model.Ordine;
import ita.homi.model.Prodotto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrdineDAO {

	private static final String GETALLORDINI = "SELECT * FROM ordine";
	private static final String GETORDINE = "SELECT * FROM ordine WHERE numero = ? ";
	private static final String COUNTALLORDINI = "SELECT max(numero) as last FROM ordine ";
	private static final String INSERT = "INSERT INTO ordine (numero,codicecliente,codiceprodotto,quantita, prezzo) VALUES (?,?,?,?,?)";
	private static final String UPDATE = "UPDATE ordine SET prezzo = ? ,  quantita = ? WHERE numero = ? AND codiceprodotto = ?";
	private static final String GETORDINEATTUALE = "SELECT * FROM ordine WHERE ordine.numero = ?";	
	private static final String DELETEPRODOTTO = "DELETE FROM ordine WHERE numero = ? AND codiceprodotto = ? ";
	private static final String DELETEORDINE = "DELETE FROM ordine WHERE numero = ? ";
	
	
	public List<Ordine> getResults(ResultSet rs) throws SQLException {
		List<Ordine> elenco = new ArrayList<Ordine>();
		while (rs.next()) {
			Ordine model = new Ordine();
			model.setNumero(rs.getInt("numero"));
			model.setCodicecliente(rs.getInt("codicecliente"));
			model.setCodiceprodotto(rs.getInt("codiceprodotto"));
			model.setQuantita(rs.getInt("quantita"));
			model.setPrezzo(rs.getDouble("prezzo"));
			model.setSconto1(rs.getDouble("sconto1"));
			model.setSconto2(rs.getDouble("sconto2"));
			model.setSconto3(rs.getDouble("sconto3"));
			model.setScontopagamento(rs.getDouble("scontopagamento"));
			model.setScontoazienda(rs.getDouble("scontoazienda"));

			elenco.add(model);
		}
	
		return elenco;
	}
	
	
	
	public List<Ordine> getAllOrdini(Connection connection) throws SQLException {
		//_log.info("Invoco la query: " + GETALLADVICE);
		PreparedStatement stmt = connection.prepareStatement(GETALLORDINI);
		ResultSet rs = stmt.executeQuery();
		List<Ordine> elenco = getResults(rs);
		//_log.debug("Recuperati " + elenco.size() + " elementi");

		rs.close();
		stmt.close();

		return elenco;
	}
	
	public List<Ordine> getOrdine(int numero, Connection connection) throws SQLException {
		//_log.info("Invoco la query: " + GETALLADVICE);
		PreparedStatement stmt = connection.prepareStatement(GETORDINE);
		int i = 1;
		stmt.setInt(i++, numero);
		ResultSet rs = stmt.executeQuery();
		List<Ordine> elenco = getResults(rs);
		//_log.debug("Recuperati " + elenco.size() + " elementi");

		rs.close();
		stmt.close();

		return elenco;
	}
	
	public int countAllOrdini(Connection connection)
			throws SQLException {

		PreparedStatement stmt = connection.prepareStatement(COUNTALLORDINI);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		int count = rs.getInt(1);
		
		rs.close();
		stmt.close();

		return count;
	}
	
	public List<Ordine> getProdottiOrdine(int ordine, Connection connection) throws SQLException {
		
		PreparedStatement stmt = connection.prepareStatement(GETORDINEATTUALE);
		int i = 1;
		stmt.setInt(i++, ordine);
		ResultSet rs = stmt.executeQuery();
		List<Ordine> elenco = getResults(rs);

		rs.close();
		stmt.close();

		return elenco;
	}
	
	
	public Ordine insert(Ordine ordine, Connection connection)
			throws SQLException {

		PreparedStatement stmt = connection.prepareStatement(INSERT,
				Statement.RETURN_GENERATED_KEYS);
		int i = 1;
		stmt.setInt(i++, ordine.getNumero());		
		stmt.setInt(i++, ordine.getCodicecliente());		
		stmt.setInt(i++, ordine.getCodiceprodotto());
		stmt.setInt(i++, ordine.getQuantita());
		stmt.setDouble(i++, ordine.getPrezzo());
		stmt.executeUpdate();

		stmt.close();

		return ordine;
	}
	

	public Ordine update(Ordine ordine, Connection connection)
			throws SQLException {

		PreparedStatement stmt = connection.prepareStatement(UPDATE,
				Statement.RETURN_GENERATED_KEYS);
		int i = 1;
		stmt.setDouble(i++, ordine.getPrezzo());	
		stmt.setInt(i++, ordine.getQuantita());
		stmt.setInt(i++, ordine.getNumero());
		stmt.setInt(i++, ordine.getCodiceprodotto());	
		stmt.executeUpdate();

		stmt.close();

		return ordine;
	}
	
	
	public int deleteProdottoOrdinato(int ordineattuale, int ordine, Connection connection)
			throws SQLException {

		PreparedStatement stmt = connection.prepareStatement(DELETEPRODOTTO);
		int i = 1;
		stmt.setInt(i++, ordineattuale);
		stmt.setInt(i++, ordine);
		int deleted = stmt.executeUpdate();

		stmt.close();

		return deleted;
	}

	
	
	public int deleteOrdine(int ordineattuale, Connection connection)
			throws SQLException {

		PreparedStatement stmt = connection.prepareStatement(DELETEORDINE);
		int i = 1;
		stmt.setInt(i++, ordineattuale);
		int deleted = stmt.executeUpdate();

		stmt.close();

		return deleted;
	}
	
}
