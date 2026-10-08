package co.uptc.edu.gui;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import co.uptc.edu.negocio.Libro;
import co.uptc.edu.negocio.LibroService;

public class GestionLibro extends JFrame {
	private static final long serialVersionUID = 1L;
    // OBJETO DEL SERVICIO
    private LibroService servicio;
    // CAMPOS
    private JTextField txtId;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtCategoria;
    private JTextField txtEditorial;
    private JTextField txtAno;
    private JTextField txtPrecio;
    private JTextField txtStock;
    // COMBO
    private JComboBox<String> cmbFormato;
    // BOTONES
    private JButton btnRegistrar;
    private JButton btnConsultar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    // TABLA
    private JTable tabla;
    private DefaultTableModel modelo;
    // CONSTRUCTOR
    public GestionLibro() {
        servicio = new LibroService();

        setTitle("Gestión de Libros");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        crearInterfaz();}
    // CREAR INTERFAZ
    private void crearInterfaz() {
        // PANEL DE DATOS
        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(new GridLayout(9, 2));
        panelDatos.add(new JLabel("ID:"));
        txtId = new JTextField();
        panelDatos.add(txtId);
        panelDatos.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panelDatos.add(txtTitulo);
        panelDatos.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        panelDatos.add(txtAutor);
        panelDatos.add(new JLabel("Categoría:"));
        txtCategoria = new JTextField();
        panelDatos.add(txtCategoria);
        panelDatos.add(new JLabel("Editorial:"));
        txtEditorial = new JTextField();
        panelDatos.add(txtEditorial);
        panelDatos.add(new JLabel("Año:"));
        txtAno = new JTextField();
        panelDatos.add(txtAno);
        panelDatos.add(new JLabel("Formato:"));
        cmbFormato = new JComboBox<String>();
        cmbFormato.addItem("Fisico");
        cmbFormato.addItem("Digital");
        panelDatos.add(cmbFormato);
        panelDatos.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        panelDatos.add(txtPrecio);
        panelDatos.add(new JLabel("Stock:"));
        txtStock = new JTextField();
        panelDatos.add(txtStock);
        // PANEL DE BOTONES
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());
        btnRegistrar = new JButton("Registrar");
        btnConsultar = new JButton("Consultar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnConsultar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        // TABLA
        String[] columnas = {
                "ID",
                "Título",
                "Autor",
                "Categoría",
                "Editorial",
                "Año",
                "Formato",
                "Precio",
                "Stock"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        // EVENTO REGISTRAR
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarLibro();
            }
        });
        // EVENTO CONSULTAR
        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarLibros();
            }
        });
        // EVENTO ACTUALIZAR
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarLibro();
            }
        });
        // EVENTO ELIMINAR
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarLibro();
            }
        });
        // EVENTO AL SELECCIONAR FILA
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtId.setText(
                        modelo.getValueAt(fila, 0).toString()
                );
                txtTitulo.setText(
                        modelo.getValueAt(fila, 1).toString()
                );
                txtAutor.setText(
                        modelo.getValueAt(fila, 2).toString()
                );
                txtCategoria.setText(
                        modelo.getValueAt(fila, 3).toString()
                );
                txtEditorial.setText(
                        modelo.getValueAt(fila, 4).toString()
                );
                txtAno.setText(
                        modelo.getValueAt(fila, 5).toString()
                );
                cmbFormato.setSelectedItem(
                        modelo.getValueAt(fila, 6).toString()
                );
                txtPrecio.setText(
                        modelo.getValueAt(fila, 7).toString()
                );
                txtStock.setText(
                        modelo.getValueAt(fila, 8).toString()
                );
            }
        });
        // VENTANA
        setLayout(new java.awt.BorderLayout());

        add(panelDatos, java.awt.BorderLayout.NORTH);
        add(scroll, java.awt.BorderLayout.CENTER);
        add(panelBotones, java.awt.BorderLayout.SOUTH);
    }
    // REGISTRAR
    private void registrarLibro() {
        try {
            Libro libro = obtenerLibro();
            boolean resultado = servicio.registrar(libro);
            if (resultado) {
                JOptionPane.showMessageDialog(
                        this,
                        "Libro registrado correctamente"
                );
                limpiarCampos();
                consultarLibros();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo registrar el libro"
                );
            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Revise los datos ingresados"
            );
        }
    }
    // CONSULTAR
    private void consultarLibros() {
        modelo.setRowCount(0);
        for (Libro libro : servicio.consultar()) {
            Object[] fila = {
                    libro.getIdLibro(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getCategoria(),
                    libro.getEditorial(),
                    libro.getAnoPublicacion(),
                    libro.getFormato(),
                    libro.getPrecio(),
                    libro.getStock()
            };
            modelo.addRow(fila);
        }
    }
    // ACTUALIZAR
    private void actualizarLibro() {
        try {
            Libro libro = obtenerLibro();
            boolean resultado = servicio.actualizar(libro);
            if (resultado) {
                JOptionPane.showMessageDialog(
                        this,
                        "Libro actualizado correctamente"
                );
                consultarLibros();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "No se encontró el libro"
                );
            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Revise los datos ingresados"
            );
        }
    }
    // ELIMINAR
    private void eliminarLibro() {
        try {
            int id = Integer.parseInt(
                    txtId.getText()
            );
            boolean resultado = servicio.eliminar(id);
            if (resultado) {
                JOptionPane.showMessageDialog(
                        this,
                        "Libro eliminado correctamente"
                );
                limpiarCampos();
                consultarLibros();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "No se encontró el libro"
                );
            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese un ID válido"
            );
        }
    }
    // OBTENER LIBRO DE LOS CAMPOS
    private Libro obtenerLibro() {
        int id = Integer.parseInt(
                txtId.getText()
        );
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        String categoria = txtCategoria.getText();
        String editorial = txtEditorial.getText();
        int ano = Integer.parseInt(
                txtAno.getText()
        );
        String formato =
                cmbFormato.getSelectedItem().toString();
        double precio = Double.parseDouble(
                txtPrecio.getText()
        );
        int stock = Integer.parseInt(
                txtStock.getText()
        );
        Libro libro = new Libro(
                id,
                titulo,
                autor,
                categoria,
                editorial,
                ano,
                formato,
                precio,
                stock
        );
        return libro;
    }
    // LIMPIAR CAMPOS
    private void limpiarCampos() {
        txtId.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtCategoria.setText("");
        txtEditorial.setText("");
        txtAno.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        cmbFormato.setSelectedIndex(0);
    }
    // MAIN
    public static void main(String[] args) {

        GestionLibro ventana = new GestionLibro();
        ventana.setVisible(true);
    }
}