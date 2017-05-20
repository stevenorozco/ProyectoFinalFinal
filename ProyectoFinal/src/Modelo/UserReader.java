/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.*;

/**
 *
 * @author invitado
 */
public class UserReader {
    private String nombre;
    private String apellidos;
    private int celular;
    private Date fechaNacimiento;
    private String correo;
    private int edad;
    private int librosLeidos;

    public UserReader(String nombre, String apellidos, int celular, Date fechaNacimiento, String correo, int edad, int librosLeidos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.celular = celular;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.edad = edad;
        this.librosLeidos = librosLeidos;
    }
    
    
    //------METODOS GET----------
    public String getNombre() {
        return nombre;
    }

    public int getCelular() {
        return celular;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public int getEdad() {
        return edad;
    }

    public int getLibrosLeidos() {
        return librosLeidos;
    }
    
    //--------METODOS SET---------
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setLibrosLeidos(int librosLeidos) {
        this.librosLeidos = librosLeidos;
    }
    
}
