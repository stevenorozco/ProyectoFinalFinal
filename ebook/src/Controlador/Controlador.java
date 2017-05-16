/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import Modelo.*;
import java.util.HashMap;

/**
 *
 * @author Steven
 */
public class Controlador {
    private HashMap libros = new HashMap(); 
    public  Ebook ebook = new Ebook(libros);

    public boolean agregarLibro(Libro libro){
        return ebook.agregarLibro(libro);
    }    
  
    public boolean eliminarLibro(String sbn)throws MyException {
        return ebook.eliminarLibro(sbn);
    }

public  Libro consultarLibro(String sbn)throws MyException{
    return ebook.consultarLibro(sbn); 
}

public boolean  modificarLibro(String sbn, Libro libro)throws MyException{
    return ebook.modificarLibro(sbn, libro);
}


   } 

