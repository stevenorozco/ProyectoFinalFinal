/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author invitado
 */
public class Libro implements Serializable{
  
    private String isbn;
    private int numeroPaginas;
    private String titulo;
    private String resumen;
    private String autor;
    private ImageIcon imagen;
    private double precio;
    private String categoria;
    private boolean bestSeller;
    private int edadMinima;
    private int calificacion;
    private String contenido; 

    public Libro(String isbn, int numeroPaginas, String titulo, String resumen, String autor, ImageIcon imagen, double precio, String categoria, boolean bestSeller, int edadMinima, int calificacion, String contenido) {
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.titulo = titulo;
        this.resumen = resumen;
        this.autor = autor;
        this.imagen = imagen;
        this.precio = precio;
        this.categoria = categoria;
        this.bestSeller = bestSeller;
        this.edadMinima = edadMinima;
        this.calificacion = calificacion;
        this.contenido = contenido;
    }
    //-------METODOS GET---------
    public String getIsbn() {
        return isbn;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getResumen() {
        return resumen;
    }

    public String getAutor() {
        return autor;
    }

    public ImageIcon getImagen() {
        return imagen;
    }

    public double getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public boolean isBestSeller() {
        return bestSeller;
    }

    public int getEdadMinima() {
        return edadMinima;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public String getContenido() {
        return contenido;
    }
    
    //-------METODOS SET----------
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setBestSeller(boolean bestSeller) {
        this.bestSeller = bestSeller;
    }

    public void setEdadMinima(int edadMinima) {
        this.edadMinima = edadMinima;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
