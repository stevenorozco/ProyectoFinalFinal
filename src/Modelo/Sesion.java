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
public class Sesion {
    private Date fecha;
    private String horaInicial;
    private String horaFinal;
    private int tiempoConexion;

    public Sesion(Date fecha, String horaInicial, String horaFinal, int tiempoConexion) {
        this.fecha = fecha;
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
        this.tiempoConexion = tiempoConexion;
    }
    //--------METODOS GET---------
    public Date getFecha() {
        return fecha;
    }

    public String getHoraInicial() {
        return horaInicial;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public int getTiempoConexion() {
        return tiempoConexion;
    }

    //---------METODOS SET--------------
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setHoraInicial(String horaInicial) {
        this.horaInicial = horaInicial;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    public void setTiempoConexion(int tiempoConexion) {
        this.tiempoConexion = tiempoConexion;
    }
    
    
}
