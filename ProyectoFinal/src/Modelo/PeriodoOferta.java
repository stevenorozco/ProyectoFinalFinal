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
public class PeriodoOferta extends Thread{
    private Date fechaInicial;
    private Date fechaFinal;
    private int porcentajeDescuento;

    public PeriodoOferta(Date fechaInicial, Date fechaFinal, int porcentajeDescuento) {
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.porcentajeDescuento = porcentajeDescuento;
    }
    
    //---------METODOS GET------------
    public Date getFechaInicial() {
        return fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public int getPorcentajeDescuento() {
        return porcentajeDescuento;
    }
    
    //-----------------METODOS SET------------------
    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public void setPorcentajeDescuento(int porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }
    
    
}
