package ita.homi.DAO;

import ita.homi.model.Cliente;
import ita.homi.model.Gruppo;
import ita.homi.model.Relati;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ClienteDAO {

	private static final String GETALLCLIENTI = "SELECT * FROM cliente";
	private static final String GETGRUPPOCLIENTI = "SELECT * FROM cliente,relati WHERE cliente.codice=relati.codicepersona AND  relati.codicegruppo = ? ";
	private static final String LISTACLIENTIIMMISSIBILI = "SELECT * FROM cliente WHERE codice NOT IN (SELECT codicepersona FROM relati WHERE codicegruppo = ? )";
	private static final String COUNT_ALL = "SELECT COUNT(*) FROM cliente ";
	private static final String COUNT_CLIENTIGRUPPO = "SELECT COUNT(*) FROM cliente,relati WHERE cliente.codice = relati.codicepersona AND relati.codicegruppo = ? ";
	private static final String UPDATE = "INSERT INTO relati (codicegruppo , codicepersona) VALUES( ? , ?) ";
	private static final String DELETE = "DELETE FROM relati WHERE codicegruppo= ? AND codicepersona = ? ";
	
	public List<Cliente> getResults(ResultSet rs) throws SQLException {
		List<Cliente> elenco = new ArrayList<Cliente>();
		while (rs.next()) {
			Cliente model = new Cliente();
			model.setCodice(rs.getInt("codice"));
			model.setRagione(rs.getString("ragione"));
			model.setIndirizzo(rs.getString("indirizzo"));
			model.setCitta(rs.getString("citta"));
			model.setTelefono(rs.getString("telefono"));
			model.setMail(rs.getString("mail"));
			model.setCategoria(rs.getString("categoria"));

			elenco.add(model);
		}

		return elenco;
	}

	public List<Cliente> getAllClienti(Connection connection)
			throws SQLException {
		// _log.info("Invoco la query: " + GETALLADVICE);
		PreparedStatement stmt = connection.prepareStatement(GETALLCLIENTI);
		ResultSet rs = stmt.executeQuery();
		List<Cliente> elenco = getResults(rs);
		// _log.debug("Recuperati " + elenco.size() + " elementi");
		rs.close();
		stmt.close();

		return elenco;
	}

	public List<Cliente> getNonGruppoClienti(Connection connection,
			Gruppo gruppo) throws SQLException {
		// _log.info("Invoco la query: " + GETALLADVICE);
		PreparedStatement stmt = connection
				.prepareStatement(LISTACLIENTIIMMISSIBILI);
		int i = 1;
		stmt.setInt(i++, gruppo.getCodice());
		ResultSet rs = stmt.executeQuery();
		List<Cliente> elenco = getResults(rs);
		// _log.debug("Recuperati " + elenco.size() + " elementi");
		rs.close();
		stmt.close();

		return elenco;
	}

	public List<Cliente> getGruppoClienti(Connection connection, Gruppo gruppo)
			throws SQLException {
		// _log.info("Invoco la query: " + GETALLADVICE);
		PreparedStatement stmt = connection.prepareStatement(GETGRUPPOCLIENTI);
		int i = 1;
		stmt.setInt(i++, gruppo.getCodice());
		ResultSet rs = stmt.executeQuery();
		List<Cliente> elenco = getResults(rs);
		// _log.debug("Recuperati " + elenco.size() + " elementi");
		rs.close();
		stmt.close();

		return elenco;
	}

	public int countClienti(Connection connection)
			throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(COUNT_ALL);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		int count = rs.getInt(1);

		rs.close();
		stmt.close();

		return count;
	}
	

	public int countClientiGruppo(Connection connection, Gruppo gruppo)
			throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(COUNT_CLIENTIGRUPPO);
		int i = 1;
		stmt.setInt(i++, gruppo.getCodice());
		ResultSet rs = stmt.executeQuery();
		rs.next();
		int count = rs.getInt(1);

		rs.close();
		stmt.close();

		return count;
	}
	
	public int update(Connection connection, Relati relati)
			throws SQLException {

		PreparedStatement stmt = connection.prepareStatement(UPDATE);
		int i = 1;
		stmt.setInt(i++, relati.getCodicegruppo());
		stmt.setInt(i++, relati.getCodicepersona());

		stmt.executeUpdate();

		stmt.close();

		return i;

	}

	
	public int delete(Connection connection, Relati relati)
			throws SQLException {

		PreparedStatement stmt = connection.prepareStatement(DELETE);
		int i = 1;
		stmt.setInt(i++, relati.getCodicegruppo());
		stmt.setInt(i++, relati.getCodicepersona());
		stmt.executeUpdate();

		stmt.close();

		return i;

	}
	
	
}
