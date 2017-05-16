/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import Modelo.*;
import Hilo.Hilo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author invitado
 */

public class Servidor extends Thread{
    private ServerSocket servidor;
    private Socket conexion;
    private HashMap biblioteca;
    
    
    public Servidor(int puerto) {
        this.biblioteca = new HashMap();          
        try {
            System.out.println("Creando servidor con puerto 1144");
            this.servidor = new ServerSocket(puerto);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void run(){
        leerBiblioteca();
        try{
            while(true){
                System.out.println("Servidor creado\nEsperando Conexion");
                this.conexion = servidor.accept();
                System.out.println("Conexion recibida de: " + conexion.getInetAddress());
                Hilo hiloCliente = new Hilo(conexion, biblioteca);
                hiloCliente.start();
                guardarBiblioteca();
            }
        }catch(IOException ioe){
            System.out.println("Error en la inicializacion del servidor");
        }
    }
    
    public void guardarBiblioteca(){
        ObjectOutputStream escritor;
        try {
            Iterator it = biblioteca.values().iterator();
            while(it.hasNext()){
                Libro libro = (Libro)it.next();
                String isbn = libro.getIsbn()+".txt";
                escritor = new ObjectOutputStream(new FileOutputStream(isbn, false));
                escritor.close();
            }
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
    
    public void leerBiblioteca(){
        ObjectInputStream lector;
        try {
            lector = new ObjectInputStream(new FileInputStream("biblioteca.txt"));
            HashMap biblioteca = (HashMap)lector.readObject();
            this.biblioteca = biblioteca;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }   
    }
    
    public String cerrarServidor(){
        String msg="Conexion del servidor detenida";
        try {
            this.conexion.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error al cerrar la conexion del servidor";
        }
        return msg;
    }
}

