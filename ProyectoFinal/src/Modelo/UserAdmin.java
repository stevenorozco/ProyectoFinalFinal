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
public class UserAdmin {
    private String nombre;
    private String apellidos;
    private String cargo;
    private String celular;
    private String correo;
    private boolean autorizado;

    public UserAdmin(String nombre, String apellidos, String cargo, String celular, String correo, boolean autorizado) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.cargo = cargo;
        this.celular = celular;
        this.correo = correo;
        this.autorizado = autorizado;
    }
    //---------METODOS GET--------------
    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getCargo() {
        return cargo;
    }

    public String getCelular() {
        return celular;
    }

    public String getCorreo() {
        return correo;
    }

    public boolean isAutorizado() {
        return autorizado;
    }
    //------------METODOS SET-------------
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setAutorizado(boolean autorizado) {
        this.autorizado = autorizado;
    }
    

}
