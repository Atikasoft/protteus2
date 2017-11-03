/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.vo;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"tipoIdentificacion", "numeroIdentificacion", "apellidos",
                    "nombres", "estab", "residencia","paisResidencia", "aplicaConvenio", 
                    "tipoDiscapacidad", "porcientoDiscapacidad", "tipoIdDiscapacidad","numeroIdDiscapacidad"})
@XmlRootElement
public class EmpleadoRdepVO implements Serializable{
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String apellidos;
    private String nombres;
    private String estab;
    private String residencia;
    private String paisResidencia;
    private String aplicaConvenio;
    private String tipoDiscapacidad;
    private String porcientoDiscapacidad;
    private String tipoIdDiscapacidad;
    private String numeroIdDiscapacidad;
    
    @XmlElement(name = "tipIdRet")
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    @XmlElement(name = "idRet")
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    @XmlElement(name = "apellidoTrab")
    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @XmlElement(name = "nombreTrab")
    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    @XmlElement(name = "estab")
    public String getEstab() {
        return estab;
    }

    public void setEstab(String estab) {
        this.estab = estab;
    }

    @XmlElement(name = "residenciaTrab")
    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    @XmlElement(name = "paisResidencia")
    public String getPaisResidencia() {
        return paisResidencia;
    }

    public void setPaisResidencia(String paisResidencia) {
        this.paisResidencia = paisResidencia;
    }

    @XmlElement(name = "aplicaConvenio")
    public String getAplicaConvenio() {
        return aplicaConvenio;
    }

    public void setAplicaConvenio(String aplicaConvenio) {
        this.aplicaConvenio = aplicaConvenio;
    }

    @XmlElement(name = "tipoTrabajDiscap")
    public String getTipoDiscapacidad() {
        return tipoDiscapacidad;
    }

    public void setTipoDiscapacidad(String tipoDiscapacidad) {
        this.tipoDiscapacidad = tipoDiscapacidad;
    }

    @XmlElement(name = "porcentajeDiscap")
    public String getPorcientoDiscapacidad() {
        return porcientoDiscapacidad;
    }

    public void setPorcientoDiscapacidad(String porcientoDiscapacidad) {
        this.porcientoDiscapacidad = porcientoDiscapacidad;
    }

    @XmlElement(name = "tipIdDiscap")
    public String getTipoIdDiscapacidad() {
        return tipoIdDiscapacidad;
    }

    public void setTipoIdDiscapacidad(String tipoIdDiscapacidad) {
        this.tipoIdDiscapacidad = tipoIdDiscapacidad;
    }

    @XmlElement(name = "idDiscap")
    public String getNumeroIdDiscapacidad() {
        return numeroIdDiscapacidad;
    }

    public void setNumeroIdDiscapacidad(String numeroIdDiscapacidad) {
        this.numeroIdDiscapacidad = numeroIdDiscapacidad;
    }

    
}
