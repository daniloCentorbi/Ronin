package ita.homi.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ita.homi.model.Cliente;
import ita.homi.model.Comunicazione;

public class ComunicazioneDAO {
	private static final String GETALLCOMUNICAZIONI = "SELECT * FROM comunicazione ";
	private static final String GETCLIENTECOMUNICAZIONI = "SELECT * FROM comunicazione WHERE destinatario = ? ";
	private static final String GETCLIENTEPERCOMUNICAZIONI = "SELECT DISTINCT destinatario,codice, oggetto, corpo, data FROM comunicazione ";
	private static final String INSERT = "INSERT INTO comunicazione (destinatario,oggetto,corpo,data) VALUES (?,?,?,?) ";
	private static final String DELETE = "DELETE FROM comunicazione WHERE codice = ? ";

	
	public List<Comunicazione> getResults(ResultSet rs) throws SQLException {
		List<Comunicazione> elenco = new ArrayList<Comunicazione>();
		while (rs.next()) {
			Comunicazione model = new Comunicazione();
			model.setCodice(rs.getInt("codice"));
			model.setDestinatario(rs.getString("destinatario"));
			model.setOggetto(rs.getString("oggetto"));
			model.setCorpo(rs.getString("corpo"));
			model.setDate(rs.getDate("data"));

			elenco.add(model);
		}
		return elenco;
	}
	
	
	public List<Comunicazione> getAllComunicazioni(Connection connection)
			throws SQLException {

		PreparedStatement stmt = connection.prepareStatement(GETALLCOMUNICAZIONI);
		ResultSet res = stmt.executeQuery();
		List<Comunicazione> elenco = getResults(res);

		res.close();
		stmt.close();

		return elenco;
	}
	
	
	public List<Comunicazione> getClientiPerComunicazioni(Connection connection)
			throws SQLException {

		PreparedStatement stmt = connection.prepareStatement(GETCLIENTEPERCOMUNICAZIONI);
		ResultSet res = stmt.executeQuery();
		
		List<Comunicazione> elenco = getResults(res);

		res.close();
		stmt.close();

		return elenco;
	}
	
	public List<Comunicazione> getClienteComunicazioni(Connection connection , String destinatario)
			throws SQLException {

		PreparedStatement stmt = connection.prepareStatement(GETCLIENTECOMUNICAZIONI);
		int i = 1;
		stmt.setString(i++, destinatario);
		ResultSet res = stmt.executeQuery();
		List<Comunicazione> elenco = getResults(res);
		
		res.close();
		stmt.close();

		return elenco;
	}

	

	public Comunicazione insert(Connection connection, Comunicazione comunicazione)
			throws SQLException {
		
		PreparedStatement stmt = connection.prepareStatement(INSERT,
				Statement.RETURN_GENERATED_KEYS);
		int i = 1;
		stmt.setString(i++, comunicazione.getDestinatario());
		stmt.setString(i++, comunicazione.getOggetto());
		stmt.setString(i++, comunicazione.getCorpo());		
		stmt.setTimestamp(i++, new java.sql.Timestamp(comunicazione.getDate().getTime()));
		
		stmt.executeUpdate();
		stmt.close();

		return comunicazione;
	}
	
	
	public int delete(Connection connection, Comunicazione comunicazione)
			throws SQLException {

		PreparedStatement stmt = connection.prepareStatement(DELETE);
		int i = 1;
		stmt.setInt(i++, comunicazione.getCodice());
		int deleted = stmt.executeUpdate();
		
		stmt.close();

		return deleted;
	}
	
	
}
