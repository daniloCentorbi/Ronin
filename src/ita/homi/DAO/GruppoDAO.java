package ita.homi.DAO;



import ita.homi.model.Gruppo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class GruppoDAO {
	
	private static final String GETALLGRUPPI = "SELECT * FROM gruppo";
	private static final String INSERT = "INSERT INTO gruppo (titolo) VALUES (?)";
	
	
	public List<Gruppo> getResults(ResultSet rs) throws SQLException {
		List<Gruppo> elenco = new ArrayList<Gruppo>();
		while (rs.next()) {
			Gruppo model = new Gruppo();
			model.setCodice(rs.getInt("codice"));
			model.setTitolo(rs.getString("titolo"));

			elenco.add(model);
		}
	
		return elenco;
	}
	
	
	
	public List<Gruppo> getAll(Connection connection) throws SQLException {
		//_log.info("Invoco la query: " + GETALLADVICE);
		PreparedStatement stmt = connection.prepareStatement(GETALLGRUPPI);
		ResultSet rs = stmt.executeQuery();
		List<Gruppo> elenco = getResults(rs);
		//_log.debug("Recuperati " + elenco.size() + " elementi");

		rs.close();
		stmt.close();

		return elenco;
	}
	
	
	public Gruppo insert(Gruppo gruppo, Connection connection)
			throws SQLException {

		PreparedStatement stmt = connection.prepareStatement(INSERT,
				Statement.RETURN_GENERATED_KEYS);
		int i = 1;
		stmt.setString(i++, gruppo.getTitolo());
		stmt.executeUpdate();

		stmt.close();

		return gruppo;
	}
	
	
}
