/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.vo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"ruc", "anio", "registroLista"})
@XmlRootElement
public class RDEP {

    private String anio;

    private String ruc;

    private List<InfoSriVO> registroLista = new ArrayList<>();

    @XmlElement(name = "numRuc")    
    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    @XmlElement(name = "anio")
    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }
    
    public List<InfoSriVO> getRegistroLista() {
        return registroLista;
    }

    @XmlElementWrapper(name = "retRelDep")
    @XmlElement(name = "datRetRelDep")
    public void setRegistroLista(List<InfoSriVO> registroLista) {
        this.registroLista = registroLista;
    }
    
    
}
