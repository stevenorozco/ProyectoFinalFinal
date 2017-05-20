/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorServidor;
import Servidor.Servidor;
/**
 *
 * @author Luis
 */
public class ControladorServidor {
    Servidor servidor;
    
    public ControladorServidor(int puerto){
        servidor = new Servidor(puerto);
    }
    
    public void iniciarServidor(){
        servidor.start();
    }
    
    public String cerrarServidor(){
        return servidor.cerrarServidor();
    }
}
