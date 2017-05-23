/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import Modelo.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Luis
 */
public class Cliente extends Thread{
    Socket conexion;
    ObjectOutputStream objectOutput;
    ObjectInputStream objectInput;
    public Cliente(){}
    
    @Override
    public void run(){
        conectarServidor();
        crearFlujos();
        
    }
    //-----------------------------------CONEXION CON EL SERVIDOR------------------------------------------
    public ArrayList iniciarSesionAdmin(String user, String pass){
        ArrayList msg = null;
        try {
            ArrayList login = new ArrayList();
            login.add("login");
            login.add(user);
            login.add(pass);
            objectOutput.writeObject(login);
            msg = (ArrayList) objectInput.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return msg;
    }
    public void conectarServidor(){
        
        try {
            System.out.println("Inicializando cliente...");
            conexion = new Socket("localhost", 1144);
            System.out.println("Cliente inicializado y conectado");
        } catch (IOException ex) {
            System.out.println("IOException in conectarServidor method, class Cliente");
        }
        
    }
    
    public void crearFlujos(){
        try {
            System.out.println("Creando flujos...");
            objectOutput = new ObjectOutputStream(conexion.getOutputStream());
            objectInput = new ObjectInputStream(conexion.getInputStream());
            System.out.println("Flujos creados");
        } catch (IOException ex) {
            System.out.println("IOException in crearFlujos method, class Cliente");
        }
    }
    
    public String cerrarConexion(){
        String msg = "";
        try {
            System.out.println("Cerrando conexion...");
            objectInput.close();
            objectOutput.close();
            conexion.close();
            msg = "Conexion cerrada";
        } catch (IOException ex) {
            msg = "Error al cerrar la conexion";
        }
        
        return msg;
    }
    //----------------------------------------------GESTION DE LIBROS---------------------------------------------------------
    public String agregarLibro(String isbn, int numeroPaginas, String titulo, String resumen, String autor, ImageIcon imagen,
            double precio, String categoria, boolean bestSeller, int edadMinima, int calificacion, String contenido){
        String mensaje="";
        try {
            Libro libro = new Libro(isbn, numeroPaginas, titulo, resumen, autor, imagen, precio, categoria, bestSeller,
            edadMinima, calificacion, contenido);
            ArrayList msg = new ArrayList();
            msg.add("agregarLibro");
            msg.add(libro);
            objectOutput.writeObject(msg);
            objectOutput.flush();
            ArrayList resp = (ArrayList)objectInput.readObject();
            mensaje = "Recibido del servidor: " + (String)resp.get(0);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return mensaje;
    }
    
    public String eliminarLibro(String isbn){
        String mensaje="";
        try{
           ArrayList msg = new ArrayList();
           msg.add("eliminarLibro");
           msg.add(isbn);
           objectOutput.writeObject(msg);
           objectOutput.flush();
           ArrayList resp = (ArrayList)objectInput.readObject();
           mensaje = "Recibido del servidor: " + (String)resp.get(0);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        return mensaje;
    }
    
    public ArrayList consultarLibro(String isbn){
        ArrayList resp=null;
        try{
            ArrayList msg = new ArrayList();
            msg.add("consultarLibro");
            msg.add(isbn);
            objectOutput.writeObject(msg);
            objectOutput.flush();
            resp = (ArrayList)objectInput.readObject();
           
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
         return resp;
    }
    
    public String editarLibro(String isbn, String titulo, String resumen, String autor, ImageIcon portada,
            double precio, String categoria, boolean bestSeller, int edadMinima, int calificacion){
        String mensaje="";
        try {
            ArrayList datos = new ArrayList();
            datos.add("editarLibro");
            datos.add(isbn);
            datos.add(titulo);
            datos.add(resumen);
            datos.add(autor);
            datos.add(portada);
            datos.add(precio);
            datos.add(categoria);
            datos.add(bestSeller);
            datos.add(edadMinima);
            datos.add(calificacion);
            
            objectOutput.writeObject(datos);
            objectOutput.flush();
            ArrayList resp = (ArrayList)objectInput.readObject();
            mensaje = (String)resp.get(0);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return mensaje;
    }
    
    public ArrayList buscarPorCategoria(String categoria){
        ArrayList libros=null;
        try {
            ArrayList msg = new ArrayList();
            msg.add("consultarCategoria");
            msg.add(categoria);
            objectOutput.writeObject(msg);
            objectOutput.flush();
            libros = (ArrayList)objectInput.readObject();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return libros;
    }
    
    public ArrayList seleccionarTexto(String path){
        ArrayList text = new ArrayList();
        String linea;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while((linea = br.readLine()) != null){
                text.add(linea);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return text;
    }
    
    public ImageIcon seleccionarPortada(String path){
        ImageIcon imagen = new ImageIcon(path);
        
        return imagen;
    }
    //----------------------------------------------GESTION DE USUARIOS LECTORES-----------------------------------------------// 
    public ArrayList agregarLector(String nombre, String apellidos, String celular, String fechaNacimiento, String correo, String password, int edad, double saldo){
        ArrayList resp = new ArrayList();
        try {
            Lector lector = new Lector(nombre, apellidos, celular, fechaNacimiento, correo, password, edad, saldo);
            ArrayList msg = new ArrayList();
            msg.add("agregarLector");
            msg.add(lector);
            objectOutput.writeObject(msg);
            resp = (ArrayList)objectInput.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return resp;
    }
}




