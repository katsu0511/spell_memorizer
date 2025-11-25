package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import config.AppConfig;

public class SpellMemorizerDAO {
	private final String DSN = AppConfig.get("db.url");
	private final String USER = AppConfig.get("db.user");
	private final String PASSWORD = AppConfig.get("db.pass");
	
	public Connection getConnection() {
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DSN, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void close(ResultSet rset) {
		if (rset != null) {
			try {
				rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
