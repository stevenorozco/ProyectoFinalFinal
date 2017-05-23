/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import Hilo.Hilo;
import Modelo.Admin;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 *
 * @author invitado
 */

public class Servidor extends Thread{
    private ServerSocket servidor;
    private Socket conexion;
    private HashMap biblioteca;
    private HashMap admins;
    private HashMap usuarios;
    
    
    public Servidor(int puerto) {
        this.biblioteca = new HashMap();   
        this.admins = new HashMap();
        this.usuarios = new HashMap();
        if(admins.isEmpty()){
            Admin admin = new Admin("Luis", "Restrepo", "Admin principal", "3192518747", "luisrestrepo1995@gmail.com", "123", true);
            admins.put(admin.getCorreo(), admin);
            
        }
        try {
            System.out.println("Creando servidor con puerto 1144");
            this.servidor = new ServerSocket(puerto);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            leerBiblioteca();
        } catch (FileNotFoundException ex) {
            System.out.println("No se encontro un archivo de biblioteca");
            guardarBiblioteca();
        }
    }
    
    @Override
    public void run(){
        try{
            while(true){
                System.out.println("Servidor creado\nEsperando Conexion");
                this.conexion = servidor.accept();
                System.out.println("Conexion recibida de: " + conexion.getInetAddress());
                Hilo hiloCliente = new Hilo(conexion, biblioteca, admins, usuarios);
                hiloCliente.start();
                
            }
        }catch(IOException ioe){
            System.out.println("Error en la inicializacion del servidor");
        }
    }
    
     public void guardarBiblioteca(){
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
    
    public void leerBiblioteca() throws FileNotFoundException{
        System.out.println("Biblioteca leida");
        ObjectInputStream lector;
        try {
            lector = new ObjectInputStream(new FileInputStream("biblioteca.txt"));
            HashMap biblioteca = (HashMap)lector.readObject();
            this.biblioteca = biblioteca;
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

