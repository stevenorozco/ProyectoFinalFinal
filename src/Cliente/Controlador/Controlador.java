/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente.Controlador;

import Cliente.Cliente;
import Modelo.Libro;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;

/**
 *
 * @author Luis
 */
public class Controlador {

    Cliente cliente;

    public Controlador() {
        this.cliente = new Cliente();
    }

    public void ejecutarCliente() {
        cliente.start();
    }

    public String agregarLibro(String isbn, int numeroPaginas, String titulo, String resumen, String autor, ImageIcon imagen,
            double precio, String categoria, boolean bestSeller, int edadMinima, int calificacion, ArrayList contenido) {
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

    public String editarLibro(String isbn, String titulo, String resumen, String autor, ImageIcon portada, double precio,
            String categoria, boolean bestSeller, int edadMinima, int calificacion) {
        return (String)cliente.editarLibro(isbn, titulo, resumen, autor, portada, precio, categoria, bestSeller, edadMinima, calificacion).get(0);
    }

    public ArrayList seleccionarTexto(String path) {
        ArrayList texto = cliente.seleccionarTexto(path);
        Iterator it = texto.iterator();
        ArrayList contenido = new ArrayList();
        String pagina = "";
        int contador = 0;

        while (it.hasNext()) {
            if (contador == 20) {
                contenido.add(pagina);
                contador = 0;
                pagina = "";
            }
            pagina += (String) it.next() + "\n";
            contador++;
        }
        contenido.add(pagina);
        return contenido;
    }

    public ArrayList buscarPorCategoria(String categoria) {
        ArrayList libros = cliente.buscarPorCategoria(categoria);
        ArrayList titulo = new ArrayList();
        ArrayList autor = new ArrayList();
        ArrayList isbn = new ArrayList();
        ArrayList resumen = new ArrayList();
        ArrayList calificacion = new ArrayList();
        ArrayList portada = new ArrayList();
        ArrayList edadMinima = new ArrayList();
        ArrayList precio = new ArrayList();
        ArrayList datos = new ArrayList();

        for (int i = 0; i < libros.size(); i++) {
            Libro libro = (Libro) libros.get(i);
            portada.add(libro.getImagen());
            titulo.add(libro.getTitulo());
            autor.add(libro.getAutor());
            resumen.add(libro.getResumen());
            edadMinima.add(libro.getEdadMinima());
            calificacion.add(libro.getCalificacion());
            precio.add(libro.getPrecio());
            isbn.add(libro.getIsbn());
        }
        datos.add(portada);
        datos.add(titulo);
        datos.add(autor);
        datos.add(resumen);
        datos.add(edadMinima);
        datos.add(calificacion);
        datos.add(precio);
        datos.add(isbn);

        return datos;
    }

    public ImageIcon seleccionarPortada(String path) {
        ImageIcon portada = cliente.seleccionarPortada(path);
        return portada;
    }
    //-------------------------------------------GESTION DE CONEXIONES-------------------------------------------------------------
    public String cerrarConexion() {
        return cliente.cerrarConexion();
    }
    //-----------------------------------------GESTION DE LOGINS-----------------------------------------------------------
    public ArrayList iniciarSesionAdmin(String user, String pass) {
        return cliente.iniciarSesionAdmin(user, pass);
    }
    
    public ArrayList iniciarSesionLector(String user, String pass){
        return cliente.iniciarSesionLector(user, pass);
    }

    //-------------------------------------GESTION DE LECTORES-------------------------------------------------------------------
    public String agregarLector(String nombre, String apellidos, String celular, String fechaNacimiento, String correo, String password, ArrayList preferencia, int edad, double saldo) {
        return (String) cliente.agregarLector(nombre, apellidos, celular, fechaNacimiento, correo, password, preferencia, edad, saldo).get(0);
    }

    public String eliminarLector(String correo) {
        return (String) cliente.eliminarLector(correo).get(0);
    }

    public String consultarLector(String correo) {
        ArrayList msg = cliente.consultarLector(correo);
        String mensaje = "Nombre: " + (String) msg.get(0)
                + "\nApellidos: " + (String) msg.get(1)
                + "\nCelular: " + (String) msg.get(2)
                + "\nFecha de nacimiento: " + (String) msg.get(3)
                + "\nEdad: " + (String) msg.get(4)
                + "\nSaldo: " + (String) msg.get(5)
                + "\nLibros leidos" + (String) msg.get(6);
        return mensaje;
    }

    public ArrayList consultarDatosLector(String correo) {
        return cliente.consultarDatosLector(correo);
    }

    public String editarLector(String correo, String nombre, String apellidos, String celular, String fechaNacimiento, int edad) {
        return (String) cliente.editarLector(correo, nombre, apellidos, celular, fechaNacimiento, edad).get(0);

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

    public ArrayList consultarDatosAdmin(String correo) {
        return cliente.consultarDatosAdmin(correo);

    }

    public String editarAdmin(String correo, String nombre, String apellidos, String cargo, String celular, boolean autorizado) {
        return (String) cliente.editarAdmin(correo, nombre, apellidos, cargo, celular, autorizado).get(0);
    }

}


/* 
    public mostrarLibrosSugeridos(){
        Lector Ul = new Lector();
        
       ArraList librosSugeridos = Libro.buscarLibrosRecomendadosPorCategoria(Ul.getCategoriaPreferida(), todosLosLibros);
        
        JoptionPane.ShowMesssage("Te recomendamos leer:" + librosSugereidos);    
    }
 */
