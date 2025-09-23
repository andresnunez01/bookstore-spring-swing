package com.example.tienda_libros.presentacion;

import com.example.tienda_libros.modelo.Libro;
import com.example.tienda_libros.servicio.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class LibroForm extends JFrame {

    LibroServicio libroServicio;
    private JPanel panel;
    private JTable tablaLibros;
    private JTextField idTexto;
    private JTextField libroTexto;
    private JTextField autorTexto;
    private JTextField precioTexto;
    private JTextField existenciasTexto;
    private JButton agreagrBtn;
    private JButton modificarBtn;
    private JButton eliminarBtn;
    private DefaultTableModel tablaModeloLibros;

    @Autowired
    public LibroForm(LibroServicio libroServicio){
        this.libroServicio = libroServicio;
        iniciarForma();

        agreagrBtn.addActionListener(e -> agregarLibro());

        tablaLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarLibroSeleccionado();
            }
        });

        modificarBtn.addActionListener(e -> modificarLibro());
        eliminarBtn.addActionListener(e -> eliminarLibro());
    }

    private void iniciarForma(){
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(900,700);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension tamanioPantalla = toolkit.getScreenSize();
        int x = (tamanioPantalla.width - getWidth()/2);
        int y = (tamanioPantalla.height - getHeight()/2);
        setLocation(x,y);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        //Creamos el elemnto idTexto oculto
        idTexto = new JTextField("");
        idTexto.setVisible(false);


        this.tablaModeloLibros = new DefaultTableModel(0, 5){

            @Override
            public boolean isCellEditable(int row, int column){return false;}
        };


        String[] headers = { "Id", "Libro", "Autor", "Precio", "Existencias"};
        tablaModeloLibros.setColumnIdentifiers(headers);
        //INSTANCIAR EL OBJETO JTABLE
        this.tablaLibros = new JTable((tablaModeloLibros));
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listarLibros();
    }

    private void listarLibros(){
        //LIMPIAR TABLA
        tablaModeloLibros.setRowCount(0);
        //OBTENER TODOS LOS LIBROS DE LA BD
        var libros = libroServicio.listarLibros();
        libros.forEach((libro) -> {
            Object[] renglonLibro = {
              libro.getIdLibro(),
              libro.getNombreLibro(),
              libro.getAutor(),
              libro.getPrecio(),
              libro.getExistencias()
            };
            tablaModeloLibros.addRow(renglonLibro);
        });
    }

    private void agregarLibro() {
        //LEER VALORES DE FORMULARIO
        if (libroTexto.getText().isEmpty()){
            mostrarMensaje("Ingresa el nombre del libro");
            libroTexto.requestFocusInWindow();
            return;
        }else if (autorTexto.getText().isEmpty()){
            mostrarMensaje("Ingresa el nombre del autor");
            libroTexto.requestFocusInWindow();
            return;
        }else if (precioTexto.getText().isEmpty()){
            mostrarMensaje("Ingresa el precio del libro");
            libroTexto.requestFocusInWindow();
            return;
        }else if (existenciasTexto.getText().isEmpty()){
            mostrarMensaje("Ingresa las existencias del libro");
            libroTexto.requestFocusInWindow();
            return;
        }else {

            var libro = new Libro(null,
                    libroTexto.getText(),
                    autorTexto.getText(),
                    Double.parseDouble(precioTexto.getText()),
                    Integer.parseInt(existenciasTexto.getText()));

            this.libroServicio.guardarLibro(libro);
            mostrarMensaje("SE AGREGO EL LIBRO");
            limpiarFormulario();
            listarLibros();

        }
    }

    private void modificarLibro() {
        if (libroTexto.getText().isEmpty() || autorTexto.getText().isEmpty() || precioTexto.getText().isEmpty() || existenciasTexto.getText().isEmpty()){
            mostrarMensaje("SELECCIONE UN REGISTRO...");
            tablaLibros.requestFocusInWindow();
            return;
        }else {

            var libro = new Libro(Integer.parseInt(idTexto.getText()),
                    libroTexto.getText(),
                    autorTexto.getText(),
                    Double.parseDouble(precioTexto.getText()),
                    Integer.parseInt(existenciasTexto.getText()));

            this.libroServicio.guardarLibro(libro);
            mostrarMensaje("SE MODIFICO EL LIBRO");
            limpiarFormulario();
            listarLibros();

        }
    }

    private void eliminarLibro() {
        var renglon = tablaLibros.getSelectedRow();
        if (renglon != -1){

            var libroEliminar = new Libro(
                    Integer.parseInt(idTexto.getText()),
                    libroTexto.getText(),
                    autorTexto.getText(),
                    Double.parseDouble(precioTexto.getText()),
                    Integer.parseInt(existenciasTexto.getText()));

            libroServicio.eliminarLibro(libroEliminar);
            mostrarMensaje("EL LIBRO " + idTexto.getText() + " ELIMINO CORRECTAMENTE");
            limpiarFormulario();
            listarLibros();

        }else mostrarMensaje("NO SE HA SELECCIONADO NINGUN REGISTRO");
    }

    private void cargarLibroSeleccionado(){
        //INDICES DE LAS COLUMNAS INICIAN EN 0
        var renglon = tablaLibros.getSelectedRow();
        if (renglon != -1){
            idTexto.setText(tablaLibros.getModel().getValueAt(renglon,0).toString());
            libroTexto.setText(tablaLibros.getModel().getValueAt(renglon,1).toString());
            autorTexto.setText(tablaLibros.getModel().getValueAt(renglon,2).toString());
            precioTexto.setText(tablaLibros.getModel().getValueAt(renglon,3).toString());
            existenciasTexto.setText(tablaLibros.getModel().getValueAt(renglon, 4).toString());

        }
    }

    private void limpiarFormulario() {
        libroTexto.setText("");
        autorTexto.setText("");
        precioTexto.setText("");
        existenciasTexto.setText("");
    }


    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }


}

