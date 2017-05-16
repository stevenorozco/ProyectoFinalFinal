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
import java.util.ArrayList;

/**
 *
 * @author invitado
 */
public class Hilo extends Thread{
    Socket conexion;
    HashMap biblioteca;
    ObjectInputStream objectInput;
    ObjectOutputStream objectOutput;
    public Hilo(Socket conexion, HashMap biblioteca){
        this.conexion = conexion;
        this.biblioteca = biblioteca;
        
        try {
            objectInput = new ObjectInputStream(conexion.getInputStream());
            objectOutput = new ObjectOutputStream(conexion.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }     
    }
    
    @Override
    public void run(){
          try{
              while(true){
                  System.out.println("Thread running");
                  ArrayList msg = (ArrayList)objectInput.readObject();
                  switch((String)msg.get(0)){
                      case "agregarLibro":
                          agregarLibro((Libro)msg.get(1));                     
                          break;
                      case "consultarLibro":
                          consultarLibro((String)msg.get(1));
                          break;
                      case "eliminarLibro":
                          eliminarLibro((String)msg.get(1));
                          break;
                      case "editarLibro":
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
    
    public void agregarLibro(Libro libro){
        if(biblioteca.containsKey(libro.getIsbn())){
            try {
                ArrayList resp = new ArrayList();
                resp.add("El libro con ISBN: " + libro.getIsbn() + " ya existe en la biblioteca");
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else{
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
    public void eliminarLibro(String isbn){
        if(biblioteca.containsKey(isbn) == true){
            try {
                biblioteca.remove(isbn);
                ArrayList resp = new ArrayList();
                resp.add("Se elimino el libro con ISBN: " + isbn);
                objectOutput.writeObject(resp);
                objectOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else{
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
    public void consultarLibro(String isbn){
       Libro libro = (Libro)biblioteca.get(isbn);
       if(libro == null){
           try {
               ArrayList resp = new ArrayList();
               resp.add(null);
               resp.add("No se encontro el libro con el ISBN: " + isbn);
               objectOutput.writeObject(resp);
               objectOutput.flush();
           } catch (IOException ex) {
               ex.printStackTrace();
           }
       }else{
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

}