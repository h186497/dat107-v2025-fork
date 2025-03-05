package no.hvl.dat107;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main3_FindPersonById {

	static final String JDBC_DRIVER = "org.postgresql.Driver";

	static final String DATABASE = "";   // Endre til din databae = ditt brukernavn
	static final String BRUKERNAVN = ""; // Endre til ditt brukernavn
	static final String PASSORD = "";    // Endre til ditt passord
	
	static final String TJENER_OG_PORT = "ider-database.westeurope.cloudapp.azure.com:5433";
	static final String DB_URL = "jdbc:postgresql://" + TJENER_OG_PORT + "/" + DATABASE;

	public static void main(String[] args) throws ClassNotFoundException {

		Class.forName(JDBC_DRIVER);

		Person p = finnPersonMedId(1001);

		System.out.println(p);

	}

	public static Person finnPersonMedId(int id) {

		String sql = "SELECT id, navn FROM forelesning1.person WHERE id=" + id;

		Person p = null;

		try (Connection conn = DriverManager.getConnection(DB_URL, BRUKERNAVN, PASSORD);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			if (rs.next()) {
				p = new Person();
				p.setId(rs.getInt("id"));
				p.setNavn(rs.getString("navn"));
			}

			return p;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
