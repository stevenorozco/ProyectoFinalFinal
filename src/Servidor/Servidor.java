/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import Servidor.Hilo.Hilo;
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
    private HashMap lectores;
    
    
    public Servidor(int puerto) {
        this.biblioteca = new HashMap();   
        this.admins = new HashMap();
        this.lectores = new HashMap();
        
        try {
            System.out.println("Creando servidor con puerto 1144");
            this.servidor = new ServerSocket(puerto);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //--------------------------------------------LECTURA DE DATOS---------------------------------------------
        try {
            cargarAdmins();
        } catch (FileNotFoundException ex) {
            System.out.println("No se encontro un archivo de administradores");
            guardarAdmins();
        }
//        try {
//            cargarLectores();
//        } catch (FileNotFoundException ex) {
//            System.out.println("No se encontro un archivo de lectores");
//            guardarLectores();
//        }
        try {
            cargarBiblioteca();
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
                Hilo hiloCliente = new Hilo(conexion, biblioteca, admins, lectores);
                hiloCliente.start();
                
            }
        }catch(IOException ioe){
            System.out.println("Error en la inicializacion del servidor");
        }
    }
    //-----------------------------------SERIALIZACION AUXILIAR DE DATOS--------------------------------------------------
    public void guardarLectores(){
        ObjectOutputStream escritor;
        try {
            escritor = new ObjectOutputStream(new FileOutputStream("lectores.txt", false));
            escritor.writeObject(this.lectores);
            escritor.flush();
            escritor.close();
            System.out.println("Lectores guardados");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void guardarAdmins(){
        ObjectOutputStream escritor;
        try {
            escritor = new ObjectOutputStream(new FileOutputStream("administradores.txt", false));
            escritor.writeObject(this.admins);
            escritor.flush();
            escritor.close();
            System.out.println("Administradores guardados");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
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
    //-------------------------------------------LECTURA DE ARCHIVOS------------------------------------------------
    public void cargarBiblioteca() throws FileNotFoundException{
        System.out.println("Biblioteca cargada");
        ObjectInputStream lector;
        try {
            lector = new ObjectInputStream(new FileInputStream("biblioteca.txt"));
            HashMap biblioteca = (HashMap)lector.readObject();
            this.biblioteca = biblioteca;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }   
    }
    
    public void cargarAdmins() throws FileNotFoundException{
        System.out.print("Administradores cargados");
        ObjectInputStream lector;
        try{
            lector = new ObjectInputStream(new FileInputStream("administradores.txt"));
            HashMap admins = (HashMap)lector.readObject();
            this.admins = admins;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public void cargarLectores() throws FileNotFoundException{
        System.out.print("Lectores cargados");
        ObjectInputStream lector;
        try{
            lector = new ObjectInputStream(new FileInputStream("lectores.txt"));
            HashMap admins = (HashMap)lector.readObject();
            this.admins = admins;
        } catch (IOException | ClassNotFoundException ex) {
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

