/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor.Controlador;
import Servidor.Servidor;
/**
 *
 * @author Luis
 */
public class Controlador {
    Servidor servidor;
    
    public Controlador(int puerto){
        servidor = new Servidor(puerto);
    }
    
    public void iniciarServidor(){
        servidor.start();
    }
    
    public String cerrarServidor(){
        return servidor.cerrarServidor();
    }
}
