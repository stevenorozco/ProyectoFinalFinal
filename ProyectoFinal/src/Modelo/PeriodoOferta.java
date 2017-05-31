/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author invitado
 */
public class PeriodoOferta extends Thread{
    private long tiempoOferta;
    private int porcentajeDescuento;

    public PeriodoOferta(long tiempoOferta, int porcentajeDescuento) {
        this.tiempoOferta = tiempoOferta;
        this.porcentajeDescuento = porcentajeDescuento;
    }
    
    @Override
    public synchronized void run(){
        
    }

    public long getTiempoOferta() {
        return tiempoOferta;
    }

    public void setTiempoOferta(long tiempoOferta) {
        this.tiempoOferta = tiempoOferta;
    }

    public int getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(int porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }
    
}