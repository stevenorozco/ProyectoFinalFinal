/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorCliente;

import Cliente.Cliente;
import Modelo.Libro;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Luis
 */
public class ControladorCliente {

    Cliente cliente;

    public ControladorCliente() {
        this.cliente = new Cliente();
    }

    public void ejecutarCliente() {
        cliente.start();
    }

    public String[] consultarDatosLibro(String isbn) {
        ArrayList resp = consultarLibro(isbn);
        if (resp.get(0) == null) {
            String[] msg = new String[1];
            msg[0] = (String) resp.get(1);
            return msg;
        } else {
            String[] datos = new String[5];
            datos[0] = ((String) resp.get(0));
            datos[1] = ((String) resp.get(1));
            datos[2] = Double.toString((double) resp.get(8));
            datos[3] = Integer.toString((int) resp.get(6));
            datos[4] = (String) resp.get(9);
            return datos;
        }
    }

    public String agregarLibro(String isbn, int numeroPaginas, String titulo, String resumen, String autor, ImageIcon imagen,
            double precio, String categoria, boolean bestSeller, int edadMinima, int calificacion, String contenido) {
        return cliente.agregarLibro(isbn, numeroPaginas, titulo, resumen, autor, imagen, precio, categoria, bestSeller, edadMinima,
                calificacion, contenido);
    }

    public String eliminarLibro(String isbn) {
        return cliente.eliminarLibro(isbn);
    }

    public ArrayList consultarLibro(String isbn) {
        ArrayList resp = cliente.consultarLibro(isbn);
        if (resp.get(0) == null) {
            return resp;
        } else {
            Libro libro = (Libro) resp.get(0);
            ArrayList datos = new ArrayList();
            datos.add(libro.getTitulo());
            datos.add(libro.getAutor());
            datos.add(libro.getIsbn());
            datos.add(libro.getNumeroPaginas());
            datos.add(libro.getCategoria());
            datos.add(libro.getCalificacion());
            datos.add(libro.getEdadMinima());
            datos.add(libro.isBestSeller());
            datos.add(libro.getPrecio());
            datos.add(libro.getResumen());
            datos.add(libro.getImagen());

            return datos;
        }
    }

    public String editarLibro(String isbn, String titulo, String resumen, String autor, ImageIcon portada, double precio,
            String categoria, boolean bestSeller, int edadMinima, int calificacion) {
        return cliente.editarLibro(isbn, titulo, resumen, autor, portada, precio, categoria, bestSeller, edadMinima, calificacion);
    }

    public ArrayList seleccionarTexto(String path) {
        ArrayList texto = cliente.seleccionarTexto(path);
        String contenido = "";
        int contador = 0;
        for (int i = 0; i < texto.size(); i++) {
            if (i % 20 == 0) {
                contenido += "\n";
                contador += 1;
            } else {
                contenido += (String) texto.get(i) + "\n";
            }
        }
        ArrayList retorno = new ArrayList();
        retorno.add(contenido);
        retorno.add(contador);

        return retorno;
    }

    public ArrayList buscarPorCategoria(String categoria) {
        ArrayList libros = cliente.buscarPorCategoria(categoria);
        ArrayList titulos = null;
        for (int i = 0; i < libros.size(); i++) {
            Libro libro = (Libro) libros.get(i);
            titulos.add(libro.getTitulo());
        }
        return titulos;
    }

    public ImageIcon seleccionarPortada(String path) {
        ImageIcon portada = cliente.seleccionarPortada(path);
        return portada;
    }

    public String cerrarConexion() {
        return cliente.cerrarConexion();
    }

    public ArrayList iniciarSesionAdmin(String user, String pass) {
        return cliente.iniciarSesionAdmin(user, pass);
    }

    //-------------------------------------GESTION DE LECTORES-------------------------------------------------------------------
    
    public String agregarLector(String nombre, String apellidos, String celular, String fechaNacimiento, String correo, String password,String preferencia, int edad, double saldo){
        return (String)cliente.agregarLector(nombre, apellidos, celular, fechaNacimiento, correo, password, preferencia, edad, saldo).get(0);
    }
    
    //-------------------------------------GESTION DE ADMINISTRADORES------------------------------------------------------------
    public String agregarAdmin(String nombre, String apellidos, String cargo, String celular, String email, String password, boolean autorizado) {
        return (String) cliente.agregarAdmin(nombre, apellidos, cargo, celular, email, password, autorizado).get(0);
    }

    public String eliminarAdmin(String correo) {
        return (String) cliente.eliminarAdmin(correo).get(0);
    }

    public String consultarAdmin(String correo) {
//        resp.add(admin.getNombre());
//                resp.add(admin.getApellidos());
//                resp.add(admin.getCargo());
//                resp.add(admin.getCelular());
//                resp.add(admin.isAutorizado());

        ArrayList datos = cliente.consultarAdmin(correo);
        if (datos.size() == 1) {
            String resp = (String) datos.get(0);
            return resp;
        } else {
            if ((boolean) datos.get(4) == false) {
                String resp = "Nombre: " + (String) datos.get(0) + "\nApellidos: " + datos.get(1) + "\nCargo: " + datos.get(2)
                        + "\nCelular: " + datos.get(3) + "\nPuede crear ofertas: No";
                return resp;
            } else {
                String resp = "Nombre: " + (String) datos.get(0) + "\nApellidos: " + datos.get(1) + "\nCargo: " + datos.get(2)
                        + "\nCelular: " + datos.get(3) + "\nPuede crear ofertas: Si";
                return resp;
            }
        }
    }
    
    public String[] consultarDatosAdmin(String correo){
        
    }
}
