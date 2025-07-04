package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	private static final String URL = "jdbc:mysql://localhost:3306/upn?serverTimezone=UTC";
	private static final String USUARIO = "root";
	private static final String PASSWORD = "";

	public static Connection conectar() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(URL, USUARIO, PASSWORD);
			System.out.println("✅ Conectado correctamente.");
		} catch (SQLException e) {
			System.out.println("❌ Error al conectar: " + e.getMessage());
		}
		return con;
	}
}