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
public class Lector {
    private String nombre;
    private String apellidos;
    private String celular;
    private String fechaNacimiento;
    private String correo;
    private int edad;
    private double saldo;
    private ArrayList consecutivo;
    private ArrayList fechaRecarga;
    private ArrayList horaRecarga;
    private ArrayList valorRecarga;
    private int librosLeidos;

    public Lector(String nombre, String apellidos, String celular, String fechaNacimiento, String correo, int edad, double saldo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.celular = celular;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.edad = edad;
        this.saldo = saldo;
        this.consecutivo = new ArrayList();
        this.fechaRecarga = new ArrayList();
        this.horaRecarga = new ArrayList();
        this.valorRecarga = new ArrayList();
        this.librosLeidos = librosLeidos;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getCelular() {
        return celular;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public int getEdad() {
        return edad;
    }

    public double getSaldo() {
        return saldo;
    }

    public ArrayList getConsecutivo() {
        return consecutivo;
    }

    public ArrayList getFechaRecarga() {
        return fechaRecarga;
    }

    public ArrayList getHoraRecarga() {
        return horaRecarga;
    }

    public ArrayList getValorRecarga() {
        return valorRecarga;
    }

    public int getLibrosLeidos() {
        return librosLeidos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setConsecutivo(ArrayList consecutivo) {
        this.consecutivo = consecutivo;
    }

    public void setFechaRecarga(ArrayList fechaRecarga) {
        this.fechaRecarga = fechaRecarga;
    }

    public void setHoraRecarga(ArrayList horaRecarga) {
        this.horaRecarga = horaRecarga;
    }

    public void setValorRecarga(ArrayList valorRecarga) {
        this.valorRecarga = valorRecarga;
    }

    public void setLibrosLeidos(int librosLeidos) {
        this.librosLeidos = librosLeidos;
    }
    
} 