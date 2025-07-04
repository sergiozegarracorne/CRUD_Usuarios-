package app;

import java.sql.*;
import java.util.*;

public class UsuarioDAO {
	public void agregarUsuario(Usuario u) {
		String sql = "INSERT INTO usuarios(nombre, correo, edad) VALUES (?, ?, ?)";
		try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, u.getNombre());
			ps.setString(2, u.getCorreo());
			ps.setInt(3, u.getEdad());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	public List<Usuario> obtenerUsuarios() {
		List<Usuario> lista = new ArrayList<>();
		String sql = "SELECT * FROM usuarios";
		try (Connection con = Conexion.conectar();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				Usuario u = new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("correo"),
						rs.getInt("edad"));
				lista.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public void actualizarUsuario(Usuario u) {
		String sql = "UPDATE usuarios SET nombre=?, correo=?, edad=? WHERE id=?";
		try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, u.getNombre());
			ps.setString(2, u.getCorreo());
			ps.setInt(3, u.getEdad());
			ps.setInt(4, u.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void eliminarUsuario(int id) {
		String sql = "DELETE FROM usuarios WHERE id=?";
		try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}