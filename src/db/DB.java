package db;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class DB {
	// Objeto de conexão com o banco
	private static Connection conn = null;

	// Realiza a conexão com o banco
	public static Connection getConnection() {
		if (conn == null) {
			Properties props = loadProperties();
			String url = props.getProperty("dburl");
			try {
				conn = DriverManager.getConnection(url, props);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

	// Obtem as credenciais de acesso ao banco no arquivo de propriedades
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (Exception e) {
			return null;
		}
	}

	// Encerra a conexão com o banco
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Encerra o PreparedStatement
	public static void closeStatement(PreparedStatement st) {
		if (st != null) {
			try {
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Encerra o ResultSet
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
