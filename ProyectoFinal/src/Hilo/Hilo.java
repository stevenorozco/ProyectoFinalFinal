/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import Modelo.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author invitado
 */
public class Hilo extends Thread {

    private Socket conexion;
    private HashMap biblioteca;
    private HashMap usuarios;
    private HashMap admins;
    private ObjectInputStream objectInput;
    private ObjectOutputStream objectOutput;
    
    public Hilo(Socket conexion, HashMap biblioteca, HashMap admins, HashMap usuarios) {
        this.conexion = conexion;
        this.biblioteca = biblioteca;
        this.usuarios = usuarios;
        this.admins = admins;
        
        try {
            objectInput = new ObjectInputStream(conexion.getInputStream());
            objectOutput = new ObjectOutputStream(conexion.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }        
    }
    
    @Override
    public synchronized void run() {
        try {
            while (true) {
                System.out.println("Thread running");
                ArrayList msg = (ArrayList) objectInput.readObject();
                switch ((String) msg.get(0)) {
                    case "login":
                        iniciarSesion((String) msg.get(1), (String) msg.get(2));
                        break;
                    case "agregarLibro":
                        agregarLibro((Libro) msg.get(1));                        
                        guardarBiblioteca();
                        break;
                    case "consultarLibro":
                        consultarLibro((String) msg.get(1));
                        break;
                    case "eliminarLibro":
                        eliminarLibro((String) msg.get(1));
                        guardarBiblioteca();
                        break;
                    case "editarLibro":
                        editarLibro(msg);
                        guardarBiblioteca();
                        break;
                    case "consultarCategoria":
                        consultarCategoria((String) msg.get(1));
                        break;
                    default:
                        System.out.println("Mensaje desconocido");
                        break;
                }
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void iniciarSesion(String correo, String pass) {
        if (this.admins.containsKey(pass)) {
            System.out.println("Encontrado pass admin");
            UserAdmin admin = (UserAdmin) admins.get(pass);
            if (admin.getCorreo() == correo) {
                try {
                    ArrayList resp = new ArrayList();
                    resp.add("admin");
                    objectOutput.writeObject(resp);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    ArrayList resp = new ArrayList();
                    resp.add("Correo o contraseña incorrecta");
                    objectOutput.writeObject(resp);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (this.usuarios.containsKey(pass)) {
            UserReader lector = (UserReader) this.usuarios.get(pass);
            if (lector.getCorreo() == correo) {
                try {
                    ArrayList resp = new ArrayList();
                    resp.add("lector");
                    objectOutput.writeObject(resp);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    ArrayList resp = new ArrayList();
                    resp.add("Correo o contraseña incorrecta");
                    objectOutput.writeObject(resp);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            try {
                ArrayList resp = new ArrayList();
                resp.add("No se encontro un usuario con el correo ingresado");
                objectOutput.writeObject(resp);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void agregarLibro(Libro libro) {
        if (biblioteca.containsKey(libro.getIsbn())) {
            try {
                ArrayList resp = new ArrayList();
                resp.add("El libro con ISBN: " + libro.getIsbn() + " ya existe en la biblioteca");
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                biblioteca.put(libro.getIsbn(), libro);
                ArrayList resp = new ArrayList();
                resp.add("Libro agregado");
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void eliminarLibro(String isbn) {
        if (biblioteca.containsKey(isbn) == true) {
            try {
                biblioteca.remove(isbn);
                ArrayList resp = new ArrayList();
                resp.add("Se elimino el libro con ISBN: " + isbn);
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                ArrayList resp = new ArrayList();
                resp.add("No se encontro el libro con ISBN: " + isbn);
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
    }

    public void consultarLibro(String isbn) {
        Libro libro = (Libro) biblioteca.get(isbn);
        if (libro == null) {
            try {
                ArrayList resp = new ArrayList();
                resp.add(null);
                resp.add("No se encontro el libro con el ISBN: " + isbn);
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                ArrayList resp = new ArrayList();
                resp.add(libro);
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void editarLibro(ArrayList msg) {
        /*String isbn, int numeroPaginas, String titulo, String resumen, String autor, ImageIcon imagen, double precio,
                String categoria, boolean bestSeller, int edadMinima, int calificacion, String contenido*/
        
        String isbn = (String) msg.get(1);
        System.out.println(isbn);
        Libro libro = (Libro) biblioteca.get(isbn);
        int numeroPaginas = libro.getNumeroPaginas();
        System.out.println(numeroPaginas);
        String contenido = libro.getContenido();
        String titulo = (String) msg.get(2);
        System.out.println(titulo);
        String resumen = ((String) msg.get(3));
        System.out.println(resumen);
        String autor = ((String) msg.get(4));
        System.out.println(autor);
        ImageIcon portada = ((ImageIcon) msg.get(5));
        double precio = ((double) msg.get(6));
        System.out.println(precio);
        String categoria = ((String) msg.get(7));
        System.out.println(categoria);
        boolean bestSeller = ((boolean) msg.get(8));
        System.out.println(bestSeller);
        int edadMinima = ((int) msg.get(9));
        System.out.println(edadMinima);
        int calificacion = ((int) msg.get(10));
        System.out.println(calificacion);
        
        if (biblioteca.containsKey(isbn) == true) {
            biblioteca.remove(isbn);
            Libro l = new Libro(isbn, numeroPaginas, titulo, resumen, autor, portada, precio, categoria, bestSeller, edadMinima, calificacion, contenido);
            biblioteca.put(isbn, l);
            try {
                ArrayList resp = new ArrayList();
                resp.add("El libro con ISBN: " + isbn + " se edito correctamente");
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                ArrayList resp = new ArrayList();
                resp.add("El libro con ISBN: " + isbn + " No existe o no se pudo editar");
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
    }
    
    public void consultarCategoria(String categoria) {
        /*
        Romance
        Suspenso
        Terror
        Drama
        Matematicas
        Fisica
        Estadistica
        Programacion
         */
        
        if (categoria == "Todos") {
            try {
                Iterator it = this.biblioteca.values().iterator();
                ArrayList libros = new ArrayList();
                while (it.hasNext()) {
                    Libro libro = (Libro) it.next();
                    libros.add(libro);
                }
                objectOutput.writeObject(libros);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (categoria == "Romance") {
            try {
                Iterator it = this.biblioteca.values().iterator();
                ArrayList libros = new ArrayList();
                while (it.hasNext()) {
                    Libro libro = (Libro) it.next();
                    if (libro.getCategoria() == "Romance") {
                        libros.add(libro);
                    }
                }
                objectOutput.writeObject(libros);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (categoria == "Suspenso") {
            try {
                Iterator it = this.biblioteca.values().iterator();
                ArrayList libros = new ArrayList();
                while (it.hasNext()) {
                    Libro libro = (Libro) it.next();
                    if (libro.getCategoria() == "Suspenso") {
                        libros.add(libro);
                    }
                }
                objectOutput.writeObject(libros);
                objectOutput.flush();
            } catch (IOException ex) {
                Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (categoria == "Terror") {
            try {
                Iterator it = this.biblioteca.values().iterator();
                ArrayList libros = new ArrayList();
                while (it.hasNext()) {
                    Libro libro = (Libro) it.next();
                    if (libro.getCategoria() == "Terror") {
                        libros.add(libro);
                    }
                }
                objectOutput.writeObject(libros);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (categoria == "Drama") {
            try {
                Iterator it = this.biblioteca.values().iterator();
                ArrayList libros = new ArrayList();
                while (it.hasNext()) {
                    Libro libro = (Libro) it.next();
                    if (libro.getCategoria() == "Drama") {
                        libros.add(libro);
                    }
                }
                objectOutput.writeObject(libros);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (categoria == "Matematicas") {
            try {
                Iterator it = this.biblioteca.values().iterator();
                ArrayList libros = new ArrayList();
                while (it.hasNext()) {
                    Libro libro = (Libro) it.next();
                    if (libro.getCategoria() == "Matematicas") {
                        libros.add(libro);
                    }
                }
                objectOutput.writeObject(libros);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (categoria == "Fisica") {
            try {
                Iterator it = this.biblioteca.values().iterator();
                ArrayList libros = new ArrayList();
                while (it.hasNext()) {
                    Libro libro = (Libro) it.next();
                    if (libro.getCategoria() == "Fisica") {
                        libros.add(libro);
                    }
                }
                objectOutput.writeObject(libros);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (categoria == "Estadistica") {
            try {
                Iterator it = this.biblioteca.values().iterator();
                ArrayList libros = new ArrayList();
                while (it.hasNext()) {
                    Libro libro = (Libro) it.next();
                    if (libro.getCategoria() == "Estadistica") {
                        libros.add(libro);
                    }
                }
                objectOutput.writeObject(libros);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (categoria == "Programacion") {
            try {
                Iterator it = this.biblioteca.values().iterator();
                ArrayList libros = new ArrayList();
                while (it.hasNext()) {
                    Libro libro = (Libro) it.next();
                    if (libro.getCategoria() == "Programacion") {
                        libros.add(libro);
                    }
                }
                objectOutput.writeObject(libros);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void guardarBiblioteca() {
        ObjectOutputStream escritor;
        try {
            escritor = new ObjectOutputStream(new FileOutputStream("biblioteca.txt", false));
            escritor.writeObject(this.biblioteca);
            escritor.flush();
            escritor.close();
            System.out.println("Biblioteca guardada");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
}
