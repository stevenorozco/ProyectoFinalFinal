/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorCliente;
import Cliente.Cliente;
import Modelo.Libro;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Luis
 */
public class ControladorCliente {
    Cliente cliente;
    
    public ControladorCliente(){
        this.cliente = new Cliente();        
    }
    
    public void ejecutarCliente(){
        cliente.start();
    }
    
    public String[] consultarDatosLibro(String isbn){
        ArrayList resp= consultarLibro(isbn);
        if(resp.get(0)==null){
            String[] msg = new String[1];
            msg[0] = (String)resp.get(1);
            return msg;
        }else{            
            String[] datos = new String[5];
            datos[0] = ((String)resp.get(0));
            datos[1] = ((String)resp.get(1));
            datos[2] = Double.toString((double)resp.get(8));
            datos[3] = Integer.toString((int)resp.get(6));
            datos[4] = (String)resp.get(9);
            return datos; 
        }                 
    }
    
    public String agregarLibro(String isbn, int numeroPaginas, String titulo, String resumen, String autor, ImageIcon imagen,
            double precio, String categoria, boolean bestSeller, int edadMinima, int calificacion, String contenido){
       return cliente.agregarLibro(isbn, numeroPaginas, titulo, resumen, autor, imagen, precio, categoria, bestSeller, edadMinima,
                calificacion, contenido);
    }
    
    public String eliminarLibro(String isbn){
       return cliente.eliminarLibro(isbn);
    }
    
    public ArrayList consultarLibro(String isbn){
       ArrayList resp = cliente.consultarLibro(isbn);
       if(resp.get(0)==null){
           return resp;
       }else{
           Libro libro = (Libro)resp.get(0);
           ArrayList datos = new ArrayList();
           datos.add(libro.getTitulo());
           datos.add(libro.getAutor());
           datos.add(libro.getIsbn());
           datos.add(libro.getNumeroPaginas());
           datos.add(libro.getCategoria());
           datos.add(libro.getCalificacion());
           datos.add(libro.getEdadMinima());
           datos.add(libro.isBestSeller());
           datos.add(libro.getPrecio());
           datos.add(libro.getResumen());
           datos.add(libro.getImagen());
           
           return datos;
       }
    }
    
    public void editarLibro(){
        
    }
    
    public ArrayList seleccionarTexto(String path){
        ArrayList texto = cliente.seleccionarTexto(path);
        String contenido="";
        for(int i = 0; i < texto.size(); i++){
            if(i%20==0){
                contenido+="\n";
            }else{
                contenido+=(String)texto.get(i)+"\n";
            }
        }
        ArrayList retorno = new ArrayList();
        retorno.add(contenido);
        retorno.add(texto.size());
        
        return retorno;
    }
    
    public ImageIcon seleccionarPortada(String path){
        ImageIcon portada = cliente.seleccionarPortada(path);
        return portada;
    }
   
    public String cerrarConexion(){
        return cliente.cerrarConexion();
    }
}
