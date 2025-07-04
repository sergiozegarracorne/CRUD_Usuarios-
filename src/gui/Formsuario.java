package gui;

import app.Usuario;
import app.UsuarioDAO;


import javax.swing.*;


import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class Formsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtId, txtNombre, txtCorreo, txtEdad;
	JButton btnActualizar,btnEliminar;
	private JTable tablaUsuarios;
	private DefaultTableModel modeloTabla;
	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	public Formsuario() {
		setTitle("Gestión de Usuarios");
		setBounds(100, 100, 609, 558);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		
		JLabel label = new JLabel("ID:");
		label.setBounds(10, 51, 54, 14);
		getContentPane().add(label);
		txtId = new JTextField(5);
		txtId.setBackground(UIManager.getColor("Button.background"));
		txtId.setEditable(false);
		txtId.setBounds(74, 48, 64, 20);
		getContentPane().add(txtId);

		JLabel label_1 = new JLabel("Nombre:");
		label_1.setBounds(10, 75, 54, 14);
		getContentPane().add(label_1);
		txtNombre = new JTextField(10);
		txtNombre.setBounds(74, 75, 374, 20);
		getContentPane().add(txtNombre);

		JLabel label_2 = new JLabel("Correo:");
		label_2.setBounds(10, 100, 54, 14);
		getContentPane().add(label_2);
		txtCorreo = new JTextField(10);
		txtCorreo.setBounds(75, 103, 373, 20);
		getContentPane().add(txtCorreo);

		JLabel label_3 = new JLabel("Edad:");
		label_3.setBounds(10, 137, 54, 14);
		getContentPane().add(label_3);
		txtEdad = new JTextField(5);
		txtEdad.setBounds(74, 134, 46, 20);
		getContentPane().add(txtEdad);

		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(10, 11, 86, 23);
		btnAgregar.addActionListener(e -> agregarUsuario());
		getContentPane().add(btnAgregar);

		btnActualizar = new JButton("Actualizar");
		btnActualizar.setEnabled(false);
		btnActualizar.setBounds(122, 11, 97, 23);
		btnActualizar.addActionListener(e -> actualizarUsuario());
		getContentPane().add(btnActualizar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setEnabled(false);
		btnEliminar.setBounds(244, 11, 97, 23);
		btnEliminar.addActionListener(e -> eliminarUsuario());
		getContentPane().add(btnEliminar);

		JButton btnListar = new JButton("RECARGAR");
		btnListar.setBounds(359, 11, 102, 23);
		btnListar.addActionListener(e -> listarUsuarios());
		getContentPane().add(btnListar);

	
		String[] columnas = { "ID", "Nombre", "Correo", "Edad" };
		modeloTabla = new DefaultTableModel(columnas, 0);
		tablaUsuarios = new JTable(modeloTabla);
		JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
		scrollPane.setBounds(0, 213, 583, 306);
		scrollPane.setPreferredSize(new Dimension(550, 200));
		getContentPane().add(scrollPane);

	
		tablaUsuarios.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int filaSeleccionada = tablaUsuarios.getSelectedRow();
				if (filaSeleccionada >= 0) {
					txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
					txtNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
					txtCorreo.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
					txtEdad.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
					btnActualizar.setEnabled(true);
					btnEliminar.setEnabled(true);
					
				}
			}
		});
		setVisible(true);
	}

	private void agregarUsuario() {
		String nombre = txtNombre.getText();
		String correo = txtCorreo.getText();
		
		if(nombre.length()==0 || nombre=="") {
			
			JOptionPane.showMessageDialog(null,"El campo Nombre es necesrio");
			txtNombre.requestFocus();
			return;
		}
		
		int edad = Integer.parseInt(txtEdad.getText());
		Usuario u = new Usuario(nombre, correo, edad);
		usuarioDAO.agregarUsuario(u);
		limpiarCampos();
	}

	private void actualizarUsuario() {
		int id = Integer.parseInt(txtId.getText());
		String nombre = txtNombre.getText();
		String correo = txtCorreo.getText();
		int edad = Integer.parseInt(txtEdad.getText());
		Usuario u = new Usuario(id, nombre, correo, edad);
		usuarioDAO.actualizarUsuario(u);
		limpiarCampos();
	}

	private void eliminarUsuario() {
		int id = Integer.parseInt(txtId.getText());
		usuarioDAO.eliminarUsuario(id);
		limpiarCampos();
	}

	private void listarUsuarios() {
		modeloTabla.setRowCount(0);

		List<Usuario> lista = usuarioDAO.obtenerUsuarios();
		for (Usuario u : lista) {
			Object[] fila = { u.getId(), u.getNombre(), u.getCorreo(), u.getEdad() };
			modeloTabla.addRow(fila);
		}
	}

	private void limpiarCampos() {
		txtId.setText("");
		txtNombre.setText("");
		txtCorreo.setText("");
		txtEdad.setText("");
		btnActualizar.setEnabled(false);
		btnEliminar.setEnabled(false);
	}

	public static void main(String[] args) {
		new Formsuario().listarUsuarios();
		
	}
}
