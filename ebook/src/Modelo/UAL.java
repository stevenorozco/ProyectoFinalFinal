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
public class UAL {
    private String nombre;
    private String cargo;
    private int celular;
    private String correo;
    private boolean autorizacion;

    public UAL(String nombre, String cargo, int celular, String correo, boolean autorizacion) {
        this.nombre = nombre;
        this.cargo = cargo;
        this.celular = celular;
        this.correo = correo;
        this.autorizacion = autorizacion;
    }
    //--------METODOS GET--------
    public String getNombre() {
        return nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public int getCelular() {
        return celular;
    }

    public String getCorreo() {
        return correo;
    }

    public boolean isAutorizacion() {
        return autorizacion;
    }
    
    //----------METODOS SET------------
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setAutorizacion(boolean autorizacion) {
        this.autorizacion = autorizacion;
    }
    
    
}
