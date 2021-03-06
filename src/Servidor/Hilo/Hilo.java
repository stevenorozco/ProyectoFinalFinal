/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor.Hilo;

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
                    case "iniciarSesionAdmin":
                        iniciarSesionAdmin((String) msg.get(1), (String) msg.get(2));
                        break;
                    case "iniciarSesionLector":
                        iniciarSesionLector((String) msg.get(1), (String) msg.get(2));
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
                    case "consultarDatosAdmin":
                        consultarDatosAdmin((String) msg.get(1));
                        break;
                    case "editarAdmin":
                        editarAdmin(msg);
                        guardarAdmins();
                        break;
                    case "agregarLector":
                        agregarLector((Lector) msg.get(1));
                        guardarLectores();
                        break;
                    case "eliminarLector":
                        eliminarLector((String) msg.get(1));
                        guardarLectores();
                        break;
                    case "consultarLector":
                        consultarLector((String) msg.get(1));
                        break;
                    case "consultarDatosLector":
                        consultarDatosLector((String) msg.get(1));
                        break;
                    case "editarLector":
                        editarLector(msg);
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

    public void iniciarSesionLector(String correo, String pass) {
        if (lectores.containsKey(correo)) {
            Lector lector = (Lector) lectores.get(correo);
            if (lector.getPassword().equals(pass)) {
                try {
                    ArrayList resp = new ArrayList();
                    resp.add(true);
                    resp.add(correo);
                    resp.add(lector.getNombre());
                    resp.add(lector.getApellidos());
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
        ArrayList resp = new ArrayList();
        String isbn = (String) msg.get(1);
        Libro libro = (Libro) biblioteca.get(isbn);
        libro.setTitulo((String) msg.get(2));
        libro.setResumen((String) msg.get(3));
        libro.setAutor((String) msg.get(4));
        libro.setImagen((ImageIcon) msg.get(5));
        libro.setPrecio((double) msg.get(6));
        libro.setCategoria((String) msg.get(7));
        libro.setBestSeller((boolean) msg.get(8));
        libro.setEdadMinima((int) msg.get(9));
        libro.setCalificacion((int) msg.get(10));
        resp.add("Se editaron los siguientes atributos del libro:\n"
                + "Titulo: " + libro.getTitulo()
                + "Autor: " + libro.getAutor()
                + "Resumen: " + libro.getResumen()
                + "Precio: " + libro.getPrecio()
                + "Categoria: " + libro.getCategoria()
                + "Best Seller: " + libro.isBestSeller()
                + "Edad Minima: " + libro.getEdadMinima()
                + "Calificacion: " + libro.getCalificacion());
        try {
            objectOutput.writeObject(resp);
            objectOutput.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void consultarCategoria(String categoria) {
        /*  Romance
            Suspenso
            Terror
            Drama
            Matematicas
            Fisica
            Estadistica
            Programacion
         */
        try {
            Iterator it = biblioteca.values().iterator();
            ArrayList libros = new ArrayList();
            while (it.hasNext()) {
                Libro libro = (Libro) it.next();
                if (libro.getCategoria().equals(categoria)) {
                    libros.add(libro);
                }
            }
            objectOutput.writeObject(libros);
            objectOutput.flush();
        } catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public void guardarAdmins() {
        ObjectOutputStream escritor;
        try {
            escritor = new ObjectOutputStream(new FileOutputStream("administradores.txt", false));
            escritor.writeObject(admins);
            escritor.flush();
            escritor.close();
            System.out.println("Administradores guardados");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void guardarLectores() {
        ObjectOutputStream escritor;
        try {
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

    public void consultarDatosAdmin(String correo) {
        ArrayList resp = new ArrayList();
        if (admins.containsKey(correo)) {
            try {
                Admin admin = (Admin) admins.get(correo);
                resp.add(true);
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
                resp.add(false);
                resp.add("No se encontro el administrador con correo " + correo);
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    public void editarAdmin(ArrayList msg) {
        try {
            String correo = (String) msg.get(1);
            Admin admin = (Admin) admins.get(correo);
            admin.setNombre((String) msg.get(2));
            admin.setApellidos((String) msg.get(3));
            admin.setCargo((String) msg.get(4));
            admin.setCelular((String) msg.get(5));
            admin.setAutorizado((boolean) msg.get(6));
            ArrayList resp = new ArrayList();
            resp.add("Se editaron los datos del admin con correo " + admin.getCorreo()
                    + ":\nNombre: " + admin.getNombre()
                    + "\nApellidos: " + admin.getApellidos()
                    + "\nCargo: " + admin.getCargo()
                    + "\nCelular: " + admin.getCelular()
                    + "\nAutorizacion: " + admin.isAutorizado());
            objectOutput.writeObject(resp);
            objectOutput.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //-------------------------------------------------GESTION DE USUARIOS LECTORES--------------------------------------------------
    public void agregarLector(Lector lector) {
        if (lectores.containsKey(lector.getCorreo())) {
            try {
                ArrayList resp = new ArrayList();
                resp.add("El correo " + lector.getCorreo() + " ya se encuentra registrado");
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                lectores.put(lector.getCorreo(), lector);
                ArrayList resp = new ArrayList();
                resp.add("Se agrego el lector con correo " + lector.getCorreo() + "exitosamente");
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    public void eliminarLector(String correo) {
        Object o = lectores.remove(correo);
        if (o == null) {
            try {
                ArrayList resp = new ArrayList();
                resp.add("No se encontro un lector con el correo " + correo);
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                ArrayList resp = new ArrayList();
                resp.add("Se elimino el lector con correo " + correo);
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void consultarLector(String correo) {
        Object o = lectores.get(correo);
        if (o == null) {
            try {
                ArrayList resp = new ArrayList();
                resp.add("No se encontro un lector con correo " + correo);
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                Lector lector = (Lector) o;
                ArrayList resp = new ArrayList();
                resp.add(lector.getNombre());
                resp.add(lector.getApellidos());
                resp.add(lector.getCelular());
                resp.add(lector.getFechaNacimiento());
                resp.add(lector.getEdad());
                resp.add(lector.getSaldo());
                resp.add(lector.getLibrosLeidos());
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void consultarDatosLector(String correo) {
        Object o = lectores.get(correo);
        if (o == null) {
            try {
                ArrayList resp = new ArrayList();
                resp.add(false);
                resp.add("No se encontro un lector con correo " + correo);
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                Lector lector = (Lector) o;
                ArrayList resp = new ArrayList();
                resp.add(true);
                resp.add(lector.getNombre());
                resp.add(lector.getApellidos());
                resp.add(lector.getCelular());
                resp.add(lector.getFechaNacimiento());
                resp.add(lector.getEdad());
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void editarLector(ArrayList msg) {
        try {
            String correo = (String) msg.get(1);
            Lector lector = (Lector) lectores.get(correo);
            lector.setNombre((String) msg.get(2));
            lector.setApellidos((String) msg.get(3));
            lector.setCelular((String) msg.get(4));
            lector.setFechaNacimiento((String) msg.get(5));
            lector.setEdad((int) msg.get(6));
            ArrayList resp = new ArrayList();
            resp.add("Se editaron los datos del lector con correo " + correo
                    + "\nNombre: " + lector.getNombre()
                    + "\nApellidos: " + lector.getApellidos()
                    + "\nCelular: " + lector.getCelular()
                    + "\nFecha de nacimiento: " + lector.getFechaNacimiento()
                    + "\nEdad: " + lector.getEdad());
            objectOutput.writeObject(resp);
            objectOutput.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
