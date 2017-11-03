/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.Servidor;

/**
 * Utilizado en la creacion de tareas, encapsula un servidor y un nemonico rol
 * @author Maikel Pérez Martínez <maikel.perez@markasoft.ec>
 */
public class ServidorVO {
    
    private Servidor servidor;
    
    private String nemonicoRol;

    public ServidorVO() {
    }

    public ServidorVO(Servidor servidor, String nemonicoRol) {
        this.servidor = servidor;
        this.nemonicoRol = nemonicoRol;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public String getNemonicoRol() {
        return nemonicoRol;
    }

    public void setNemonicoRol(String nemonicoRol) {
        this.nemonicoRol = nemonicoRol;
    }
    
    
    
}
