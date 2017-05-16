/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author invitado
 */
public class Ebook {
    private HashMap libros; 

    public Ebook(HashMap libros) {
        this.libros = libros;
    }

    //----------METODOS GET------------

    public HashMap getLibros() {
        return libros;
    }
    
    //----------METODOS SET------------

    public void setLibros(HashMap libros) {
        this.libros = libros;
    }
    
    

    public boolean agregarLibro(Libro libro){
       try{
           String sbn = libro.getIsbn();
           libros.put(sbn, libro);
           return true; 
           }
       catch(NullPointerException e ){
           return false;
       }
    }
    
    public boolean eliminarLibro(String sbn) throws MyException{
        if(libros.remove(sbn) == null){
            throw new MyException("No existe el libro que desea eliminar.");
        }
        return true; 
    }
    
    public Libro consultarLibro(String sbn) throws MyException{
        if(libros.containsKey(sbn)){
                Libro libro = (Libro)libros.get(sbn);
                return libro;
        }
        return null; 
    }
    
    public boolean modificarLibro(String sbn, Libro libro)throws MyException{
        if(libros.containsKey(sbn)){
            libros.put(sbn, libro);
        }
        else{
            throw new MyException("El libro especificado no fue encontrado");
        }
        return true; 
    }
    
    
      public ArrayList<Libro> consultarLibroCategoria(String categoria){
        ArrayList<Libro> librosCategoria = new ArrayList();       
        
        Iterator it = this.libros.keySet().iterator();
        while(it.hasNext()){
            String key = (String) it.next();
            Libro libro = (Libro)this.libros.get(key);
            if(libro.getCategoria().equals(categoria))
                librosCategoria.add(libro);
        }

        return librosCategoria;
    }
}
