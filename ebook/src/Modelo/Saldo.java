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
public class Saldo {
    private int consecutivo;
    private Date fecha;
    private String hora;
    private int valor;

    public Saldo(int consecutivo, Date fecha, String hora, int valor) {
        this.consecutivo = consecutivo;
        this.fecha = fecha;
        this.hora = hora;
        this.valor = valor;
    }
    
    //--------METODOS GET---------
    public int getConsecutivo() {
        return consecutivo;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public int getValor() {
        return valor;
    }
    
    //-------------METODOS SET-------------------
    public void setConsecutivo(int consecutivo) {
        this.consecutivo = consecutivo;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
    
    
}
