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
    private HashMap lectores;
    private HashMap admins;
    private ObjectInputStream objectInput;
    private ObjectOutputStream objectOutput;

    public Hilo(Socket conexion, HashMap biblioteca, HashMap admins, HashMap lectores) {
        this.conexion = conexion;
        this.biblioteca = biblioteca;
        this.lectores = lectores;
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
                        iniciarSesionAdmin((String) msg.get(1), (String) msg.get(2));
                        break;
                    case "agregarAdmin":
                        agregarAdmin((Admin) msg.get(1));
                        guardarAdmins();
                        break;
                    case "eliminarAdmin":
                        eliminarAdmin((String) msg.get(1));
                        guardarAdmins();
                        break;
                    case "consultarAdmin":
                        consultarAdmin((String) msg.get(1));
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

    public void iniciarSesionAdmin(String correo, String pass) {
        if (admins.containsKey(correo)) {
            Admin admin = (Admin) admins.get(correo);
            if (admin.getPassword().equals(pass)) {
                try {
                    ArrayList resp = new ArrayList();
                    resp.add(true);
                    resp.add(admin.getNombre());
                    resp.add(admin.getApellidos());
                    resp.add(admin.isAutorizado());
                    objectOutput.writeObject(resp);
                    objectOutput.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    ArrayList resp = new ArrayList();
                    resp.add(false);
                    resp.add("Contraseña incorrecta");
                    objectOutput.writeObject(resp);
                    objectOutput.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            try {
                ArrayList resp = new ArrayList();
                resp.add(false);
                resp.add("Correo incorrecto");
                objectOutput.writeObject(resp);
                objectOutput.flush();
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
        Libro libro = (Libro) biblioteca.get(isbn);
        int numeroPaginas = libro.getNumeroPaginas();
        String contenido = libro.getContenido();
        String titulo = (String) msg.get(2);
        String resumen = ((String) msg.get(3));
        String autor = ((String) msg.get(4));
        ImageIcon portada = ((ImageIcon) msg.get(5));
        double precio = ((double) msg.get(6));
        String categoria = ((String) msg.get(7));
        boolean bestSeller = ((boolean) msg.get(8));
        int edadMinima = ((int) msg.get(9));
        int calificacion = ((int) msg.get(10));

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

        
    }
    //-------------------------------------SERIALIZACION DE USUARIOS Y LIBROS--------------------------------------------
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
    
    public void guardarAdmins(){
        ObjectOutputStream escritor;
        try{
            escritor = new ObjectOutputStream(new FileOutputStream("administradores.txt", false));
            escritor.writeObject(admins);
            escritor.flush();
            escritor.close();
            System.out.println("Administradores guardados");
            } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void guardarLectores(){
        ObjectOutputStream escritor;
        try{
            escritor = new ObjectOutputStream(new FileOutputStream("lectores.txt", false));
            escritor.writeObject(lectores);
            escritor.flush();
            escritor.close();
            System.out.println("Lectores guardados");
            } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //-------------------------------GESTION DE USUARIOS ADMINISTRADORES---------------------------------------------------------
    public void agregarAdmin(Admin admin) {
        if (admins.containsKey(admin.getCorreo())) {
            try {
                ArrayList resp = new ArrayList();
                resp.add("El correo " + admin.getCorreo() + " ya se encuentra registrado");
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                admins.put(admin.getCorreo(), admin);
                ArrayList resp = new ArrayList();
                resp.add("Se registro el administrador con correo " + admin.getCorreo());
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void eliminarAdmin(String correo) {
        Object o = admins.remove(correo);
        if (o == null) {
            try {
                ArrayList resp = new ArrayList();
                resp.add("No existe un administrador con el correo ingresado");
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                ArrayList resp = new ArrayList();
                Admin admin = (Admin) o;
                resp.add("Se elimino el administrador con correo: " + admin.getCorreo());
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void consultarAdmin(String correo) {
//        this.nombre = nombre;
//        this.apellidos = apellidos;
//        this.cargo = cargo;
//        this.celular = celular;
//        this.correo = correo;
//        this.password = password;
//        this.autorizado = autorizado;
        if (admins.containsKey(correo)) {
            try {
                ArrayList resp = new ArrayList();
                Admin admin = (Admin) admins.get(correo);
                resp.add(admin.getNombre());
                resp.add(admin.getApellidos());
                resp.add(admin.getCargo());
                resp.add(admin.getCelular());
                resp.add(admin.isAutorizado());
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                ArrayList resp = new ArrayList();
                resp.add("No se encontro un administrador con el correo " + correo);
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
